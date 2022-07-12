package com.example.food_ingest;

import com.example.food_ingest.messaging.IMessageProducer;
import com.example.food_ingest.messaging.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class FoodIngestApplication {

    public static void main(String[] args) {
        try (var ctx = SpringApplication.run(FoodIngestApplication.class, args);) {
            var messageProducer = ctx.getBean(IMessageProducer.class);
            var mainThread = new Thread(messageProducer);
            var startTime = Instant.now();
			mainThread.start();
            mainThread.join();
            log.info("Finished loading branded foods, time taken: {} minutes", Duration.between(startTime, Instant.now()).toMinutes());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}
