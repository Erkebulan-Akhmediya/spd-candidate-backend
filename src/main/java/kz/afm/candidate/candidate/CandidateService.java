package kz.afm.candidate.candidate;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityService;
import kz.afm.candidate.candidate.dto.CandidateRequestDto;
import kz.afm.candidate.candidate.education.EducationService;
import kz.afm.candidate.candidate.language_knowledge.LanguageKnowledgeService;
import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import kz.afm.candidate.candidate.status.CandidateStatusService;
import kz.afm.candidate.candidate.experience.ExperienceService;
import kz.afm.candidate.user.UserEntity;
import kz.afm.candidate.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CandidateService {

    private final ExperienceService experienceService;
    private final UserService userService;
    private final CandidateStatusService candidateStatusService;
    private final AreaOfActivityService areaOfActivityService;
    private final EducationService educationService;
    private final CandidateEntityFactory candidateEntityFactory;
    private final LanguageKnowledgeService languageKnowledgeService;

    private final CandidateRepository candidateRepository;

    public long countAll(int statusId, int testingRegionId) {
        if (testingRegionId == -1) return this.candidateRepository.countByStatus_Id(statusId);
        return this.candidateRepository.countByStatus_IdAndTestingRegion_Id(statusId, testingRegionId);
    }

    public List<CandidateEntity> getAll(int statusId, int testingRegionId, int pageNumber, int pageSize) {
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("createDate").descending());
        if (testingRegionId == -1) return this.candidateRepository.findByStatus_Id(statusId, pageRequest);
        return candidateRepository.findByStatus_IdAndTestingRegion_Id(statusId, testingRegionId, pageRequest);
    }

    public CandidateEntity getById(String id) throws NoSuchElementException {
        return this.candidateRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Не найден кандидат с ID: " + id)
        );
    }

    public CandidateEntity getByUserId(long userId) throws NoSuchElementException {
        return this.candidateRepository.findByUser_Id(userId).getFirst();
    }

    public CandidateEntity getByUserOrNull(UserEntity user) {
        return this.candidateRepository.findByUser(user).orElse(null);
    }

    @Transactional
    public void create(CandidateRequestDto candidateDto) throws NoSuchElementException {
        final CandidateEntity candidate = this.candidateEntityFactory.createFrom(candidateDto);

        CandidateStatusEntity status = this.candidateStatusService.getNewCandidateStatus();
        candidate.setStatus(status);

        final CandidateEntity savedCandidate = this.candidateRepository.save(candidate);

        this.experienceService.createAll(savedCandidate, candidateDto.experiences);
        this.educationService.createAll(savedCandidate, candidateDto.education);
        this.languageKnowledgeService.createAll(savedCandidate, candidateDto.languageKnowledge);
    }

    @Transactional
    public void update(CandidateRequestDto candidateDto) throws NoSuchElementException {
        CandidateEntity updatedCandidate = this.updateUsing(candidateDto);
        this.candidateRepository.save(updatedCandidate);
    }

    @Transactional
    public void sendToSecurityCheck(CandidateRequestDto candidateDto) throws NoSuchElementException {
        CandidateEntity updatedCandidate = this.updateUsing(candidateDto);

        CandidateStatusEntity status = this.candidateStatusService.getOnSecurityCheckStatus();
        updatedCandidate.setStatus(status);

        this.candidateRepository.save(updatedCandidate);
    }

    private CandidateEntity updateUsing(CandidateRequestDto candidateDto) throws NoSuchElementException {
        CandidateEntity candidate = this.getById(candidateDto.identificationNumber);

        this.userService.updateUsername(candidateDto.username, candidate.getUser());
        this.experienceService.updateAll(candidate, candidateDto.experiences);
        this.educationService.updateAll(candidate, candidateDto.education);
        this.languageKnowledgeService.updateAll(candidate, candidateDto.languageKnowledge);

        return this.candidateEntityFactory.updateEntityUsingRequestDtoValues(candidate, candidateDto);
    }

    public void sendToApproval(CandidateRequestDto candidateDto) throws NoSuchElementException {
        final CandidateStatusEntity status = this.candidateStatusService.getOnApprovalStatus()  ;
        final CandidateEntity candidate = this.getById(candidateDto.identificationNumber);
        candidate.setSecurityCheckResult(candidateDto.securityCheckResult);
        candidate.setStatus(status);
        this.candidateRepository.save(candidate);
    }

    public void approve(String iin, String areaOfActivity) throws NoSuchElementException {
        final CandidateStatusEntity status = this.candidateStatusService.getApprovedStatus();
        final CandidateEntity candidate = this.getById(iin);
        candidate.setStatus(status);
        candidate.setAreaOfActivity(this.areaOfActivityService.getByName(areaOfActivity));
        this.candidateRepository.save(candidate);
    }

    public void reject(String iin) {
        final CandidateEntity candidate = this.getById(iin);
        final CandidateStatusEntity status = this.candidateStatusService.getRejectedStatus();
        candidate.setStatus(status);
        this.candidateRepository.save(candidate);
    }

    @Transactional
    public void delete(String iin) {
        this.experienceService.deleteByCandidateIdentificationNumber(iin);
        this.educationService.deleteByCandidateIdentificationNumber(iin);
        this.languageKnowledgeService.deleteByCandidateIdentificationNumber(iin);
        this.candidateRepository.deleteById(iin);
    }

}
