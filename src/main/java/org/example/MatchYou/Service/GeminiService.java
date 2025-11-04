package org.example.MatchYou.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.MatchYou.configure.RestTemplateConfig;
import org.example.MatchYou.dto.GeminiRequestDTO;
import org.example.MatchYou.dto.GeminiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.model.name}")
    private String model;
    @Value("${gemini.api.url}")
    private String baseUrl;
    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplateConfig restTemplateConfig;

    /*
    프롬프트에 대한 답변을 생성해 반환하는 메서드
     */
    public String generateContent(String prompt, byte[] image) throws JsonProcessingException {
        // 이미지 part 생성
        GeminiRequestDTO.InlineData inlineData = new GeminiRequestDTO.InlineData("image/jpeg", Base64.getEncoder().encodeToString(image));

        // request 생성
        GeminiRequestDTO request = new GeminiRequestDTO(prompt, inlineData);

        // 요청 전송 준비
        String url = baseUrl + "models/" + model + ":generateContent?key=" + apiKey;

        RestTemplate template = restTemplateConfig.geminiTemplate();
        GeminiResponseDTO response;
        response = template.postForObject(url, request, GeminiResponseDTO.class);   // request, body, execution

        // 요청 전송
        String content = response.getCandidates().get(0).getContent().getParts().get(0).getText();

        return content;
    }

}
