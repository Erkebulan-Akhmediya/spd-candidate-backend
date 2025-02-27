package kz.afm.candidate.reference.driver_license;

import kz.afm.candidate.candidate.dto.CandidateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class DriverLicenseService {

    private final DriverLicenseRepository driverLicenseRepository;

    public Set<DriverLicenseEntity> getSetOfAllUsing(CandidateRequestDto candidateDto) {
        return this.getSetOfAllByCodes(candidateDto.driverLicenseCodes, true);
    }

    public Set<DriverLicenseEntity> getSetOfAllByCodes(Set<String> codes, boolean notEmpty) throws NoSuchElementException {
        if (codes.isEmpty()) return new HashSet<>();
        final Set<DriverLicenseEntity> driverLicenses = new HashSet<>(
                this.driverLicenseRepository.findAllById(codes)
        );
        if (!notEmpty) return driverLicenses;

        if (driverLicenses.isEmpty()) throw new NoSuchElementException("Водительские права не найдены");
        return driverLicenses;
    }

    public List<DriverLicenseEntity> getAll(boolean notEmpty) throws NoSuchElementException {
        final List<DriverLicenseEntity> driverLicenses = this.driverLicenseRepository.findAll();
        if (!notEmpty) return driverLicenses;

        if (driverLicenses.isEmpty()) throw new NoSuchElementException("Водительские права не найдены");
        return driverLicenses;
    }

}
