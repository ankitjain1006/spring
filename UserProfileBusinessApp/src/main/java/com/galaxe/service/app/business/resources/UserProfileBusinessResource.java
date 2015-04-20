package com.galaxe.service.app.business.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.galaxe.enterprise.UserProfileBusinessRequestBO;
import com.galaxe.enterprise.UserProfileBusinessResponseBO;
import com.galaxe.service.app.business.resources.impl.UserProfileBusinessResourceImpl;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("userprofile")
@Produces({MediaType.APPLICATION_XML})
public class UserProfileBusinessResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 * 
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Path("authentication")
	@Consumes({MediaType.APPLICATION_XML})
	public UserProfileBusinessResponseBO authenticate(
			UserProfileBusinessRequestBO request) {
		UserProfileBusinessResourceImpl resourceImpl = new UserProfileBusinessResourceImpl();

		UserProfileBusinessResponseBO response = resourceImpl
				.authenticate(request);

		return response;
	}
	
	@GET
	@Path("authenticationget")
	public UserProfileBusinessResponseBO authenticateGet(){
		UserProfileBusinessResourceImpl resourceImpl = new UserProfileBusinessResourceImpl();

		UserProfileBusinessResponseBO response = resourceImpl
				.authenticate(null);

		return response;
	}
}
