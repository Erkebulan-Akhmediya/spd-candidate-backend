package kz.afm.candidate.candidate.education;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.dto.EducationDto;
import kz.afm.candidate.candidate.education.type.EducationTypeEntity;
import kz.afm.candidate.candidate.education.type.EducationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationTypeService educationTypeService;

    public void updateAll(CandidateEntity candidate, Set<EducationDto> educations) {
        this.educationRepository.deleteByCandidate(candidate);
        this.createAll(candidate, educations);
    }

    public void createAll(CandidateEntity candidate, Set<EducationDto> educationDtoSet) {
        final Map<Integer, EducationTypeEntity> typesMap = this.educationTypeService.getAllMap();
        final List<EducationEntity> educations = educationDtoSet.stream()
                .map(
                        (EducationDto educationDto) -> {
                            final EducationTypeEntity type = typesMap.get(educationDto.type);
                            final Date endDate = educationDto.untilNow ? null : educationDto.endDate;
                            return new EducationEntity(
                                    type,
                                    educationDto.startDate,
                                    endDate,
                                    educationDto.organization,
                                    educationDto.major,
                                    candidate
                            );
                        }
                ).toList();
        this.educationRepository.saveAll(educations);
    }

    public List<EducationEntity> getAllByCandidate(CandidateEntity candidate) {
        return this.educationRepository.findAllByCandidate(candidate);
    }

    public void deleteByCandidateIdentificationNumber(String candidateIdentificationNumber) {
        this.educationRepository.deleteByCandidate_IdentificationNumber(candidateIdentificationNumber);
    }

}
