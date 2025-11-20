package org.example.MatchYou.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.MatchYou.dto.PersonalColorRequest;
import org.example.MatchYou.dto.PersonalColorResponseDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PersonalColorService {

    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    /*
    퍼스널컬러 측정 프롬프트를 작성해 AI로부터 답변을 받고 그 결과를 반환하는 메서드
     */
    public PersonalColorResponseDTO generatePersonalColor(PersonalColorRequest request) throws IOException {
        // 사진 추출
        byte[] image = request.getImage().getBytes();

        // 프롬프트 생성
        String prompt = "당신은 전문 퍼스널컬러 컨설턴트입니다. 사진 속 인물 얼굴의 퍼스널컬러를 진단해 JSON 형식으로 출력합니다.\n" +
                "퍼스널컬러 종류 : 봄 라이트, 봄 브라이트, 여름 라이트, 여름 브라이트, 여름 뮤트, 가을 뮤트, 가을 스트롱, 가을 딥, 겨울 브라이트, 겨울 딥\n" +
                "출력 JSON 형식 : \n" +
                "{\n" +
                "  \"no1PersonalColor\": \"컬러명\",\n" +
                "  \"no1Percentage\": 유사도 (10의 배수),\n" +
                "  \"no2PersonalColor\": \"컬러명\",\n" +
                "  \"no2Percentage\": 유사도 (10의 배수),\n" +
                "  \"no3PersonalColor\": \"컬러명\",\n" +
                "  \"no3Percentage\": 유사도 (10의 배수),\n" +
                "  \"reason\": \"측정 근거\"\n}" +
                "제약 사항:\n" +
                "유사도 총합은 100이며, 다른 텍스트는 절대 출력하지 않습니다. reason 내 줄바꿈은 \\n으로 이스케이프 처리하고 200자 이내로 논리적으로 작성합니다.";

        // Gemini 호출
        String content = geminiService.generateContent(prompt, image)
                .replaceAll("```json", "")     // 코드 시작 구분자 제거
                .replaceAll("```", "")  // 코드 종료 구분자 제거
                .trim();

        // 응답 구성
        PersonalColorResponseDTO response = objectMapper.readValue(content, PersonalColorResponseDTO.class);

        return response;
    }

}
