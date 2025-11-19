package ru.em.demo.kafkaappenderdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class KafkaAppenderDemoApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(KafkaAppenderDemoApplication.class, args);
        log.debug("Test debug message");
        log.info("Test info message");
        log.warn("Test warn message");
        log.error("Test error message");

        Thread.sleep(10_000);
        System.out.println("Finished");
    }
}