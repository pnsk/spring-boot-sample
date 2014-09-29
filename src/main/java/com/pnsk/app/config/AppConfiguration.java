package com.pnsk.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * the standard settings for the application.
 * Created by okuda_junko on 14/09/16.
 */
@Configuration
@Import(Application.class)
public class AppConfiguration {

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        return characterEncodingFilter;
    }
}
