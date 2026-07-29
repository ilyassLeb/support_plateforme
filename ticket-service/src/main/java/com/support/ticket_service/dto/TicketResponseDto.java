package com.support.ticket_service.dto;


import com.support.ticket_service.entity.TicketStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



import java.time.LocalDateTime;



@Getter
@Setter
@Builder
public class TicketResponseDto {

    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private Long userId;
    private Long agentId;
    private LocalDateTime createdAt;

    private String category;
    private String priority;
    private String suggestedResponse;
    private String summary;
}