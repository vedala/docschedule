package com.docschedule.services;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class SchedulingApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public SchedulingApplication() {
        singletons.add(new DateRangeScheduleResource());
        singletons.add(new OneDayScheduleResource());
        singletons.add(new VerifyEmailResource());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
