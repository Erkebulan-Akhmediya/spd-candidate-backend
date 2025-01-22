package kz.afm.candidate.test.option;

import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.dto.CreateOptionRequest;
import kz.afm.candidate.test.question.QuestionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final FileService fileService;
    private final OptionRepository optionRepository;

    public void create(QuestionEntity question, List<CreateOptionRequest> dtos) {
        List<OptionEntity> options = new LinkedList<>();
        if (dtos == null || dtos.isEmpty()) return;

        dtos.forEach((CreateOptionRequest dto) -> {
            try {
                final String fileName = dto.isWithFile() ? this.fileService.save(dto.getFile()) : null;
                options.add(
                        new OptionEntity(
                                dto.isWithFile(),
                                fileName,
                                dto.getNameRus(),
                                dto.getNameKaz(),
                                dto.getIsCorrect(),
                                question
                        )
                );
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Ошибка сохранения файла");
            }
        });
        this.optionRepository.saveAll(options);
    }

}
