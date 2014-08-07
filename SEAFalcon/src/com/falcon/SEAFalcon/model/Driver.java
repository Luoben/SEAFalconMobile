package com.falcon.SEAFalcon.model;

import com.google.android.gms.maps.model.LatLng;

public class Driver extends Person{

	private String driverLicenseNumber;
	private Vehicle activiteVehicle;
	private LatLng latlng;
	
	public Driver(String driverLicenseNumber){
		this.driverLicenseNumber = driverLicenseNumber;
	}

	public Vehicle getActiviteVehicle() {
		return activiteVehicle;
	}

	public void setActiviteVehicle(Vehicle activiteVehicle) {
		this.activiteVehicle = activiteVehicle;
	}

	public LatLng getLatlng() {
		return latlng;
	}

	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}

	public String getDriverLicenseNumber() {
		return driverLicenseNumber;
	}
}



