package com.senla.authorization.client;

import com.senla.authorization.dto.UserDataDto;

public interface UserDataMicroserviceClient {

    UserDataDto getUserDataByUserId(Long id);

}
