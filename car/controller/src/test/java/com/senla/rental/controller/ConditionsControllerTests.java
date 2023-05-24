package com.senla.rental.controller;

import com.senla.car.controller.ConditionsController;
import com.senla.car.controller.advice.CarsControllerAdviceExceptionHandler;
import com.senla.car.dto.ConditionDto;
import com.senla.common.json.JsonMapper;
import com.senla.rental.controller.configs.CarControllersTestsConfiguration;
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
public class ConditionsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private ConditionsController conditionsController;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(conditionsController)
                .setControllerAdvice(new CarsControllerAdviceExceptionHandler())
                .build();
    }

    @Test
    public void addConditionTest() throws Exception {
        ConditionDto conditionDto = new ConditionDto(null, "CONDITION11");

        mockMvc.perform(MockMvcRequestBuilders.post("/conditions")
                .flashAttr("conditionDto", conditionDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(conditionDto.getName()));
    }

    @Test
    public void addAlreadyExistedConditionTest() throws Exception {
        ConditionDto conditionDto = new ConditionDto(null, "CONDITION12");
        conditionsController.addCondition(conditionDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/conditions")
                .flashAttr("conditionDto", conditionDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getConditionTest() throws Exception {
        ConditionDto conditionDto = new ConditionDto(null, "CONDITION13");
        conditionDto = conditionsController.addCondition(conditionDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/conditions/" + conditionDto.getId())
                .flashAttr("conditionDto", conditionDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(conditionDto));
    }

    @Test
    public void getNotExistedConditionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/conditions/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateConditionTest() throws Exception {
        ConditionDto conditionDto = new ConditionDto(null, "CONDITION14");
        ConditionDto oldStatusDto = conditionsController.addCondition(conditionDto).getBody();
        conditionDto = new ConditionDto(oldStatusDto.getId(), "NEW_CONDITION14");

        mockMvc.perform(MockMvcRequestBuilders.put("/conditions")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(conditionDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(conditionDto));
    }

    @Test
    public void updateNotExistedConditionTest() throws Exception {
        ConditionDto conditionDto = new ConditionDto(0L, "CONDITION10");

        mockMvc.perform(MockMvcRequestBuilders.put("/conditions")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(conditionDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteConditionTest() throws Exception {
        ConditionDto conditionDto = new ConditionDto(null, "CONDITION15");
        conditionDto = conditionsController.addCondition(conditionDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/conditions/" + conditionDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(conditionDto.getId()));
    }

    @Test
    public void deleteNotExistedConditionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/conditions/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllConditionsTest() throws Exception {
        ConditionDto statusDto1 = new ConditionDto(null, "CONDITION16");
        ConditionDto statusDto2 = new ConditionDto(null, "CONDITION17");
        conditionsController.addCondition(statusDto1);
        conditionsController.addCondition(statusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/conditions"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
