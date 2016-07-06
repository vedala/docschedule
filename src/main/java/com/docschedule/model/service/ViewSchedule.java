
package com.docschedule.model.service;

import java.sql.Date;

import com.docschedule.model.dao.DailyScheduleDAO;
import com.docschedule.model.domain.DailySchedule;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import com.docschedule.model.domain.DailySchedule;
import com.docschedule.model.domain.PhysicianInfo;

public class ViewSchedule {

    public static List<DailySchedule> getSchedule(String startDate, String endDate) {

        DailyScheduleDAO dailyScheduleDAO = new DailyScheduleDAO();
        List<DailySchedule> scheduleArr = dailyScheduleDAO.getScheduleByDateRange(startDate, endDate);

        return scheduleArr;
    }

}
