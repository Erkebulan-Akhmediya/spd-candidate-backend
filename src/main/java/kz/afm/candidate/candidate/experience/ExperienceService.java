package kz.afm.candidate.candidate.experience;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.dto.ExperienceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public void updateAll(CandidateEntity candidate, Set<ExperienceDto> experienceRequests) {
        this.experienceRepository.deleteAllByCandidate_IdentificationNumber(candidate.getIdentificationNumber());
        this.createAll(candidate, experienceRequests);
    }

    public void createAll(CandidateEntity candidate, Set<ExperienceDto> experienceRequests) {
        final List<ExperienceEntity> experiences = experienceRequests
                .stream()
                .map(
                        (ExperienceDto experience) -> ExperienceEntity.builder()
                                .startDate(experience.startDate)
                                .endDate(experience.endDate)
                                .companyName(experience.companyName)
                                .position(experience.position)
                                .candidate(candidate)
                                .build()
                )
                .toList();
        experienceRepository.saveAll(experiences);
    }

    public List<ExperienceEntity> getByCandidate(CandidateEntity candidate) {
        final String identificationNumber = candidate.getIdentificationNumber();
        return this.experienceRepository.findByCandidate_IdentificationNumber(identificationNumber);
    }

}
