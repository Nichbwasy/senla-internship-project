package com.senla.rental.controller;

import com.senla.common.json.JsonMapper;
import com.senla.rental.dto.RequestDto;
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

import java.sql.Timestamp;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class RequestsControllerTests {

    @Container
    private final static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:14");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestsController requestsController;

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
    public void addRequestTest() throws Exception {
        RequestDto requestDto = new RequestDto(
                null,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/requests")
                .flashAttr("requestDto", requestDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(requestDto.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(requestDto.getCarId()));
    }

    @Test
    public void addAlreadyExistedRequestTest() throws Exception {
        RequestDto requestDto = new RequestDto(
                null,
                2L,
                2L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        requestsController.addRequest(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/requests")
                .flashAttr("requestDto", requestDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRequestTest() throws Exception {
        RequestDto requestDto = new RequestDto(
                null,
                3L,
                3L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        requestDto = requestsController.addRequest(requestDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/requests/" + requestDto.getId())
                .flashAttr("requestDto", requestDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(requestDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(requestDto.getCarId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(requestDto.getUserId()));
    }

    @Test
    public void getNotExistedRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/requests/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRequestTest() throws Exception {
        RequestDto requestDto = new RequestDto(
                null,
                4L,
                4L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        RequestDto oldTypeDto = requestsController.addRequest(requestDto).getBody();
        requestDto = new RequestDto(
                oldTypeDto.getId(),
                44L,
                44L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(requestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(requestDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(requestDto.getCarId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(requestDto.getUserId()));
    }

    @Test
    public void updateNotExistedRequestTest() throws Exception {
        RequestDto requestDto = new RequestDto(
                0L,
                0L,
                0L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(requestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteRequestTest() throws Exception {
        RequestDto requestDto = new RequestDto(
                null,
                5L,
                5L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        requestDto = requestsController.addRequest(requestDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/requests/" + requestDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(requestDto.getId()));
    }

    @Test
    public void deleteNotExistedRequestTest() throws Exception {
        RequestDto requestDto = new RequestDto(
                0L,
                0L,
                0L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.delete("/requests/" + requestDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllRequestsTest() throws Exception {
        RequestDto requestStatusDto1 = new RequestDto(
                null,
                6L,
                6L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        RequestDto requestStatusDto2 = new RequestDto(
                null,
                7L,
                7L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        requestsController.addRequest(requestStatusDto1);
        requestsController.addRequest(requestStatusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/requests"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
