package kz.afm.candidate.reference;

import kz.afm.candidate.reference.dto.RegionResponse;
import kz.afm.candidate.reference.region.RegionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("reference")
@RestController
public class ReferenceController {

    private final ReferenceService referenceService;

    @GetMapping("region/all")
    public ResponseEntity<List<RegionResponse>> getAllRegions() {
        try {
            final List<RegionResponse> regions = this.referenceService.getAllRegions()
                    .stream()
                    .map(
                            (RegionEntity region) -> new RegionResponse(
                                    region.getId(),
                                    region.getNameRus(),
                                    region.getNameKaz()
                            )
                    )
                    .toList();
            return ResponseEntity.ok(regions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
