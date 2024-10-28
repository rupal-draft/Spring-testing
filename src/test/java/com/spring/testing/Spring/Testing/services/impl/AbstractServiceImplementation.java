package com.spring.testing.Spring.Testing.services.impl;

import com.spring.testing.Spring.Testing.TestContainerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
@ExtendWith(MockitoExtension.class)
public class AbstractServiceImplementation {
    @Spy
    ModelMapper modelMapper;
}
