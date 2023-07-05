package com.senla.authorization.controller;

import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class RegistrationControllerTests {

    @Container
    private final static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:14");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RegistrationController registrationController;

    /*
    As PostgreSQLContainer creates on random port, db name, username and password
    needs to replace some properties to connect to postgres database test container.
 */
    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void registerUserTest() throws Exception{
        UserRegistrationDataDto form = new UserRegistrationDataDto("register_login1", "password1", "email1", "password1");

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/register")
                    .flashAttr("userRegistrationDataDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("register_login1"));

    }

    @Test
    public void registerAlreadyRegisteredUserTest() throws Exception{
        registrationController.registerUser(new UserRegistrationDataDto("register_login2", "password2", "email2", "password2"));
        UserRegistrationDataDto form = new UserRegistrationDataDto("register_login2", "password2", "email2", "password2");

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/register")
                        .flashAttr("userRegistrationDataDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

}
