package com.docschedule.domain;

import java.util.ArrayList;

public class DailySchedule {
   private String scheduleDate;
   private ArrayList<PhysicianInfo> physicianInfoArray;
   

	public String getScheduleDate() {
		return scheduleDate;
	}
	
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public ArrayList<PhysicianInfo> getPhysicianInfoArray() {
		return physicianInfoArray;
	}
	
	public void setPhysicianInfoArray(ArrayList<PhysicianInfo> physicianInfoArray) {
		this.physicianInfoArray = physicianInfoArray;
	}
}

