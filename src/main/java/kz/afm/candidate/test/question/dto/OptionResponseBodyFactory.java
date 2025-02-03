package kz.afm.candidate.test.question.dto;

import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.option.OptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OptionResponseBodyFactory {

    private final FileService fileService;

    public OptionResponseBody create(OptionEntity option) {
        return new OptionResponseBody(
                option.getId(),
                option.getNameRus(),
                option.getNameKaz(),
                option.isWithFile(),
                option.isWithFile() ? this.fileService.getBase64Url(option.getFileName()) : null,
                option.getIsCorrect()
        );
    }

    public Set<OptionResponseBody> createSet(List<OptionEntity> options) {
        return options.stream().map(this::create).collect(Collectors.toSet());
    }

}
