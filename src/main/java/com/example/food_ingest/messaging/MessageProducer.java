package com.example.food_ingest.messaging;

import com.example.food_ingest.parsing.BrandedFoodLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("direct")
public class MessageProducer implements IMessageProducer {
    private final RabbitTemplate template;
    private final BrandedFoodLoader foodLoader;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("${rabbit.exchange.type}")
    private String exchangeType;

    @Override
    public void run() {
        try (var ignored = setupExchangeIfNecessary()) {
            foodLoader.loadFile().map(food -> {
                try {
                    return mapper.writeValueAsString(food);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(foodmsg -> template.convertAndSend("branded-food-exchange", "", foodmsg));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection setupExchangeIfNecessary() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        var conn = connectionFactory.newConnection("brandedFoodProductionConn");
        var channel = conn.createChannel();
        channel.exchangeDeclare("branded-food-exchange", exchangeType);
        log.info("Connection successfully acquired");
        return conn;
    }
}
