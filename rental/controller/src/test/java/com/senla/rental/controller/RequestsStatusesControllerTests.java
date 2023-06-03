package com.senla.rental.controller;

import com.senla.common.json.JsonMapper;
import com.senla.rental.dto.RequestStatusDto;
import org.hamcrest.Matchers;
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
public class RequestsStatusesControllerTests {

    @Container
    private final static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:14");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestStatusesController requestStatusesController;

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
    public void addRequestStatusTest() throws Exception {
        RequestStatusDto requestStatusDto = new RequestStatusDto(null, "REQUEST_STATUS1");

        mockMvc.perform(MockMvcRequestBuilders.post("/requests/statuses")
                .flashAttr("requestStatusDto", requestStatusDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(requestStatusDto.getName()));
    }

    @Test
    public void addAlreadyExistedRequestStatusTest() throws Exception {
        RequestStatusDto requestStatusDto = new RequestStatusDto(null, "REQUEST_STATUS2");
        requestStatusesController.addRequestStatus(requestStatusDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/requests/statuses")
                .flashAttr("requestStatusDto", requestStatusDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRequestStatusTest() throws Exception {
        RequestStatusDto requestStatusDto = new RequestStatusDto(null, "REQUEST_STATUS3");
        requestStatusDto = requestStatusesController.addRequestStatus(requestStatusDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/requests/statuses/" + requestStatusDto.getId())
                .flashAttr("requestStatusDto", requestStatusDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(requestStatusDto));
    }

    @Test
    public void getNotExistedRequestStatusTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/requests/statuses/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRequestStatusTest() throws Exception {
        RequestStatusDto requestStatusDto = new RequestStatusDto(null, "REQUEST_STATUS4");
        RequestStatusDto oldTypeDto = requestStatusesController.addRequestStatus(requestStatusDto).getBody();
        requestStatusDto = new RequestStatusDto(oldTypeDto.getId(), "NEW_REQUEST_STATUS4");

        mockMvc.perform(MockMvcRequestBuilders.put("/requests/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(requestStatusDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(requestStatusDto));
    }

    @Test
    public void updateNotExistedRequestStatusTest() throws Exception {
        RequestStatusDto requestStatusDto = new RequestStatusDto(0L, "REQUEST_STATUS1");

        mockMvc.perform(MockMvcRequestBuilders.put("/requests/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(requestStatusDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteRequestStatusTest() throws Exception {
        RequestStatusDto requestStatusDto = new RequestStatusDto(null, "REQUEST_STATUS5");
        requestStatusDto = requestStatusesController.addRequestStatus(requestStatusDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/requests/statuses/" + requestStatusDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(requestStatusDto.getId()));
    }

    @Test
    public void deleteNotExistedRequestStatusTest() throws Exception {
        RequestStatusDto requestStatusDto = new RequestStatusDto(0L, "REQUEST_STATUS1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/requests/statuses/" + requestStatusDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllRequestStatusesTest() throws Exception {
        RequestStatusDto requestStatusDto1 = new RequestStatusDto(null, "REQUEST_STATUS6");
        RequestStatusDto requestStatusDto2 = new RequestStatusDto(null, "REQUEST_STATUS7");
        requestStatusesController.addRequestStatus(requestStatusDto1);
        requestStatusesController.addRequestStatus(requestStatusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/requests/statuses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
