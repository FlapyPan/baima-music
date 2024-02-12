package com.baima.music;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
public class BaimaMusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaimaMusicApplication.class, args);
        log.info("启动完成...");
    }
}
