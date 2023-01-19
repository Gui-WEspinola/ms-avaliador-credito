package io.github.guiwespinola.msavaliadorcredito.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CardRequestData;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestCardPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final Queue queueCardRequest; //queue configurada na classe configuração de MQConfig

    public void cardRequest(CardRequestData data) throws JsonProcessingException {
        var json = convertIntoJson(data);
        rabbitTemplate.convertAndSend(queueCardRequest.getName(), json);
    }

    private String convertIntoJson(CardRequestData data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}
