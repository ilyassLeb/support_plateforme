package com.support.ticket_service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiAnalysisResponseDto {
    private String category;
    private String priority;
    private String suggestedResponse;
    private String summary;
}
