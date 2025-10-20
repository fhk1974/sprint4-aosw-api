package com.fiap.aosw;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void fullFlow_register_login_create_list() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(Map.of("username","test","password","123"))))
                .andExpect(status().isOk());

        String token = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(Map.of("username","test","password","123"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String jwt = om.readTree(token).get("token").asText();

        mvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .content("{"title":"Minha tarefa"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Minha tarefa"));

        mvc.perform(get("/api/todos").header("Authorization","Bearer "+jwt))
                .andExpect(status().isOk());
    }
}
