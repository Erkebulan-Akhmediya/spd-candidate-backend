package kz.afm.candidate.reference.driver_license;

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

    public Set<DriverLicenseEntity> getAllSetByCodes(Set<String> codes) {
        return new HashSet<>(this.driverLicenseRepository.findAllById(codes));
    }

    public Set<DriverLicenseEntity> getAllSetByCodes(Set<String> codes, boolean notEmpty) throws NoSuchElementException {
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
