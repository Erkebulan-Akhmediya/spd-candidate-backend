package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.session.evaluation.assessment.AssessmentEntity;
import kz.afm.candidate.test.session.evaluation.result.ResultEntity;
import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import kz.afm.candidate.test.session.evaluation.section.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSessionResultMapper {

    private final SectionService sectionService;
    private final FileService fileService;

    public TestSessionResultDto toDto(ResultEntity result) {
        SectionEntity section = sectionService.getByResult(result);
        TestSessionResultDto dto = new TestSessionResultDto(result, section);

        if (result.getTestSession().getVariant().getTest().getType().getId() == 6) {
            dto.fileUrl = fileService.getBase64Url(dto.answer);
        }

        return dto;
    }

    public TestSessionResultDto toDto(AssessmentEntity assessment) {
        TestSessionResultDto dto = new TestSessionResultDto(assessment);

        if (assessment.getTestSessionAnswer().getTestSession().getVariant().getTest().getType().getId() == 6) {
            dto.fileUrl = fileService.getBase64Url(dto.answer);
        }

        return dto;
    }
}
