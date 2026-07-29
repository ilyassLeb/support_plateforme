package com.support.ticket_service.service;



import com.support.ticket_service.dto.TicketRequestDto;
import com.support.ticket_service.dto.TicketResponseDto;

import java.util.List;

public interface TicketService {

    TicketResponseDto createTicket(TicketRequestDto requestDto);

    List<TicketResponseDto> getAllTickets();

    TicketResponseDto getTicketById(Long id);

    List<TicketResponseDto> getTicketsByUserId(Long userId);
}
