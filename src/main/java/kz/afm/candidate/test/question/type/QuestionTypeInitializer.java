package kz.afm.candidate.test.question.type;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Order(4)
@Component
public class QuestionTypeInitializer implements CommandLineRunner {

    private final QuestionTypeRepository questionTypeRepository;

    @Override
    public void run(String... args) throws Exception {

        List<QuestionTypeEntity> types = new LinkedList<>() {{
            add(new QuestionTypeEntity(1, "Без ответа", "Жауапсыз"));
            add(new QuestionTypeEntity(2, "Открытый", "Ашық"));
            add(new QuestionTypeEntity(3, "Без правильного варианта", "Дұрыс жауабы жоқ"));
            add(new QuestionTypeEntity(4, "С одним правильным вариантом", "Бір дұрыс жауабы бар"));
            add(new QuestionTypeEntity(5, "С несколькими правильными вариантами", "Бірнеше дұрыс жауабы бар"));
        }};

        this.questionTypeRepository.saveAll(types);

    }

}
