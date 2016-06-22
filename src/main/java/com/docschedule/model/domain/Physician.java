
package com.docschedule.model.domain;

public class Physician{
    private int     physicianId;
	private String	firstName;
	private String	lastName;
    private int     nightOrder;
    private int     sideId;

	public int getPhysicianId() { return physicianId; }
	public void setPhysicianId(int physicianId) { this.physicianId = physicianId; }
	
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public int getNightOrder() { return nightOrder; }
	public void setNightOrder(int nightOrder) { this.nightOrder = nightOrder; }

	public int getSideId() { return sideId; }
	public void setSideId(int sideId) { this.sideId = sideId; }

}
