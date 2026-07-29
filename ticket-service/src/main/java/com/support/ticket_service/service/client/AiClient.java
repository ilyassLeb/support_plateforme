package com.support.ticket_service.service.client;



import com.support.ticket_service.dto.AiAnalysisRequestDto;
import com.support.ticket_service.dto.AiAnalysisResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//falback
//@FeignClient(name = "AI-SERVICE", fallback = AiClientFallback.class)
@FeignClient(name = "AI-SERVICE", fallbackFactory = AiClientFallback.class)
public interface AiClient {

    @PostMapping("/api/ai/analyze-ticket")
    AiAnalysisResponseDto analyzeTicket(@RequestBody AiAnalysisRequestDto request);
}