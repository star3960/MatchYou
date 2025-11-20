package org.example.MatchYou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequestDTO {

    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Setter
    @Getter
    public static class Content {
        private Parts parts;
    }

    interface Parts {}

    @Getter
    @Setter
    public static class TextParts implements Parts {
        private String text;    // 프롬프트
    }

    @Getter
    @Setter
    public static class ImagePart implements Parts {
        private InlineData inlineData;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class InlineData {
        private String mimeType;
        private String data;
    }

    @Setter
    @Getter
    public static class GenerationConfig {
        private int candidate_count;    // 응답 후보
        private int max_output_tokens;  // 최대 응답 길이
        private double temperature;
    }

    // 일반 Gemini 호출 시 사용
    public GeminiRequestDTO(String prompt, InlineData inlineData) {
        TextParts textParts = new TextParts();
        textParts.setText(prompt);

        ImagePart imagePart = new ImagePart();
        imagePart.setInlineData(inlineData);

        Content textContent = new Content();
        textContent.setParts(textParts);
        Content imgContent = new Content();
        imgContent.setParts(imagePart);

        this.contents = new ArrayList<>();
        this.contents.add(textContent);
        this.contents.add(imgContent);

        this.generationConfig = new GenerationConfig();
        this.generationConfig.setCandidate_count(1);
        this.generationConfig.setMax_output_tokens(5000);
        this.generationConfig.setTemperature(0.4);
    }

}
