
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

        final int SIDE_ONE = 1;
        final int SIDE_TWO = 2;
        final int SHIFT_DAY   = 1;
        final int SHIFT_NIGHT = 2;

        SideDAO sideDAO = new SideDAO();
        PhysicianDAO physicianDAO = new PhysicianDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();

        Date startDateSide1 = sideDAO.getStartDate(SIDE_ONE);
        int numPhysiciansPerSide = physicianDAO.getPhysiciansForSide(SIDE_ONE);

        // determine if schedule start date should start at side 1 or side 2

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        int daysBetween = ((int) (startDate.getTime() / (24*60*60*1000)) )
                            - ((int) (startDateSide1.getTime() / (24*60*60*1000)));

        int side = -1;
        if ((daysBetween % 14) < 7) {
            side = SIDE_ONE;
        }
        else {
            side = SIDE_TWO;
        }

        // create schedule records keeping in mind night order
        int nightOrder = 1;
        int currSide = SIDE_ONE;
        int week = 1;
        Date currDate = startDate;
        int dayCount = 1;

        Boolean dateInRange = currDate.compareTo(endDate) <= 0 ? true : false;
        while (dateInRange) {
            ArrayList<Physician> physicianArr = physicianDAO.getPhysiciansBySide(currSide);
            for (Physician physician : physicianArr) {
                int physicianId = physician.getPhysicianId();
                int nightOrderSelected = physician.getNightOrder();
                int shiftIdToInsert = SHIFT_DAY;

                if (nightOrderSelected == nightOrder) {
                    shiftIdToInsert = SHIFT_NIGHT;
                }
                scheduleDAO.addSchedule(currDate, physicianId, shiftIdToInsert);

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
                currSide = (currSide == SIDE_ONE) ? SIDE_TWO : SIDE_ONE;

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
