package com.galaxe.products.app.service.impl;

import com.galaxe.products.app.bo.UserProfileRequestBO;
import com.galaxe.products.app.bo.UserProfileResponseBO;
import com.galaxe.products.app.business.bo.BusinessEntAdapterService;
import com.galaxe.products.app.service.UserProfileService;

public class UserProfileServiceImpl implements UserProfileService {

	private BusinessEntAdapterService businessEntAdapterService;

	@Override
	public UserProfileResponseBO authenticate(UserProfileRequestBO boRequest) {

		UserProfileResponseBO boResponse = businessEntAdapterService
				.authenticate(boRequest);

		return boResponse;
	}

	public void setBusinessEntAdapterService(
			BusinessEntAdapterService businessEntAdapterService) {
		this.businessEntAdapterService = businessEntAdapterService;
	}
}
