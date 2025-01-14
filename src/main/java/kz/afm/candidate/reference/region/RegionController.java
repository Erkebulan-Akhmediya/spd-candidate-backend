package kz.afm.candidate.reference.region;

import kz.afm.candidate.reference.dto.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("region")
@RestController
public class RegionController {

    private final RegionService regionService;

    @GetMapping("all")
    public ResponseEntity<List<RegionResponse>> getAll() {
        try {
            final List<RegionResponse> regions = this.regionService.getAll()
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
