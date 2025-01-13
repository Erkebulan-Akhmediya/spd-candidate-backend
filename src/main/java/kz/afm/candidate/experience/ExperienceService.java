package kz.afm.candidate.experience;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.dto.ExperienceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public void createAll(CandidateEntity candidate, Set<ExperienceRequest> experienceRequests) {
        final List<ExperienceEntity> experiences = experienceRequests
                .stream()
                .map(
                        (ExperienceRequest experience) -> ExperienceEntity.builder()
                                .startDate(experience.getStartDate())
                                .endDate(experience.getEndDate())
                                .companyName(experience.getCompanyName())
                                .position(experience.getPosition())
                                .candidate(candidate)
                                .build()
                )
                .toList();
        experienceRepository.saveAll(experiences);
    }

}
