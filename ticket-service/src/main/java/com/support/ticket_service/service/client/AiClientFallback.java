package com.support.ticket_service.service.client;


import com.support.ticket_service.dto.AiAnalysisRequestDto;
import com.support.ticket_service.dto.AiAnalysisResponseDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AiClientFallback implements FallbackFactory<AiClient> {

    @Override
    public AiClient create(Throwable cause) {
        return new AiClient() {
            @Override
            public AiAnalysisResponseDto analyzeTicket(AiAnalysisRequestDto request) {
                System.err.println("Erreur appel AI-SERVICE : " + cause.getMessage());

                return new AiAnalysisResponseDto();
            }
        };
    }
}