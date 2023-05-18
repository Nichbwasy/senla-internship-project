package controller;

import com.senla.car.controller.TypesController;
import com.senla.car.controller.advice.CarsControllerAdviceExceptionHandler;
import com.senla.car.dto.TypeDto;
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
public class TypesControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private TypesController typesController;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(typesController)
                .setControllerAdvice(new CarsControllerAdviceExceptionHandler())
                .build();
    }

    @Test
    public void addTypeTest() throws Exception {
        TypeDto typeDto = new TypeDto(null, "TYPE1");

        mockMvc.perform(MockMvcRequestBuilders.post("/types")
                .flashAttr("typeDto", typeDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(typeDto.getName()));
    }

    @Test
    public void addAlreadyExistedTypeTest() throws Exception {
        TypeDto typeDto = new TypeDto(null, "TYPE2");
        typesController.addType(typeDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/types")
                .flashAttr("typeDto", typeDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getTypeTest() throws Exception {
        TypeDto typeDto = new TypeDto(null, "TYPE3");
        typeDto = typesController.addType(typeDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/types/" + typeDto.getId())
                .flashAttr("typeDto", typeDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(typeDto));
    }

    @Test
    public void getNotExistedTypeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/types/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateTypeTest() throws Exception {
        TypeDto typeDto = new TypeDto(null, "TYPE4");
        TypeDto oldTypeDto = typesController.addType(typeDto).getBody();
        typeDto = new TypeDto(oldTypeDto.getId(), "NEW_TYPE4");

        mockMvc.perform(MockMvcRequestBuilders.put("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(typeDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(typeDto));
    }

    @Test
    public void updateNotExistedTypeTest() throws Exception {
        TypeDto typeDto = new TypeDto(0L, "TYPE1");

        mockMvc.perform(MockMvcRequestBuilders.put("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(typeDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteTypeTest() throws Exception {
        TypeDto typeDto = new TypeDto(null, "TYPE5");
        typeDto = typesController.addType(typeDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/types/" + typeDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(typeDto.getId()));
    }

    @Test
    public void deleteNotExistedTypeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/types/0" ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllTypesTest() throws Exception {
        TypeDto typeDto1 = new TypeDto(null, "TYPE6");
        TypeDto typeDto2 = new TypeDto(null, "TYPE7");
        typesController.addType(typeDto1);
        typesController.addType(typeDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/types"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }

}
