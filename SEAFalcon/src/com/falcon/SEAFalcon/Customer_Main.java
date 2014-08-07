package com.falcon.SEAFalcon;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class Customer_Main extends ActionBarActivity {
	public static FragmentManager customer_fragment_manager;
	private Fragment googleMapFragment;
	public static Customer_Main instance;
	GoogleCloudMessaging gcm;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().show();
		setContentView(R.layout.customer_main);
		instance = this;
		context = this;

		customer_fragment_manager = getSupportFragmentManager();
		if (findViewById(R.id.customer_container) != null) {

			if (savedInstanceState != null) {
				return;
			}
			googleMapFragment = new Customer_Map_Fragment();
			customer_fragment_manager.beginTransaction()
					.add(R.id.customer_container, googleMapFragment).commit();
		}

		/*
		 * register Google could message ID, and share it with SEAFalcon web
		 * service
		 */
		registerGCM();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		FragmentTransaction transaction = customer_fragment_manager
				.beginTransaction();
		int id = item.getItemId();
		switch (id) {
		case R.id.action_settings:
			return true;
		case R.id.action_selfinfo:
			// start customer self information fragment
			transaction.replace(R.id.customer_container,
					new CustomerSelfInformationFragment());
			transaction.addToBackStack(null);
			transaction.commit();
			return true;
		case R.id.action_view_reservation:
			// start a fragment to show current reservation
			transaction = customer_fragment_manager.beginTransaction();
			transaction.replace(R.id.customer_container,
					new CustomerReservationFragment());
			transaction.addToBackStack(null);
			transaction.commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void registerGCM() {
		String regId = getRegistrationId(this);
		if (TextUtils.isEmpty(regId)) {
			registerGCMInBackground();
		} else {
			// share with SEAFalcon server
			shareRegIdWithServer(regId);
		}
	}

	private void registerGCMInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String regId = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(Constants.GOOGLE_PROJECT_ID);
					storeRegistrationId(context, regId);
				} catch (IOException e) {
					Log.e("Customer_Main e", e.toString());
				}
				return regId;
			}

			@Override
			protected void onPostExecute(String regId) {
				Log.e("Customer_Main e", regId);
				shareRegIdWithServer(regId);
			}
		}.execute(null, null, null);
	}

	private void shareRegIdWithServer(String regId) {
		new AsyncTask<String, Void, Void>() {
			@Override
			protected Void doInBackground(String... regId) {
				JSONObject jo_gcm_regId = new JSONObject();
				URI url = null;
				try {
					
					jo_gcm_regId.put(Constants.REG_ID, regId);
					StringEntity se = new StringEntity(jo_gcm_regId.toString());
					se.setContentType("application/json; charset=UTF-8");
					url = new URI(Constants.URL_SHARE_GCM_REGID);
					HttpPost login_request = new HttpPost();
					login_request.setURI(url);
					login_request.setEntity(se);

					HttpClient httpclient = new DefaultHttpClient();
					HttpResponse httpresponse = httpclient
							.execute(login_request);

					//result = EntityUtils.toString(httpresponse.getEntity());
					return null;

				} catch (JSONException e) {

				} catch (Exception e) {
					Log.e("Share GCM RegID", e.toString());
				} finally {

				}
				return null;
			}

			@Override
			protected void onPostExecute(Void regId) {

			}
		}.execute(regId, null, null);
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getSharedPreferences(
				Customer_Main.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString(Constants.REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i("Customer_Main", "Registration not found.");
			return "";
		}
		Log.i("Customer_Main", registrationId);
		return registrationId;
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getSharedPreferences(
				Customer_Main.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(Constants.REG_ID, regId);
		editor.commit();
	}
}
