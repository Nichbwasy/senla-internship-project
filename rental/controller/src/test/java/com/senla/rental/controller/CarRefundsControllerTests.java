package com.senla.rental.controller;

import com.senla.common.json.JsonMapper;
import com.senla.rental.controller.advice.RentalControllerAdviceExceptionHandler;
import com.senla.rental.dto.CarRefundDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class CarRefundsControllerTests {

    @Container
    private final static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:14");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarsRefundsController carsRefundsController;

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
        CarRefundDto carRefundDto = new CarRefundDto(
                null,
                1L, 
                1L,
                new Timestamp(System.currentTimeMillis()), 
                new Timestamp(System.currentTimeMillis()), 
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/car/refunds")
                .flashAttr("carRefundDto", carRefundDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(carRefundDto.getCarId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(carRefundDto.getUserId()));
    }

    @Test
    public void addAlreadyExistedRequestStatusTest() throws Exception {
        CarRefundDto carRefundDto = new CarRefundDto(
                null,
                2L,
                2L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        carsRefundsController.addCarRefund(carRefundDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/car/refunds")
                .flashAttr("carRefundDto", carRefundDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRequestStatusTest() throws Exception {
        CarRefundDto carRefundDto = new CarRefundDto(
                null,
                3L,
                3L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        carRefundDto = carsRefundsController.addCarRefund(carRefundDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/car/refunds/" + carRefundDto.getId())
                .flashAttr("carRefundDto", carRefundDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(carRefundDto.getCarId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(carRefundDto.getUserId()));
    }

    @Test
    public void getNotExistedRequestStatusTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/car/refunds/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRequestStatusTest() throws Exception {
        CarRefundDto carRefundDto = new CarRefundDto(
                null,
                4L,
                4L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        CarRefundDto old = carsRefundsController.addCarRefund(carRefundDto).getBody();
        carRefundDto = new CarRefundDto(
                old.getId(),
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/car/refunds")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(carRefundDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(carRefundDto.getCarId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(carRefundDto.getUserId()));
    }

    @Test
    public void updateNotExistedRequestStatusTest() throws Exception {
        CarRefundDto carRefundDto = new CarRefundDto(
                0L,
                0L,
                0L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/car/refunds")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(carRefundDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteRequestStatusTest() throws Exception {
        CarRefundDto carRefundDto = new CarRefundDto(
                null,
                5L,
                5L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        carRefundDto = carsRefundsController.addCarRefund(carRefundDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/car/refunds/" + carRefundDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(carRefundDto.getId()));
    }

    @Test
    public void deleteNotExistedRequestStatusTest() throws Exception {
        CarRefundDto carRefundDto = new CarRefundDto(
                0L,
                0L,
                0L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.delete("/car/refunds/" + carRefundDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllRequestStatusesTest() throws Exception {
        CarRefundDto requestStatusDto1 = new CarRefundDto(
                null,
                6L,
                6L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        CarRefundDto requestStatusDto2 = new CarRefundDto(
                null,
                7L,
                7L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        carsRefundsController.addCarRefund(requestStatusDto1);
        carsRefundsController.addCarRefund(requestStatusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/car/refunds"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }    
    
}
