package com.skazy.casseteteviet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CasseTeteVietApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CasseTeteVietApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("""
                    __   ____  _____ _____   ___      ______    ___ ______    ___      __ __  ____    ___ ______         ____    ____    __  __  _\s
                   /  ] /    |/ ___// ___/  /  _]    |      |  /  _]      |  /  _]    |  |  ||    |  /  _]      |       |    \\  /    |  /  ]|  |/ ]
                  /  / |  o  (   \\_(   \\_  /  [_     |      | /  [_|      | /  [_     |  |  | |  |  /  [_|      | _____ |  o  )|  o  | /  / |  ' /\s
                 /  /  |     |\\__  |\\__  ||    _]    |_|  |_||    _]_|  |_||    _]    |  |  | |  | |    _]_|  |_||     ||     ||     |/  /  |    \\\s
                /   \\_ |  _  |/  \\ |/  \\ ||   [_       |  |  |   [_  |  |  |   [_     |  :  | |  | |   [_  |  |  |_____||  O  ||  _  /   \\_ |     |
                \\     ||  |  |\\    |\\    ||     |      |  |  |     | |  |  |     |     \\   /  |  | |     | |  |         |     ||  |  \\     ||  .  |
                 \\____||__|__| \\___| \\___||_____|      |__|  |_____| |__|  |_____|      \\_/  |____||_____| |__|         |_____||__|__|\\____||__|\\_|
                                                                                                                                                  \s
                """);
    }
}
