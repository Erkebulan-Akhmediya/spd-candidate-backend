package kz.afm.candidate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CandidateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CandidateApplication.class, args);
    }

}
