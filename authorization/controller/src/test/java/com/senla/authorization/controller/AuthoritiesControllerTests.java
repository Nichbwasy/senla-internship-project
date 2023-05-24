package com.senla.authorization.controller;

import com.senla.authorization.dto.AuthorityDto;
import com.senla.authorization.model.Authority;
import com.senla.common.json.JsonMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class AuthoritiesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthoritiesController authoritiesController;

    @Test
    public void getAllAuthoritiesTest() throws Exception {
        authoritiesController.createAuthority(new AuthorityDto(null, "AUTHORITY1"));
        authoritiesController.createAuthority(new AuthorityDto(null, "AUTHORITY2"));

        mockMvc.perform(MockMvcRequestBuilders.get("/authorization/authorities"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)));
    }

    @Test
    public void getAuthorityByIdTest() throws Exception {
        AuthorityDto authority = authoritiesController.createAuthority(new AuthorityDto(null, "AUTHORITY3")).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/authorization/authorities/" + authority.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(authority));
    }

    @Test
    public void getAuthorityByNotExistedIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authorization/authorities/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createAuthorityTest() throws Exception {
        AuthorityDto authorityDto = new AuthorityDto(null, "AUTHORITY4");

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/authorities")
                        .flashAttr("authorityDto", authorityDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorityDto.getName()));
    }

    @Test
    public void createAlreadyExistedAuthorityTest() throws Exception {
        AuthorityDto authorityDto = new AuthorityDto(null, "AUTHORITY5");
        authoritiesController.createAuthority(authorityDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/authorities")
                        .flashAttr("authorityDto", authorityDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateAuthorityTest() throws Exception{
        AuthorityDto authorityDto = new AuthorityDto(null, "AUTHORITY6");
        AuthorityDto oldAuthority = authoritiesController.createAuthority(authorityDto).getBody();
        authorityDto = new AuthorityDto(oldAuthority.getId(), "AUTHORITY6");

        mockMvc.perform(MockMvcRequestBuilders.put("/authorization/authorities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.objectToJson(authorityDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(authorityDto));
    }

    @Test
    public void updateNotExistedAuthorityTest() throws Exception{
        AuthorityDto authorityDto = new AuthorityDto(0L, "AUTHORITY6");

        mockMvc.perform(MockMvcRequestBuilders.put("/authorization/authorities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.objectToJson(authorityDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteAuthorityTest() throws Exception {
        AuthorityDto authorityDto = new AuthorityDto(null, "AUTHORITY7");
        authorityDto = authoritiesController.createAuthority(authorityDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/authorization/authorities/" + authorityDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(authorityDto.getId()));
    }

    @Test
    public void deleteNotExistedAuthorityTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/authorization/authorities/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
