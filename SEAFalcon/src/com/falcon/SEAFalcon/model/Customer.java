package com.falcon.SEAFalcon.model;


public class Customer extends Person{
	private static Customer instance;
	private int zipCode;
	private int phone;
	private long creditNumber;
	private int cvv;
	private String email;
	
	private Customer(String email){
		super();
		this.email = email;
	}
	
	public static Customer getCustomer(String email){
		if(instance == null){
			instance = new Customer(email);
		}
		return instance;
	}

	public static Customer getInstance() {
		return instance;
	}

	public static void setInstance(Customer instance) {
		Customer.instance = instance;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public long getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(long creditNumber) {
		this.creditNumber = creditNumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	} 
	
	public String getEmail(){
		return email;
	}
	
}
