package com.wnc.javlib.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JavTask {
    @Scheduled(cron = "0/10 * * * * *")
    public void testSyso() {
        System.out.println(System.currentTimeMillis());
    }
}
