package com.senla.authorization.controller;

import com.senla.authorization.dto.AuthorityDto;
import com.senla.authorization.dto.RoleDto;
import com.senla.authorization.dto.controllers.AddOrRemoveAuthoritiesDto;
import com.senla.common.json.JsonMapper;
import org.hamcrest.Matchers;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false) // disable security filter chain
public class RolesControllerTests {

    @Container
    private final static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:14");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RolesController rolesController;

    @Autowired
    private AuthoritiesController authoritiesController;

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
    public void getAllTest() throws Exception {
        rolesController.addRole(new RoleDto(null, "ROLE1", null));
        rolesController.addRole(new RoleDto(null, "ROLE2", null));

        mockMvc.perform(MockMvcRequestBuilders.get("/authorization/roles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",
                        Matchers.greaterThanOrEqualTo(2)));
    }

    @Test
    public void getByIdTest() throws Exception {
        RoleDto authority = rolesController.addRole(new RoleDto(null, "ROLE3", null)).getBody();

        mockMvc.perform(MockMvcRequestBuilders.get("/authorization/roles/" + authority.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(authority));
    }

    @Test
    public void getByNotExistedIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authorization/roles/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createTest() throws Exception {
        RoleDto roleDto = new RoleDto(null, "ROLE4", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/roles")
                        .flashAttr("roleDto", roleDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(roleDto.getName()));
    }

    @Test
    public void createAlreadyExistedTest() throws Exception {
        RoleDto roleDto = new RoleDto(null, "ROLE5", null);
        rolesController.addRole(roleDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/roles")
                        .flashAttr("roleDto", roleDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateTest() throws Exception{
        RoleDto roleDto = new RoleDto(null, "ROLE6", null);
        RoleDto oldRole = rolesController.addRole(roleDto).getBody();
        roleDto = new RoleDto(oldRole.getId(), "ROLE6", null);

        mockMvc.perform(MockMvcRequestBuilders.put("/authorization/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.objectToJson(roleDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(roleDto));
    }

    @Test
    public void updateNotExistedTest() throws Exception{
        RoleDto roleDto = new RoleDto(0L, "ROLE6", null);

        mockMvc.perform(MockMvcRequestBuilders.put("/authorization/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.objectToJson(roleDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteTest() throws Exception {
        RoleDto roleDto = new RoleDto(null, "ROLE7", null);
        roleDto = rolesController.addRole(roleDto).getBody();

        mockMvc.perform(MockMvcRequestBuilders.delete("/authorization/roles/" + roleDto.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(roleDto.getId()));
    }

    @Test
    public void deleteNotExistedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/authorization/roles/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void addAuthorityToRoleTest() throws Exception{
        AuthorityDto auth1 = authoritiesController.createAuthority(new AuthorityDto(null, "ROLES_AUTH1")).getBody();
        AuthorityDto auth2 = authoritiesController.createAuthority(new AuthorityDto(null, "ROLES_AUTH2")).getBody();
        rolesController.addRole(new RoleDto(1L, "ROLE8", new ArrayList<>()));
        AddOrRemoveAuthoritiesDto form = new AddOrRemoveAuthoritiesDto(1L, Arrays.asList(auth1.getId(), auth2.getId()));

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/roles/authorities/adding")
                        .flashAttr("addOrRemoveAuthoritiesDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.equalTo(2)));
    }

    @Test
    public void addAuthorityToRoleIfOneNotExistTest() throws Exception{
        AuthorityDto auth1 = authoritiesController.createAuthority(new AuthorityDto(null, "ROLES_AUTH3")).getBody();
        rolesController.addRole(new RoleDto(1L, "ROLE8", new ArrayList<>()));
        AddOrRemoveAuthoritiesDto form = new AddOrRemoveAuthoritiesDto(1L, Arrays.asList(auth1.getId(), 0L));

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/roles/authorities/adding")
                        .flashAttr("addOrRemoveAuthoritiesDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.equalTo(1)));
    }

    @Test
    public void removeAuthorityFromRoleTest() throws Exception{
        AuthorityDto auth1 = authoritiesController.createAuthority(new AuthorityDto(null, "ROLES_AUTH4")).getBody();
        AuthorityDto auth2 = authoritiesController.createAuthority(new AuthorityDto(null, "ROLES_AUTH5")).getBody();
        rolesController.addRole(new RoleDto(1L, "ROLE8", Arrays.asList(auth1, auth2)));
        AddOrRemoveAuthoritiesDto form = new AddOrRemoveAuthoritiesDto(1L, Arrays.asList(auth1.getId(), auth2.getId()));

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/roles/authorities/removal")
                        .flashAttr("addOrRemoveAuthoritiesDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.equalTo(2)));
    }

    @Test
    public void removeAuthorityFromRoleIfOneNotBelongToItTest() throws Exception{
        AuthorityDto auth1 = authoritiesController.createAuthority(new AuthorityDto(null, "ROLES_AUTH6")).getBody();
        rolesController.addRole(new RoleDto(1L, "ROLE8", Collections.singletonList(auth1)));
        AddOrRemoveAuthoritiesDto form = new AddOrRemoveAuthoritiesDto(1L, Arrays.asList(auth1.getId(), 0L));

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization/roles/authorities/removal")
                        .flashAttr("addOrRemoveAuthoritiesDto", form))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.equalTo(1)));
    }

}
