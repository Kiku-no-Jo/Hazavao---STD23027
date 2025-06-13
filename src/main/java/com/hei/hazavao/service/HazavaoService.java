package com.hei.hazavao.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HazavaoService {
  private static final String OPENAI_API_KEY = "dummy api_key";

  private final RestTemplate restTemplate = new RestTemplate();

  public String getDefinition(String teny) {
    String prompt = "Hazavao amin'ny teny malagasy tsotra ilay teny hoe: " + teny;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(OPENAI_API_KEY);

    Map<String, Object> message = new HashMap<>();
    message.put("role", "user");
    message.put("content", prompt);

    Map<String, Object> body = new HashMap<>();
    body.put("model", "gpt-3.5-turbo");
    body.put("messages", Collections.singletonList(message));

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

    try {
      ResponseEntity<Map> response =
          restTemplate.postForEntity(
              "https://api.openai.com/v1/chat/completions", entity, Map.class);

      if (response.getStatusCode() == HttpStatus.OK) {
        List<Map<String, Object>> choices =
            (List<Map<String, Object>>) response.getBody().get("choices");
        if (choices != null && !choices.isEmpty()) {
          Map<String, Object> messageMap = (Map<String, Object>) choices.get(0).get("message");
          return messageMap.get("content").toString().trim();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "Nisy olana tamin'ny fangatahana: " + e.getMessage();
    }

    return "Tsy afaka namaly ny fangatahana.";
  }
}
