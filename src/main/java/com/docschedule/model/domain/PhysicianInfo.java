package com.docschedule.model.domain;

public class PhysicianInfo {
	private String	firstName;
	private String	lastName;
	private	String	shiftShortname;

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getShiftShortname() { return shiftShortname; }
	
	public void setShiftShortname(String shiftShortname) { this.shiftShortname = shiftShortname; }
}
