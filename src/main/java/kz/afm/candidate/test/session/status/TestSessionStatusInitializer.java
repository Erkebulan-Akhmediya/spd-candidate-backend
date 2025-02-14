package kz.afm.candidate.test.session.status;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TestSessionStatusInitializer implements CommandLineRunner {

    private final TestSessionStatusRepository sessionStatusRepository;

    @Override
    public void run(String... args) throws Exception {
        List<TestSessionStatusEntity> statuses = new LinkedList<>(){{
            add(new TestSessionStatusEntity(1, "Начат", "Басталған"));
            add(new TestSessionStatusEntity(2, "Завершен", "Аяқталған"));
            add(new TestSessionStatusEntity(3, "Проверено", "Тексерілді"));
        }};
        this.sessionStatusRepository.saveAll(statuses);
    }

}
