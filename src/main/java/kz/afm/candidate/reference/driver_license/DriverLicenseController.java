package kz.afm.candidate.reference.driver_license;

import kz.afm.candidate.reference.driver_license.dto.GetAllDriverLicenseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("driver_license")
@RestController
public class DriverLicenseController {

    private final DriverLicenseService driverLicenseService;

    @GetMapping("all")
    public ResponseEntity<GetAllDriverLicenseResponse> getAll() {
        try {
            final List<String> driverLicenses = this.driverLicenseService.getAll(true)
                    .stream()
                    .map(DriverLicenseEntity::getCode)
                    .toList();
            return ResponseEntity.ok(new GetAllDriverLicenseResponse(null, driverLicenses));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllDriverLicenseResponse(e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllDriverLicenseResponse("Ошибка сервера", null)
            );
        }
    }

}
