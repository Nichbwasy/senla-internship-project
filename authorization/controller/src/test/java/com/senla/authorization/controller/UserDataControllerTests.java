package com.senla.authorization.controller;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.model.UserData;
import org.hamcrest.Matchers;
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
public class UserDataControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDataRepository userDataRepository;

    @Test
    public void getUserDataByIdTest() throws Exception {
        UserData user = userDataRepository.save(new UserData(null, "ud_login1", "ud_password1", null));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value(user.getLogin()));
    }

    @Test
    public void getUserDataByNotExistedIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllUsersData() throws Exception {
        userDataRepository.save(new UserData(null, "ud_login2", "ud_password3", null));
        userDataRepository.save(new UserData(null, "ud_login3", "ud_password3", null));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)));
    }

}
