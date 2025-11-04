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
        String prompt = "당신은 전문 퍼스널컬러 컨설턴트입니다. 아래 순서에 따른 진단 결과를 JSON 형식으로 출력합니다.\n" +
                "업무 : 사진 속 인물 얼굴의 퍼스널컬러를 진단합니다.\n" +
                "퍼스널컬러 종류로는 봄 라이트, 봄 브라이트, 여름 라이트, 여름 브라이트, 여름 뮤트, 가을 뮤트, 가을 스트롱, 가을 딥, 겨울 브라이트, 겨울 딥이 있습니다.\n" +
                "출력 형식 (JSON) : \n" +
                "{\n" +
                "  \"no1PersonalColor\": \"가장 근접한 퍼스널컬러\",\n" +
                "  \"no1Percentage\": no1PersonalColor의 유사도,\n" +
                "  \"no2PersonalColor\": \"2번째로 근접한 퍼스널컬러\",\n" +
                "  \"no2Percentage\": no2PersonalColor의 유사도,\n" +
                "  \"no3PersonalColor\": \"3번째로 근접한 퍼스널컬러\",\n" +
                "  \"no3Percentage\": no3PersonalColor의 유사도,\n" +
                "  \"reason\": \"측정 근거\"\n}" +
                "출력 예시 (JSON) : \n" +
                "{\n" +
                "  \"no1PersonalColor\": \"봄 웜 브라이트\",\n" +
                "  \"no1Percentage\": 70,\n" +
                "  \"no2PersonalColor\": \"봄 웜 라이트\",\n" +
                "  \"no2Percentage\": 30,\n" +
                "  \"no3PersonalColor\": \"여름 쿨 라이트\",\n" +
                "  \"no3Percentage\": 0,\n" +
                "\"reason\": \"1. 색상 온도 : 웜 톤\n" +
                "- 피부 톤에서 노란기가 뚜렷하게 감지되며, 이는 웜 베이스의 가장 강력한 증거입니다.\n" +
                "- 웜 톤 계열의밝고 따뜻한 미디엄 브라운의 머리카락이 얼굴을 화사하게 돋보이게 합니다.\n" +
                "\n" +
                "2. 명도와 채도 : 고명도/고채도 \n" +
                "- 피부가 전반적으로 매우 밝아 라이트나 브라이트 톤에 적합합니다.\n" +
                "- 맑고 밝은 피부와 선명한 색조의 조화는 봄 브라이트의 핵심 근거입니다.\n" +
                "\n" +
                "3. 대비감 및 이미지 : 활력 있고 생기 있는 대비\n" +
                "- 이목구비가 뚜렷하지만, 머리카락과 눈동자에서 극단적인 명도 대비가 드러나지는 않습니다. 부드러운 웜톤 컬러 속에서 선명한 색조가 대비되어 발랄하고 생기 있는 이미지를 형성합니다.\n" +
                "제약 사항\n" +
                "1. 출력은 반드시 위 JSON 형식으로 하며, 그 외 텍스트는 절대 출력하지 않는다. 또한 reason 내 줄바꿈은 반드시 \\n으로 이스케이프 처리한다.\n" +
                "2. 유사도 합은 100이 되도록 하며 10의 배수로 한다.\n" +
                "3. reason은 300자 미만으로 간결하고 논리적으로 작성한다.";

        // Gemini 호출
        long startTime = System.currentTimeMillis();    // 시작 시간

        String content = geminiService.generateContent(prompt, image)
                .replaceAll("```json", "")     // 코드 시작 구분자 제거
                .replaceAll("```", "")  // 코드 종료 구분자 제거
                .trim();

        long endTime = System.currentTimeMillis();  // 종료 시간
        System.out.println((endTime - startTime) / 1000 + "sec");   // Gemini 응답 시간 출력

        // 응답 구성
        PersonalColorResponseDTO response = objectMapper.readValue(content, PersonalColorResponseDTO.class);

        return response;
    }

}
