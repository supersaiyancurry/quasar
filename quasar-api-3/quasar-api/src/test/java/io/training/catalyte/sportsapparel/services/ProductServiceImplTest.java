package io.training.catalyte.sportsapparel.services;

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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProductServiceImplTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
    ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();
    ResultMatcher expectedType = MockMvcResultMatchers.content()
            .contentType(MediaType.APPLICATION_JSON_UTF8);

    @Before
    public void setUp() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void getByDemographic() throws Exception {
        mockMvc
                .perform(get("/products/demographic/men"))
                .andExpect(jsonPath("$[0].demographic", is("Men")))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getByDemographicAndCategory() throws Exception {
        mockMvc
                .perform(get("/products/demographic/women/category/golf"))
                .andExpect(jsonPath("$[0].demographic", is("Women")))
                .andExpect(jsonPath("$[0].category", is("Golf")))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getByDemographicAndType() throws Exception {
        mockMvc
                .perform(get("/products/demographic/women/type/belt"))
                .andExpect(jsonPath("$[0].type", is("Belt")))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getByDemographicContentType() throws Exception {
        mockMvc
                .perform(get(
                        "/products/demographic/men/category/soccer/type/hat"))
                .andExpect(jsonPath("$[0].demographic", is("Men")))
                .andExpect(jsonPath("$[0].category", is("Soccer")))
                .andExpect(jsonPath("$[0].type", is("Hat")))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getNewestProductsByCategory() throws Exception {
        mockMvc
                .perform(get("/products/newest/category/hockey"))
                .andExpect(jsonPath("$[0].releaseDate", is(greaterThan("$[5].releaseDate"))))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getNewestProductsByDemographic() throws Exception {
        mockMvc
                .perform(get("/products/newest/demographic/men"))
                .andExpect(jsonPath("$[0].releaseDate", is(greaterThan("$[5].releaseDate"))))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getBySearchTerm() throws Exception {
        mockMvc
                .perform(get("/products/search/wristband"))
                .andExpect(jsonPath("$[0].name", containsString("Wristband")))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getByMultipleSearchTerms() throws Exception {
        mockMvc
                .perform(get("/products/search/water resistant"))
                .andExpect(jsonPath("$[0].name", containsString("Water Resistant")))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }
}