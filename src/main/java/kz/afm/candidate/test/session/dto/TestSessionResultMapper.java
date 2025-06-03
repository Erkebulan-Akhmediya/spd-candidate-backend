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
        SectionEntity section = this.sectionService.getByResult(result);
        return new TestSessionResultDto(result, section);
    }

    public TestSessionResultDto toDto(AssessmentEntity assessment) {
        TestSessionResultDto dto = new TestSessionResultDto(assessment);

        final int testWithFileTypeId = 6;
        if (assessment.getTestSessionAnswer().getTestSession().getVariant().getTest().getType().getId() == testWithFileTypeId) {
            dto.fileUrl = this.fileService.getBase64Url(dto.answer);
        }

        return dto;
    }
}
