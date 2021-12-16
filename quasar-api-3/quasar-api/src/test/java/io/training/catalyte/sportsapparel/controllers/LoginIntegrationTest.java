package io.training.catalyte.sportsapparel.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class LoginIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
    ResultMatcher badData = MockMvcResultMatchers.status().isBadRequest();
    ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();

    @Before
    public void setUp() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void login() throws Exception {

        String json = "{\"email\":\"jdoe@gmail.com\",\"password\":\"Password\"}";

        mockMvc
                .perform(post("/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(mvcResult -> containsString("bY4g-Kk335HNYFb2mCdtChN7p-Xxg5SHkzBYzsxe8Jk"))
                .andExpect(okStatus);
    }

    @Test
    public void loginWrongEmail() throws Exception {

        String json = "{\"email\":\"j@gmail.com\",\"password\":\"Password\"}";

        mockMvc
                .perform(post("/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(notFoundStatus);
    }

    @Test
    public void loginBadData() throws Exception {

        String json = "{\"email\":\"jail.com\",\"password\":\"Password\"}";

        mockMvc
                .perform(post("/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(badData);
    }
}