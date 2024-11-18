package com.ti9.send.email.core.infrastructure.adapter.in.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {


    @Scheduled(cron = "0 36 20 * * *") // Todos os dias às 8h
    public void sendDailySummary() {
        System.out.println("Enviando email...");
    }
}

