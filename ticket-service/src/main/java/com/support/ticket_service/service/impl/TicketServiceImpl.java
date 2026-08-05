package com.support.ticket_service.service.impl;




import com.support.ticket_service.dto.*;
import com.support.ticket_service.entity.Ticket;
import com.support.ticket_service.exception.InvalidUserException;
import com.support.ticket_service.exception.ResourceNotFoundException;
import com.support.ticket_service.mapper.TicketMapper;
import com.support.ticket_service.repository.TicketRepository;
import com.support.ticket_service.service.TicketService;
import com.support.ticket_service.service.client.AiClient;
import com.support.ticket_service.service.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import feign.FeignException;



import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserClient userClient;
    private final AiClient aiClient;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto requestDto) {
        validateUserExists(requestDto.getUserId());

        Ticket ticket = TicketMapper.toEntity(requestDto);

        enrichTicketWithAi(ticket);

        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketMapper.toResponseDto(savedTicket);
    }

    @Override
    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketMapper::toResponseDto)
                .toList();
    }

    @Override
    public TicketResponseDto getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        return TicketMapper.toResponseDto(ticket);
    }

    @Override
    public List<TicketResponseDto> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId)
                .stream()
                .map(TicketMapper::toResponseDto)
                .toList();
    }

    private void validateUserExists(Long userId) {
        try {
            UserResponseDto user = userClient.getUserById(userId);
            if (user == null || user.getId() == null) {
                throw new InvalidUserException("Utilisateur non trouvé avec  id: " + userId);
            }
        } catch (FeignException.NotFound ex) {
            throw new InvalidUserException("Utilisateur non trouvé avec  id: " + userId);
        } catch (FeignException ex) {
            throw new RuntimeException("erreur user-service: " + ex.getMessage());
        }
    }

    private void enrichTicketWithAi(Ticket ticket) {
        AiAnalysisRequestDto aiRequest = new AiAnalysisRequestDto();
        aiRequest.setTitle(ticket.getTitle());
        aiRequest.setDescription(ticket.getDescription());

        AiAnalysisResponseDto aiResponse = aiClient.analyzeTicket(aiRequest);
        System.out.println("il est la ");

        if (aiResponse != null) {
            ticket.setCategory(aiResponse.getCategory());
            ticket.setPriority(aiResponse.getPriority());
            ticket.setSuggestedResponse(aiResponse.getSuggestedResponse());
            ticket.setSummary(aiResponse.getSummary());
        }
    }
}