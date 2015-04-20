package com.galaxe.products.app.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.galaxe.products.app.resource.impl.UserProfileResourceImpl;

/**
 * Root resource 
 */
@Path("userprofile")
public class UserProfileResource {

	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("authentication")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    
    public ResponseJAXB authenticateUser(RequestJAXB request) {
    
    	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    	
    	UserProfileResourceImpl userProfileResourceImpl = (UserProfileResourceImpl) context.getBean("userProfileResourceImpl");
    	
    	 ResponseJAXB jaxbResponse = userProfileResourceImpl.authenticateUser(request);
    	 
    	 return jaxbResponse;
    }
	
	 
}
