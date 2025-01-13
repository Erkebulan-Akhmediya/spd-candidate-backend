package kz.afm.candidate.candidate.status;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Order(3)
@Component
public class CandidateStatusInitializer implements CommandLineRunner {

    private final CandidateStatusService candidateStatusService;

    @Override
    public void run(String... args) throws Exception {
        this.candidateStatusService.createIfNotExists(1, "Новый кандидат", "Жаңа үміткер");
        this.candidateStatusService.createIfNotExists(2, "На проверке ВБ", "ЖҚ тексерісінде");
        this.candidateStatusService.createIfNotExists(3, "На согласовании", "Бекітуде");
        this.candidateStatusService.createIfNotExists(4, "Согласован", "Бекітілді");
        this.candidateStatusService.createIfNotExists(5, "Отказан", "Бас тартылды");
    }

}
