package com.falcon.SEAFalcon;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.falcon.SEAFalcon.model.Driver;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Customer_Map_Fragment extends SupportMapFragment{
	private GoogleMap mGoogleMap;
	private Map<String, Driver> driversMap;
	public Customer_Map_Fragment(){
		driversMap = new HashMap<String, Driver>();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, parent, savedInstanceState);
		
		//View v = inflater.inflate(R.layout.customer_map, parent, false); ???? why this doesn't work
		
		
		// Stash a reference to the GoogleMap
		mGoogleMap = getMap();
		// Show the user's location
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.setInfoWindowAdapter(new InfoWindowAdapter(){
			// Use default InfoWindow frame
	        @Override
	        public View getInfoWindow(Marker arg0) {
	            return null;
	        }
	        
	        // Defines the contents of the InfoWindow
	        @Override
	        public View getInfoContents(Marker arg0) {

	            // Getting view from the layout file info_window_layout
	            View v = Customer_Main.instance.getLayoutInflater().inflate(R.layout.driver_infowindow, null);
	            // Returning the view containing InfoWindow contents
	            return v;

	        }
		});
		mGoogleMap.setOnMapClickListener(new OnMapClickListener(){
			@Override
			public void onMapClick(LatLng point){
				
				Marker melbourne = mGoogleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .anchor(0.5f, 0.5f) // Anchors the marker on the bottom left
                .position(point)
                .alpha(0.7f)
                .title("I am here!!"));
			}
		});
		
		mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
			@Override
			public void onInfoWindowClick(Marker marker){
				FragmentTransaction transaction = Customer_Main.customer_fragment_manager.beginTransaction(); 
				transaction.replace(R.id.customer_container,
						new CustomerReservationFragment());
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		
		mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener(){
			@Override
			public boolean onMarkerClick(Marker marker){
				marker.showInfoWindow();
				return true;
			}
		});
		return v;
	}
}
