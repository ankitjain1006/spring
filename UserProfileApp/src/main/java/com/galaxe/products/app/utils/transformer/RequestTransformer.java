package com.galaxe.products.app.utils.transformer;

import com.galaxe.enterprise.UserProfileBusinessRequestBO;
import com.galaxe.products.app.bo.UserProfileRequestBO;
import com.galaxe.products.app.resource.RequestJAXB;

public class RequestTransformer {

	public static UserProfileRequestBO transform(RequestJAXB request) {

		UserProfileRequestBO boRequest = new UserProfileRequestBO();

		boRequest.setUserName(request.getUserName());
		boRequest.setUserPassword(request.getUserPassword());

		return boRequest;

	}

	public static UserProfileBusinessRequestBO transform(UserProfileRequestBO boRequest) {

		UserProfileBusinessRequestBO boBusinessRequest = new UserProfileBusinessRequestBO();

		boBusinessRequest.setUserName(boRequest.getUserName());
		boBusinessRequest.setUserPassword(boRequest.getUserPassword());

		return boBusinessRequest;

	}

}
