package org.example.MatchYou.Controller;

import lombok.RequiredArgsConstructor;
import org.example.MatchYou.Service.PersonalColorService;
import org.example.MatchYou.dto.PersonalColorRequest;
import org.example.MatchYou.dto.PersonalColorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PersonalColorController {

    private final PersonalColorService personalColorService;

    /*
    사진 속 인물의 퍼스널컬러를 측정해 근접한 퍼스널컬러 순위 및 비율과 측정 근거를 반환하는 메서드
     */
    @PostMapping("/api/personal-color")
    public ResponseEntity<PersonalColorResponseDTO> generatePersonalColor(@ModelAttribute PersonalColorRequest request) throws IOException {
        PersonalColorResponseDTO response = personalColorService.generatePersonalColor(request);

        return ResponseEntity.ok().body(response);
    }

}
