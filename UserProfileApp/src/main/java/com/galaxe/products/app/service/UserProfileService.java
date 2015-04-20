package com.galaxe.products.app.service;

import com.galaxe.products.app.bo.UserProfileRequestBO;
import com.galaxe.products.app.bo.UserProfileResponseBO;

public interface UserProfileService {

	UserProfileResponseBO authenticate(UserProfileRequestBO boRequest);

}
