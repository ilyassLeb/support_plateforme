package com.support.ai_service.service;


import com.support.ai_service.dto.TicketAnalysisRequestDto;
import com.support.ai_service.dto.TicketAnalysisResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;




import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

    private final ChatClient.Builder chatClientBuilder;

    public TicketAnalysisResponseDto analyzeTicket(TicketAnalysisRequestDto request) {

        ChatClient chatClient = chatClientBuilder.build();

        String prompt = """
                Tu es un assistant de support client.

                Analyse le ticket suivant et retourne UNIQUEMENT un JSON valide.
                Ne retourne aucun texte avant ou après le JSON.
                Ne mets pas de markdown.
                Ne mets pas de balises ```json.
                Ne mets aucune explication.

                Format exact attendu :
                {
                  "category": "AUTHENTICATION|BILLING|TECHNICAL|ACCOUNT|OTHER",
                  "priority": "LOW|MEDIUM|HIGH",
                  "suggestedResponse": "texte court et professionnel",
                  "summary": "résumé court"
                }

                Règles :
                - category doit être une seule des valeurs autorisées
                - priority doit être une seule des valeurs autorisées
                - suggestedResponse doit être en français
                - summary doit être en français
                - réponse strictement en JSON valide

                Titre : %s
                Description : %s
                """.formatted(request.getTitle(), request.getDescription());

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return TicketAnalysisResponseDto.builder()
                .category(extractJsonField(response, "category"))
                .priority(extractJsonField(response, "priority"))
                .suggestedResponse(extractJsonField(response, "suggestedResponse"))
                .summary(extractJsonField(response, "summary"))
                .build();
    }

    private String extractJsonField(String json, String field) {
        String regex = "\"" + field + "\"\\s*:\\s*\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1).replace("\\n", "\n").replace("\\\"", "\"") : null;
    }
}