package com.galaxe.service.app.business.resources.impl;

import com.galaxe.enterprise.UserProfileBusinessRequestBO;
import com.galaxe.enterprise.UserProfileBusinessResponseBO;

public class UserProfileBusinessResourceImpl {

	public UserProfileBusinessResponseBO authenticate(
			UserProfileBusinessRequestBO request) {
		
		UserProfileBusinessResponseBO response = new UserProfileBusinessResponseBO();
		
		response.setFirstName("Vineet");
		response.setLastName("Kejriwal");
		response.setAge(29);
		
		return response;
	}

}
