package com.galaxe.products.app.utils.transformer;

import com.galaxe.enterprise.UserProfileBusinessResponseBO;
import com.galaxe.products.app.bo.UserProfileResponseBO;
import com.galaxe.products.app.resource.ResponseJAXB;

public class ResponseTransformer {

	public static ResponseJAXB transform(UserProfileResponseBO boResponse) {

		ResponseJAXB responseJaxb = new ResponseJAXB();
		
		responseJaxb.setFirstName(boResponse.getFirstName());
		responseJaxb.setLastName(boResponse.getLastName());
		responseJaxb.setAge(boResponse.getAge());
		
		return responseJaxb;
	}
	
	
	public static UserProfileResponseBO transform(UserProfileBusinessResponseBO boBusinessResponse) {

		UserProfileResponseBO boResponse = new UserProfileResponseBO();
		
		boResponse.setFirstName(boBusinessResponse.getFirstName());
		boResponse.setLastName(boBusinessResponse.getLastName());
		boResponse.setAge(boBusinessResponse.getAge());
		
		return boResponse;
	}

}
