package com.skeleton.account.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PACKAGE_PRIVATE;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setMethodAccessLevel(PACKAGE_PRIVATE)
                .setFieldAccessLevel(PACKAGE_PRIVATE);
        return modelMapper;
    }
}
