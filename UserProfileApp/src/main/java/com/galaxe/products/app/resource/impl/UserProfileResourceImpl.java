package com.galaxe.products.app.resource.impl;

import com.galaxe.products.app.bo.UserProfileRequestBO;
import com.galaxe.products.app.bo.UserProfileResponseBO;
import com.galaxe.products.app.resource.RequestJAXB;
import com.galaxe.products.app.resource.ResponseJAXB;
import com.galaxe.products.app.service.UserProfileService;
import com.galaxe.products.app.service.impl.UserProfileServiceImpl;
import com.galaxe.products.app.utils.transformer.RequestTransformer;
import com.galaxe.products.app.utils.transformer.ResponseTransformer;

public class UserProfileResourceImpl {

	private UserProfileService userProfileService;
	
	public ResponseJAXB authenticateUser(RequestJAXB request) {
		
		UserProfileRequestBO boRequest = RequestTransformer.transform(request);
		
		UserProfileResponseBO boResponse = userProfileService.authenticate(boRequest);
		
		return ResponseTransformer.transform(boResponse);
	}
	
	public void setUserProfileService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

}
