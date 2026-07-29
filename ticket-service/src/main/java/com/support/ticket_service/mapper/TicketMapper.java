package com.support.ticket_service.mapper;


import com.support.ticket_service.dto.TicketRequestDto;
import com.support.ticket_service.dto.TicketResponseDto;
import com.support.ticket_service.entity.Ticket;
import com.support.ticket_service.entity.TicketStatus;

import java.time.LocalDateTime;




public class TicketMapper {

    private TicketMapper() {
    }

    public static Ticket toEntity(TicketRequestDto dto) {
        return Ticket.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .userId(dto.getUserId())
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static TicketResponseDto toResponseDto(Ticket ticket) {
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .userId(ticket.getUserId())
                .agentId(ticket.getAgentId())
                .createdAt(ticket.getCreatedAt())
                .category(ticket.getCategory())
                .priority(ticket.getPriority())
                .suggestedResponse(ticket.getSuggestedResponse())
                .summary(ticket.getSummary())
                .build();
    }
}