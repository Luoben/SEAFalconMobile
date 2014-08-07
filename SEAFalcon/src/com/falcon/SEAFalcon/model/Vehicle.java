package com.falcon.SEAFalcon.model;

public class Vehicle {
	private VehicleType vehicleType;
	private int capacity;
	private String license;
	
	public Vehicle(String license, VehicleType vt, int capacity){
		this.license = license;
		this.capacity = capacity;
		this.vehicleType = vt;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public int getCapacity() {
		return capacity;
	}

	public String getLicense() {
		return license;
	}
}

enum VehicleType{
	Sedan, SUV, VAN
}
