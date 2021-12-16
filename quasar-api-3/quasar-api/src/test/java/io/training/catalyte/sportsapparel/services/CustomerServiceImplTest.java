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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CustomerServiceImplTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
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
    public void getWishList() throws Exception {
        mockMvc
                .perform(get("/customers/1/wishlist"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void getWishList2() throws Exception {
        mockMvc
                .perform(get("/customers/2/wishlist"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(okStatus)
                .andExpect(expectedType);
    }

    @Test
    public void addToWishList() throws Exception {
        String json = "{\"id\":3411,\"price\":56.37,\"name\":\"Fashionable Running Headband\","
                + "\"description\":\"This running headband for men is extremely fashionable. "
                + "You're going to love it!\",\"demographic\":\"Men\",\"category\":\"Running\","
                + "\"type\":\"Headband\",\"releaseDate\":\"2017-01-01\",\"primaryColorCode\":\"#f9845b\","
                + "\"secondaryColorCode\":\"#39add1\",\"styleNumber\":\"sc31982\","
                + "\"globalProductCode\":\"po-8956920\"}";

        mockMvc
                .perform(put("/customers/2/wishlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.wishList[2]", is(3411)))
                .andExpect(createdStatus)
                .andExpect(expectedType);
    }
}