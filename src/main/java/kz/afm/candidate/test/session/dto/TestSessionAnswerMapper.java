package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import kz.afm.candidate.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSessionAnswerMapper {
    private final FileService fileService;

    public TestSessionAnswerDto toDto(TestSessionAnswerEntity entity) {
        TestSessionAnswerDto dto = new TestSessionAnswerDto(entity);

        try {
            var session = entity.getTestSession();
            var variant = session != null ? session.getVariant() : null;
            var test = variant != null ? variant.getTest() : null;
            var type = test != null ? test.getType() : null;

            if (type != null && type.getId() == 6 && dto.answer != null) {
                dto.fileUrl = fileService.getBase64Url(dto.answer);
            }
        } catch (Exception e) {
            System.err.println("Error while mapping answer ID " + entity.getId());
        }

        return dto;
    }
}
