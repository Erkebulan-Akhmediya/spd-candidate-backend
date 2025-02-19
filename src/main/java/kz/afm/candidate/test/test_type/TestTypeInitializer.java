package kz.afm.candidate.test.test_type;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TestTypeInitializer implements CommandLineRunner {

    private final TestTypeRepository testTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        final boolean automaticallyEvaluated = true;
        List<TestTypeEntity> types = new LinkedList<>();

        TestTypeEntity type = new TestTypeEntity(
                1,
                "Открытые",
                "Ашық",
                !automaticallyEvaluated
        );
        types.add(type);

        type = new TestTypeEntity(
                2,
                "Без правильного варианта",
                "Дұрыс жауабы жоқ",
                automaticallyEvaluated
        );
        types.add(type);

        type = new TestTypeEntity(
                3,
                "С одним правильным вариантом",
                "Бір дұрыс жауабы бар",
                automaticallyEvaluated
        );
        types.add(type);

        type = new TestTypeEntity(
                4,
                "С несколькими правильными вариантами",
                "Бірнеше дұрыс жауабы бар",
                automaticallyEvaluated
        );
        types.add(type);

        type = new TestTypeEntity(
                5,
                "Рапределение баллов",
                "Балл тарату",
                automaticallyEvaluated
        );
        types.add(type);

        type = new TestTypeEntity(
                6,
                "Файл",
                "Файл",
                !automaticallyEvaluated
        );
        types.add(type);

        this.testTypeRepository.saveAll(types);
    }

}
