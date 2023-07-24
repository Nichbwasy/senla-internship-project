package com.senla.authorization.client;

import com.senla.authorization.dto.UserDataDto;

public interface UserDataMicroserviceClient {

    /**
     * Returns user data dto by user id if exists
     * @param id Id of user
     * @return User data dto
     */
    UserDataDto getUserDataByUserId(Long id);

}
