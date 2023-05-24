package com.senla.authorization.controller;

import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class RegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RegistrationController registrationController;

    @Test
    public void registerUserTest() throws Exception{
        UserRegistrationDataDto form = new UserRegistrationDataDto("register_login1", "password1", "password1");

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/register")
                    .flashAttr("userRegistrationDataDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("register_login1"));

    }

    @Test
    public void registerAlreadyRegisteredUserTest() throws Exception{
        registrationController.registerUser(new UserRegistrationDataDto("register_login2", "password2", "password2"));
        UserRegistrationDataDto form = new UserRegistrationDataDto("register_login2", "password2", "password2");

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/register")
                        .flashAttr("userRegistrationDataDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

}
