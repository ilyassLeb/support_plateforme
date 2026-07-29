package com.support.ai_service.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketAnalysisResponseDto {

    private String category;
    private String priority;
    private String suggestedResponse;
    private String summary;
}
