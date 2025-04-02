package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.test.session.dto.TestSessionAnswerDto;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.test_type.TestTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestSessionAnswerMapper {

    @Autowired
    private FileService fileService;

    public TestSessionAnswerDto toDto(TestSessionAnswerEntity entity) {
        TestSessionAnswerDto dto = new TestSessionAnswerDto(entity);

        if (entity.getTestSession().getVariant().getTest().getType().getId() == 6) {
            dto.fileUrl = fileService.getBase64Url(dto.answer);
        }

        return dto;
    }
}
