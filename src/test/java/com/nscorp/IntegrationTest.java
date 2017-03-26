package com.nscorp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by raphael on 25/03/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnderApplication.class)
@WebAppConfiguration
public abstract class IntegrationTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;
    @Autowired @Getter private ObjectMapper objectMapper;
    @Autowired @Getter private RestTemplate restTemplate;

    private @Getter MockMvc mockMvc;
    private @Getter MockRestServiceServer mockServer;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
}
