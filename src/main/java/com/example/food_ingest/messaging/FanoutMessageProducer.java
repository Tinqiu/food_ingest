package com.example.food_ingest.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("fanout")
public class FanoutMessageProducer {
}
