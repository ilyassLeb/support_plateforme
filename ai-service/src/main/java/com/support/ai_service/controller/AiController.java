package com.support.ai_service.controller;


import com.support.ai_service.dto.TicketAnalysisRequestDto;
import com.support.ai_service.dto.TicketAnalysisResponseDto;
import com.support.ai_service.service.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiAnalysisService aiAnalysisService;

    @PostMapping("/analyze-ticket")
    public TicketAnalysisResponseDto analyzeTicket(@RequestBody TicketAnalysisRequestDto request) {

        return aiAnalysisService.analyzeTicket(request);
    }
}