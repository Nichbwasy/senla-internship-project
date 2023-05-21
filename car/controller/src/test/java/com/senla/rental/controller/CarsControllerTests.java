package com.senla.rental.controller;

import com.senla.car.controller.CarsController;
import com.senla.car.controller.advice.CarsControllerAdviceExceptionHandler;
import com.senla.car.dto.CarDto;
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
public class CarsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private CarsController carsController;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(carsController)
                .setControllerAdvice(new CarsControllerAdviceExceptionHandler())
                .build();
    }

    @Test
    public void addCarTest() throws Exception {
        CarDto carDto = new CarDto(null, "Desc1", 11000D, null, null, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .flashAttr("carDto", carDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(carDto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mileage").value(carDto.getMileage()));
    }

    @Test
    public void addAlreadyExistedCarTest() throws Exception {
        CarDto carDto = new CarDto(null, "Desc2", 12000D, null, null, null);
        carsController.addCar(carDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .flashAttr("carDto", carDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getCarTest() throws Exception {
        CarDto carDto = new CarDto(null, "Desc3", 13000D, null, null, null);
        carDto = carsController.addCar(carDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + carDto.getId())
                .flashAttr("carDto", carDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(carDto));
    }

    @Test
    public void getNotExistedCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateCarTest() throws Exception {
        CarDto carDto = new CarDto(null, "Desc4", 14000D, null, null, null);
        CarDto oldStatusDto = carsController.addCar(carDto).getBody();
        carDto = new CarDto(oldStatusDto.getId(), "Desc444", 14444D, null, null, null);

        mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(carDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(carDto));
    }

    @Test
    public void updateNotExistedCarTest() throws Exception {
        CarDto carDto = new CarDto(0L, "Desc0", 10000D,  null, null, null);

        mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(carDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteCarStatus() throws Exception {
        CarDto carDto = new CarDto(null, "Desc5", 15000D, null, null, null);
        carDto = carsController.addCar(carDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/" + carDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(carDto.getId()));
    }

    @Test
    public void deleteNotExistedCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllCarsTest() throws Exception {
        CarDto carDto1 = new CarDto(null, "Desc6", 16000D, null, null, null);
        CarDto carDto2 = new CarDto(null, "Desc7", 17000D, null, null, null);
        carsController.addCar(carDto1);
        carsController.addCar(carDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
}
