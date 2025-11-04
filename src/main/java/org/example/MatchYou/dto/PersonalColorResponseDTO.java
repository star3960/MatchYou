package org.example.MatchYou.dto;

import lombok.Builder;

@Builder
public record PersonalColorResponseDTO(

        String no1PersonalColor,
        int no1Percentage,
        String no2PersonalColor,
        int no2Percentage,
        String no3PersonalColor,
        int no3Percentage,
        String reason

) {

    public static PersonalColorResponseDTO from(String no1PersonalColor, int no1Percentage, String no2PersonalColor, int no2Percentage,
                                                String no3PersonalColor, int no3Percentage, String reason) {
        return PersonalColorResponseDTO.builder()
                .no1PersonalColor(no1PersonalColor)
                .no1Percentage(no1Percentage)
                .no2PersonalColor(no2PersonalColor)
                .no2Percentage(no2Percentage)
                .no3PersonalColor(no3PersonalColor)
                .no3Percentage(no3Percentage)
                .reason(reason)
                .build();
    }

}