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
        List<TestTypeEntity> types = new LinkedList<>(){{
            add(new TestTypeEntity(1, "Открытые", "Ашық"));
            add(new TestTypeEntity(2, "Без правильного варианта", "Дұрыс жауабы жоқ"));
            add(new TestTypeEntity(3, "С одним правильным вариантом", "Бір дұрыс жауабы бар"));
            add(new TestTypeEntity(4, "С несколькими правильными вариантами", "Бірнеше дұрыс жауабы бар"));
            add(new TestTypeEntity(5, "Рапределение баллов", "Балл тарату"));
        }};
        this.testTypeRepository.saveAll(types);
    }

}
