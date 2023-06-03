package com.senla.rental.controller;

import com.senla.common.json.JsonMapper;
import com.senla.rental.controller.advice.RentalControllerAdviceExceptionHandler;
import com.senla.rental.dto.RefundCompensationDto;
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

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class RefundCompensationsControllerTests {

    @Container
    private final static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:14");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RefundCompensationsController refundCompensationsController;

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
        RefundCompensationDto refundCompensationDto
                = new RefundCompensationDto(null, "Title1", "Text1", false, 10D);

        mockMvc.perform(MockMvcRequestBuilders.post("/car/refunds/compensations")
                .flashAttr("refundCompensationDto", refundCompensationDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(refundCompensationDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(refundCompensationDto.getText()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isPaid").value(refundCompensationDto.getIsPaid()));
    }

    @Test
    public void addAlreadyExistedRequestStatusTest() throws Exception {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(null, "Title2", "Text2", false, 20D);
        refundCompensationsController.addRefundCompensation(refundCompensationDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/car/refunds/compensations")
                .flashAttr("refundCompensationDto", refundCompensationDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRequestStatusTest() throws Exception {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(null, "Title3", "Text4", false, 30D);
        refundCompensationDto = refundCompensationsController.addRefundCompensation(refundCompensationDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/car/refunds/compensations/" + refundCompensationDto.getId())
                .flashAttr("refundCompensationDto", refundCompensationDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(refundCompensationDto));
    }

    @Test
    public void getNotExistedRequestStatusTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/car/refunds/compensations/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRequestStatusTest() throws Exception {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(null, "Title4", "Text4", false, 40D);
        RefundCompensationDto old = refundCompensationsController.addRefundCompensation(refundCompensationDto).getBody();
        refundCompensationDto =
                new RefundCompensationDto(old.getId(), "Title44", "Text44", true, 440D);

        mockMvc.perform(MockMvcRequestBuilders.put("/car/refunds/compensations")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(refundCompensationDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(refundCompensationDto));
    }

    @Test
    public void updateNotExistedRequestStatusTest() throws Exception {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(0L, "Title0", "Text0", false, 0D);

        mockMvc.perform(MockMvcRequestBuilders.put("/car/refunds/compensations")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(refundCompensationDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteRequestStatusTest() throws Exception {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(null, "Title5", "Text5", false, 50D);
        refundCompensationDto = refundCompensationsController.addRefundCompensation(refundCompensationDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/car/refunds/compensations/" + refundCompensationDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(refundCompensationDto.getId()));
    }

    @Test
    public void deleteNotExistedRequestStatusTest() throws Exception {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(0L, "Title0", "Text0", false, 0D);

        mockMvc.perform(MockMvcRequestBuilders.delete("/car/refunds/compensations/" + refundCompensationDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllRequestStatusesTest() throws Exception {
        RefundCompensationDto requestStatusDto1 =
                new RefundCompensationDto(null, "Title6", "Text6", false, 60D);
        RefundCompensationDto requestStatusDto2 =
                new RefundCompensationDto(null, "Title7", "Text7", false, 70D);
        refundCompensationsController.addRefundCompensation(requestStatusDto1);
        refundCompensationsController.addRefundCompensation(requestStatusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/car/refunds/compensations"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
