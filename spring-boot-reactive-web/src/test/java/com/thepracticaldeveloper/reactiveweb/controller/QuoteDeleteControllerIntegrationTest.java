package com.thepracticaldeveloper.reactiveweb.controller;

import com.thepracticaldeveloper.reactiveweb.configuration.QuijoteDataLoader;
import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import com.thepracticaldeveloper.reactiveweb.repository.QuoteMongoReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuoteDeleteControllerIntegrationTest {

    @MockBean
    private QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    // This one is not needed, but we need to override the real one to prevent the default behavior
    @MockBean
    private QuijoteDataLoader quijoteDataLoader;

    @LocalServerPort
    private int serverPort;

    private RestTemplate restTemplate;

    private String serverBaseUrl;

    @Before
    public void setUp() {
        serverBaseUrl = "http://localhost:" + serverPort;
        restTemplate = new RestTemplate();
    }

    @Test
    public void deleteRequest() {
        // given
        Map<String, String> requestParams = Collections.singletonMap("quoteId", "2");

        // when
        restTemplate.delete(serverBaseUrl + "/quotes-delete?", requestParams);

        // then
        verify(quoteMongoReactiveRepository).deleteById("2");
    }
}
