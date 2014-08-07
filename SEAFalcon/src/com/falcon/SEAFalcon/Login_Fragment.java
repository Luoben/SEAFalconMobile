package com.falcon.SEAFalcon;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login_Fragment extends Fragment {

	private Button login;
	private Button register;
	private EditText et_name;
	private EditText et_password;
	private TextView tv_error;
	private String str_userId;
	private String str_password;
	private Context context;
	ProgressDialog progress = null;

	public Login_Fragment(Context context) {
		this.context = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.login_fragment, container, false);

		login = (Button) view.findViewById(R.id.lg_bt_login);
		register = (Button) view.findViewById(R.id.lg_bt_signup);
		et_name = (EditText) view.findViewById(R.id.lg_et_email);
		et_password = (EditText) view.findViewById(R.id.lg_et_pw);
		tv_error = (TextView) view.findViewById(R.id.lg_tv_error);

		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				str_userId = et_name.getText().toString();
				str_password = et_password.getText().toString();
				if (str_userId.isEmpty() || str_password.isEmpty()) {
					tv_error.setText("Email or password should not be empty!");
				} else {
					// start a ASynctask to login
					new LoginTask().execute();
				}
			}
		});

		register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentTransaction transaction = MainActivity.fragmentmanager
						.beginTransaction();
				transaction.replace(R.id.fragment_container,
						new Register_Fragment());
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		return view;
	}

	private class LoginTask extends AsyncTask<Void, Void, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(context);
			progress.setTitle("Connecting server...");
			progress.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			// run in background

			JSONObject jo_email_password = new JSONObject();
			URI url = null;
			String result = "";
			try {
				jo_email_password.put("userid", str_userId);
				jo_email_password.put("password", str_userId);
				StringEntity se = new StringEntity(jo_email_password.toString());
				se.setContentType("application/json; charset=UTF-8");
				url = new URI(Constants.URL_LOGIN);
				HttpPost login_request = new HttpPost();
				login_request.setURI(url);
				login_request.setEntity(se);

				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse httpresponse = httpclient.execute(login_request);

				result = EntityUtils.toString(httpresponse.getEntity());
				return result;

			} catch (JSONException e) {

			} catch (Exception e) {
				Log.e("LoginTask", e.toString());
			} finally {

			}

			return result;
		}

		@Override
		protected void onPostExecute(String returnString) {
			// run after doInBackground in main thread
			String returnCode = "OK";
			String email = "email";
			
			/*try{
				JSONObject jo = new JSONObject(returnString);
				returnCode = jo.get("RC_CODE").toString();
			} catch (JSONException e){
				
			}*/
			
			
			progress.dismiss();
			if (returnCode.equals("OK")) {
				//start a new activity
				Intent intent = new Intent(context, Customer_Main.class);
				intent.putExtra("user_id", email);
				//get Customer class's get function to create a customer, and then set all the attributes
				
				context.startActivity(intent);
				MainActivity.instance.finish();
			} else {
				tv_error.setText("Login failed!");
			}
		}
	}

}
