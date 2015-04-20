package com.galaxe.products.app.business.bo;

import com.galaxe.enterprise.UserProfileBusinessInterface;
import com.galaxe.enterprise.UserProfileBusinessRequestBO;
import com.galaxe.enterprise.UserProfileBusinessResponseBO;
import com.galaxe.products.app.bo.UserProfileRequestBO;
import com.galaxe.products.app.bo.UserProfileResponseBO;
import com.galaxe.products.app.utils.transformer.RequestTransformer;
import com.galaxe.products.app.utils.transformer.ResponseTransformer;

public class BusinessEntAdapterService {

	private UserProfileBusinessInterface businessResource;

	public UserProfileResponseBO authenticate(UserProfileRequestBO boRequest) {

		UserProfileBusinessRequestBO boBusinessRequest = RequestTransformer
				.transform(boRequest);

		UserProfileBusinessResponseBO boBusinessResponse = businessResource
				.authenticateGet();

		UserProfileResponseBO boResponse = ResponseTransformer
				.transform(boBusinessResponse);

		return boResponse;

	}

	public void setBusinessResource(UserProfileBusinessInterface businessResource) {
		this.businessResource = businessResource;
	}
}
