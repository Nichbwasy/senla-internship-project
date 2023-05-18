package controller;

import com.senla.car.controller.ColorsController;
import com.senla.car.controller.advice.CarsControllerAdviceExceptionHandler;
import com.senla.car.dto.ColorDto;
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
public class ColorsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private ColorsController colorsController;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(colorsController)
                .setControllerAdvice(new CarsControllerAdviceExceptionHandler())
                .build();
    }

    @Test
    public void addColorTest() throws Exception {
        ColorDto colorDto = new ColorDto(null, "COLOR1");

        mockMvc.perform(MockMvcRequestBuilders.post("/colors")
                .flashAttr("colorDto", colorDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(colorDto.getName()));
    }

    @Test
    public void addColorAlreadyExistedTypeTest() throws Exception {
        ColorDto colorDto = new ColorDto(null, "COLOR2");
        colorDto = colorsController.addColor(colorDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.post("/colors")
                .flashAttr("colorDto", colorDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getColorTest() throws Exception {
        ColorDto colorDto = new ColorDto(null, "COLOR3");
        colorDto = colorsController.addColor(colorDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/colors/" + colorDto.getId())
                .flashAttr("colorDto", colorDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(colorDto));
    }

    @Test
    public void getNotExistedColorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/colors/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateColorTest() throws Exception {
        ColorDto colorDto = new ColorDto(null, "COLOR4");
        ColorDto oldStatusDto = colorsController.addColor(colorDto).getBody();
        colorDto = new ColorDto(oldStatusDto.getId(), "NEW_COLOR4");

        mockMvc.perform(MockMvcRequestBuilders.put("/colors")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(colorDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(colorDto));
    }

    @Test
    public void updateNotExistedColorTest() throws Exception {
        ColorDto colorDto = new ColorDto(0L, "COLOR0");

        mockMvc.perform(MockMvcRequestBuilders.put("/colors")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(colorDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteColorStatus() throws Exception {
        ColorDto colorDto = new ColorDto(null, "COLOR5");
        colorDto = colorsController.addColor(colorDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/colors/" + colorDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(colorDto.getId()));
    }

    @Test
    public void deleteNotExistedColorTest() throws Exception {
        ColorDto colorDto = new ColorDto(0L, "COLOR0");

        mockMvc.perform(MockMvcRequestBuilders.delete("/colors/" + colorDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllColorsTest() throws Exception {
        ColorDto statusDto1 = new ColorDto(null, "COLOR6");
        ColorDto statusDto2 = new ColorDto(null, "COLOR7");
        colorsController.addColor(statusDto1);
        colorsController.addColor(statusDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/colors"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
