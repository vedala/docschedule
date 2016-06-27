
package com.docschedule.model.service;

import java.sql.Date;

import com.docschedule.model.dao.SideDAO;
import com.docschedule.model.dao.PhysicianDAO;
import com.docschedule.model.dao.ScheduleDAO;
import com.docschedule.model.domain.Physician;

import java.util.Calendar;
import java.util.ArrayList;


public class ViewSchedule {

    public ArrayList<PhysicianInfo> getSchedule(String startDateStr, String endDateStr) {

        ArrayList<PhysicianInfo> scheduleArr = new ArrayList<PhysicianInfo>();

        return scheduleArr;
    }

}
