package com.senla.authorization.controller;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.registrations.LogInUserDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.RegistrationService;
import com.senla.authorization.service.encoders.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class AuthorizationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDataRepository userDataRepository;

    @Test
    public void logInUserTest() throws Exception {
        String password = PasswordEncoder.encodeString("password1");
        userDataRepository.save(new UserData(null, "login1", password, new ArrayList<>()));

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization")
                .flashAttr("logInUserDto", new LogInUserDto("login1", "password1")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    public void logInUserIfUserNotRegisteredTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authorization")
                        .flashAttr("logInUserDto", new LogInUserDto("login2", "password2")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
