package edu.vikhani.asciinameloggerspringbootstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ascii.name")
public class AsciiNameLoggerProperties {
    private final String projectName;
    private final String additionalText;
    private final String font;

    public AsciiNameLoggerProperties(String projectName, String additionalText, String font) {
        this.projectName = projectName;
        this.additionalText = additionalText;
        this.font = font;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public String getFont() {
        return font;
    }
}
