package com.senla.rental.controller;

import com.senla.car.controller.RegistrationsController;
import com.senla.car.controller.advice.CarsControllerAdviceExceptionHandler;
import com.senla.car.dto.RegistrationDto;
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
public class RegistrationsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private RegistrationsController registrationsController;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(registrationsController)
                .setControllerAdvice(new CarsControllerAdviceExceptionHandler())
                .build();
    }

    @Test
    public void addRegistrationTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                null,
                "NUM1", 
                "MOD1", 
                2001, 
                "BOD1", 
                2000, 
                1000, 
                null, 
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/registrations")
                .flashAttr("registrationDto", registrationDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(registrationDto.getNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(registrationDto.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseYear").value(registrationDto.getReleaseYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bodyNumber").value(registrationDto.getBodyNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allowedMaxMass").value(registrationDto.getAllowedMaxMass()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unladenMass").value(registrationDto.getUnladenMass()));
    }

    @Test
    public void addAlreadyExistedRegistrationTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                null,
                "NUM2",
                "MOD2",
                2002,
                "BOD2",
                2200,
                1200,
                null,
                null
        );
        registrationsController.addRegistration(registrationDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/registrations")
                .flashAttr("registrationDto", registrationDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRegistrationTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                null,
                "NUM3",
                "MOD3",
                2003,
                "BOD3",
                2300,
                1300,
                null,
                null
        );
        registrationDto = registrationsController.addRegistration(registrationDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/registrations/" + registrationDto.getId())
                .flashAttr("registrationDto", registrationDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(registrationDto));
    }

    @Test
    public void getNotExistedRegistrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registrations/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRegistrationTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                null,
                "NUM4",
                "MOD4",
                2004,
                "BOD4",
                2400,
                1400,
                null,
                null
        );
        RegistrationDto oldStatusDto = registrationsController.addRegistration(registrationDto).getBody();
        registrationDto = new RegistrationDto(
                null,
                "NUM44",
                "MOD44",
                2044,
                "BOD44",
                2444,
                1444,
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(registrationDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(registrationDto));
    }

    @Test
    public void updateNotExistedRegistrationTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                0L,
                "NUM0",
                "MOD0",
                2000,
                "BOD0",
                2000,
                1000,
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(registrationDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteRegistrationTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                null,
                "NUM5",
                "MOD5",
                2005,
                "BOD5",
                2500,
                1500,
                null,
                null
        );
        registrationDto = registrationsController.addRegistration(registrationDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/registrations/" + registrationDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(registrationDto.getId()));
    }

    @Test
    public void deleteNotExistedRegistrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/registrations/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllRegistrationsTest() throws Exception {
        RegistrationDto registrationDto1 = new RegistrationDto(
                null,
                "NUM6",
                "MOD6",
                2006,
                "BOD6",
                2600,
                1600,
                null,
                null
        );
        RegistrationDto registrationDto2 = new RegistrationDto(
                null,
                "NUM7",
                "MOD7",
                2007,
                "BOD7",
                2700,
                1700,
                null,
                null
        );
        registrationsController.addRegistration(registrationDto1);
        registrationsController.addRegistration(registrationDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/registrations"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)))
                .andReturn();
    }
    
}
