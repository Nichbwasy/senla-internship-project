package controller;

import com.senla.car.controller.StatusesController;
import com.senla.car.controller.advice.CarsControllerAdviceExceptionHandler;
import com.senla.car.dto.StatusDto;
import com.senla.common.json.JsonMapper;
import controller.configs.CarControllersTestsConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitConfig(CarControllersTestsConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class StatusesControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private StatusesController statusesController;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(statusesController)
                .setControllerAdvice(new CarsControllerAdviceExceptionHandler())
                .build();
    }

    @Test
    public void addStatusTest() throws Exception {
        StatusDto statusDto = new StatusDto(null, "STATUS1");

        mockMvc.perform(MockMvcRequestBuilders.post("/statuses")
                .flashAttr("statusDto", statusDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(statusDto.getName()));
    }

    @Test
    public void addAlreadyExistedStatusTest() throws Exception {
        StatusDto statusDto = new StatusDto(null, "STATUS2");
        statusesController.addStatus(statusDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/statuses")
                .flashAttr("statusDto", statusDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getStatusTest() throws Exception {
        StatusDto statusDto = new StatusDto(null, "STATUS3");
        statusDto = statusesController.addStatus(statusDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/statuses/" + statusDto.getId())
                .flashAttr("statusDto", statusDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(statusDto));
    }

    @Test
    public void getNotExistedStatusTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/statuses/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateStatusTest() throws Exception {
        StatusDto statusDto = new StatusDto(null, "STATUS4");
        StatusDto oldStatusDto = statusesController.addStatus(statusDto).getBody();
        statusDto = new StatusDto(oldStatusDto.getId(), "NEW_STATUS4");

        mockMvc.perform(MockMvcRequestBuilders.put("/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(statusDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(statusDto));
    }

    @Test
    public void updateNotExistedStatusTest() throws Exception {
        StatusDto statusDto = new StatusDto(0L, "STATUS0");

        mockMvc.perform(MockMvcRequestBuilders.put("/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(statusDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteTypeStatus() throws Exception {
        StatusDto statusDto = new StatusDto(null, "STATUS5");
        statusDto = statusesController.addStatus(statusDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/statuses/" + statusDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(statusDto.getId()));
    }

    @Test
    public void deleteNotExistedStatusTest() throws Exception {
        StatusDto statusDto = new StatusDto(0L, "STATUS0");

        mockMvc.perform(MockMvcRequestBuilders.delete("/statuses/" + statusDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllStatusesTest() throws Exception {
        StatusDto statusDto1 = new StatusDto(null, "STATUS6");
        StatusDto statusDto2 = new StatusDto(null, "STATUS7");
        statusesController.addStatus(statusDto1);
        statusesController.addStatus(statusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/statuses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
