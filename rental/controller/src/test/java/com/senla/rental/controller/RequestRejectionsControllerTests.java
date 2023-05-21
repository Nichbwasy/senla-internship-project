package com.senla.rental.controller;

import com.senla.common.json.JsonMapper;
import com.senla.rental.controller.advice.RentalControllerAdviceExceptionHandler;
import com.senla.rental.dto.RequestRejectionDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class RequestRejectionsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestRejectionsController requestRejectionsController;

    @Test
    public void addRequestStatusTest() throws Exception {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(null, "TITLE1", "TEXT1");

        mockMvc.perform(MockMvcRequestBuilders.post("/requests/rejections")
                .flashAttr("requestRejectionDto", requestRejectionDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(requestRejectionDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(requestRejectionDto.getText()));
    }

    @Test
    public void addAlreadyExistedRequestStatusTest() throws Exception {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(null, "TITLE2", "TEXT2");
        requestRejectionsController.addRequestRejection(requestRejectionDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/requests/rejections")
                .flashAttr("requestRejectionDto", requestRejectionDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRequestStatusTest() throws Exception {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(null, "TITLE3", "TEXT3");
        requestRejectionDto = requestRejectionsController.addRequestRejection(requestRejectionDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/requests/rejections/" + requestRejectionDto.getId())
                .flashAttr("requestRejectionDto", requestRejectionDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(requestRejectionDto));
    }

    @Test
    public void getNotExistedRequestStatusTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/requests/rejections/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRequestStatusTest() throws Exception {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(null, "TITLE4", "TEXT4");
        RequestRejectionDto oldTypeDto = requestRejectionsController.addRequestRejection(requestRejectionDto).getBody();
        requestRejectionDto = new RequestRejectionDto(oldTypeDto.getId(), "NEW_TITLE4", "NEW_TEXT4");

        mockMvc.perform(MockMvcRequestBuilders.put("/requests/rejections")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(requestRejectionDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(requestRejectionDto));
    }

    @Test
    public void updateNotExistedRequestStatusTest() throws Exception {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(0L, "TITLE0", "TEXT0");

        mockMvc.perform(MockMvcRequestBuilders.put("/requests/rejections")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(requestRejectionDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteRequestStatusTest() throws Exception {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(null, "TITLE5", "TEXT5");
        requestRejectionDto = requestRejectionsController.addRequestRejection(requestRejectionDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/requests/rejections/" + requestRejectionDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(requestRejectionDto.getId()));
    }

    @Test
    public void deleteNotExistedRequestStatusTest() throws Exception {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(0L, "TITLE0", "TEXT0");

        mockMvc.perform(MockMvcRequestBuilders.delete("/requests/rejections/" + requestRejectionDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllRequestStatusesTest() throws Exception {
        RequestRejectionDto requestStatusDto1 = new RequestRejectionDto(null, "TITLE6", "TEXT6");
        RequestRejectionDto requestStatusDto2 = new RequestRejectionDto(null, "TITLE7", "TEXT7");
        requestRejectionsController.addRequestRejection(requestStatusDto1);
        requestRejectionsController.addRequestRejection(requestStatusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/requests/rejections"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
