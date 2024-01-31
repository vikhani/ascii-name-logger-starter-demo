package edu.vikhani.asciinameloggerspringbootstarter.config;

import edu.vikhani.asciinameloggerspringbootstarter.listener.AsciiNameLoggerListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AsciiNameLoggerProperties.class)
public class AsciiNameLoggerConfiguration {

    private final AsciiNameLoggerProperties properties;

    public AsciiNameLoggerConfiguration(AsciiNameLoggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public AsciiNameLoggerListener customListener() {
        return new AsciiNameLoggerListener(properties.getProjectName(), properties.getAdditionalText(), properties.getFont());
    }
}