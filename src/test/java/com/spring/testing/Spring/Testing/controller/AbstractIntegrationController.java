package com.spring.testing.Spring.Testing.controller;

import com.spring.testing.Spring.Testing.TestContainerConfig;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
@Import(TestContainerConfig.class)
public class AbstractIntegrationController {
    @Autowired
    WebTestClient webTestClient;
    @Spy
    ModelMapper modelMapper;
}
