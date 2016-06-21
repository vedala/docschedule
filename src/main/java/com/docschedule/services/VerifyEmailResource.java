/*
 * VerifyEmailResource.java
 *
 * This file contains the REST web service that extracts token
 * from url and verifies the account by updating the verified
 * field in users table.
 *
 */ 

package com.docschedule.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.docschedule.model.dao.UserDAO;

@Path("/v1/VerifyEmail/{verifyToken}")
public class VerifyEmailResource {

    @GET
    public void verifyAndUpdate(@PathParam("verifyToken") String verifyToken) {
        UserDAO.updateUserVerified(verifyToken);
    }
}
