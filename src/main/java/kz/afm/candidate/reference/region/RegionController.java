package kz.afm.candidate.reference.region;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.reference.region.dto.RegionResponseDto;
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
    public ResponseEntity<ResponseBodyWrapper<List<RegionResponseDto>>> getAll() {
        try {
            final List<RegionEntity> regions = this.regionService.getAll();
            final List<RegionResponseDto> regionDtoList = regions.stream().map(RegionResponseDto::new).toList();
            return ResponseEntity.ok(ResponseBodyWrapper.success(regionDtoList));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
