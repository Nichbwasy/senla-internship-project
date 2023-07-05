package com.senla.authorization.service;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import com.senla.authorization.service.exceptions.services.registration.LoginInUseException;
import com.senla.authorization.service.exceptions.services.registration.PasswordMatchException;
import com.senla.authorization.service.impl.RegistrationServiceImpl;
import com.senla.authorization.service.mappers.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RegistrationServiceTests {

    @InjectMocks
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Mock
    private UserDataRepository userDataRepository;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void registerUserTest() {
        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(false);
        Mockito.when(userDataRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        UserDataDto result = registrationService.registerUser(new UserRegistrationDataDto("Login", "Password","email1", "Password"));
        Assertions.assertEquals("Login", result.getLogin());
    }

    @Test
    public void registerUserIfPasswordsNotMatchTest() {
        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(false);
        Mockito.when(userDataRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        UserRegistrationDataDto form = new UserRegistrationDataDto("Login", "Password", "email2", "Password2");
        Assertions.assertThrows(PasswordMatchException.class, () -> registrationService.registerUser(form));
    }

    @Test
    public void registerUserIfAlreadyRegisteredTest() {
        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(true);

        UserRegistrationDataDto form = new UserRegistrationDataDto("Login", "Password", "email3", "Password");
        Assertions.assertThrows(LoginInUseException.class, () -> registrationService.registerUser(form));
    }

    @Test
    public void registerUserIfFormIsNullTest() {
        Mockito.when(userDataRepository.existsByLogin(Mockito.nullable(String.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> registrationService.registerUser(null));
    }

}
