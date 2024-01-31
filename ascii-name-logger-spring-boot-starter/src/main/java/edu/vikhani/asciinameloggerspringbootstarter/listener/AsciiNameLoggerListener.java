package edu.vikhani.asciinameloggerspringbootstarter.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AsciiNameLoggerListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final String STANDARD_FONT_NAME = "standard";
    private final String projectName;
    private final String additionalText;
    private final String font;

    public AsciiNameLoggerListener(String projectName, String additionalText, String font) {
        super();
        this.projectName = projectName;
        this.additionalText = additionalText;
        this.font = font;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (Strings.isBlank(projectName)) {
            return;
        }

        String reqFont = Strings.isBlank(font)
                ? STANDARD_FONT_NAME
                : font;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://figlet-api.onrender.com/ascii?text=" + projectName + "&font=" + reqFont))
                .header("Accept", "application/json")
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::readAsciiArtResult)
                .join();
    }

    private void readAsciiArtResult(String json) {
        final JsonNode node;
        try {
            node = new ObjectMapper().readTree(json);
        } catch (JsonProcessingException e) {
            return;
        }

        if (node.has("ascii")) {
            System.out.println(node.get("ascii").asText());
        }
        if (!Strings.isBlank(additionalText)) {
            System.out.println(additionalText);
        }
    }
}