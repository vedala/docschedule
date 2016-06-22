
package com.docschedule.model.service;

import java.sql.Date;

import com.docschedule.model.dao.SideDAO;
import com.docschedule.model.dao.PhysicianDAO;
import com.docschedule.model.dao.ScheduleDAO;
import com.docschedule.model.domain.Physician;

import java.util.Calendar;
import java.util.ArrayList;


public class CreateSchedule {

    public static void createSchedule(String startDateStr, String endDateStr) {

        Date startDateSide1 = SideDAO.getStartDate(1);
        int numPhysiciansPerSide = PhysicianDAO.getPhysiciansForSide(1);

        // determine if schedule start date should start at side 1 or side 2

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        int daysBetween = ((int) (startDate.getTime() / (24*60*60*1000)) )
                            - ((int) (startDateSide1.getTime() / (24*60*60*1000)));

        int side = -1;
        if ((daysBetween % 14) < 7) {
            side = 1;
        }
        else {
            side = 2;
        }

        // create schedule records keeping in mind night order
        int nightOrder = 1;
        int currSide = 1;
        int week = 1;
        Date currDate = startDate;
        int dayCount = 1;

        Boolean dateInRange = currDate.compareTo(endDate) <= 0 ? true : false;
        while (dateInRange) {
            ArrayList<Physician> physicianArr = PhysicianDAO.getPhysiciansBySide(currSide);
            for (Physician physician : physicianArr) {
                int physicianId = physician.getPhysicianId();
                int nightOrderSelected = physician.getNightOrder();
                int shiftIdToInsert = 1;

                if (nightOrderSelected == nightOrder) {
                    shiftIdToInsert = 2;
                }
                ScheduleDAO.addSchedule(currDate, physicianId, shiftIdToInsert);

            }

            // increment by 1 day
            currDate.setTime(currDate.getTime() + (24*60*60*1000));
            dateInRange = currDate.compareTo(endDate) <= 0 ? true : false;

            // get day of week
            Calendar cal = Calendar.getInstance();
            cal.setTime(currDate);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            dayCount++;

            // Tuesday is start of new week
            if (dayOfWeek == Calendar.TUESDAY && dayCount > 1) {
                week++;
                currSide = (currSide == 1) ? 2 : 1;

                // Night order changes once every four weeks
                if ((week > 1) && (week % 4 == 1)) {
                    nightOrder++;
                    if (nightOrder > numPhysiciansPerSide) {
                        nightOrder = 1;
                    }
                }
            }
        }
    }

}
