package kz.afm.candidate.reference.driver_license.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllDriverLicenseResponse {
    private String error;
    List<String> driverLicenses;
}
