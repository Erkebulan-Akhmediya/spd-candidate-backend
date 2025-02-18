package kz.afm.candidate.candidate.education;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.dto.EducationDto;
import kz.afm.candidate.candidate.education.type.EducationTypeEntity;
import kz.afm.candidate.candidate.education.type.EducationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationTypeService educationTypeService;

    public void updateAll(CandidateEntity candidate, Set<EducationDto> educations) {
        this.educationRepository.deleteAllByCandidate(candidate);
        this.createAll(candidate, educations);
    }

    public void createAll(CandidateEntity candidate, Set<EducationDto> educations) {
        final Map<Integer, EducationTypeEntity> typesMap = this.educationTypeService.getAllMap();
        final List<EducationEntity> entities = educations
                .stream()
                .map(
                        (EducationDto education) -> {
                            final EducationTypeEntity type = typesMap.get(education.getType());
                            return new EducationEntity(
                                    type,
                                    education.startDate,
                                    education.endDate,
                                    education.organization,
                                    education.major,
                                    candidate
                            );
                        }
                ).toList();
        this.educationRepository.saveAll(entities);
    }

    public List<EducationEntity> getAllByCandidate(CandidateEntity candidate) {
        return this.educationRepository.findAllByCandidate(candidate);
    }

}
