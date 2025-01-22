package kz.afm.candidate.candidate.area_of_activity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("area_of_activity")
@RestController
public class AreaOfActivityController {

    private final AreaOfActivityService areaOfActivityService;

    @GetMapping("all")
    public ResponseEntity<List<String>> getAll() {
        try {
            final List<String> areas = this.areaOfActivityService.getAll()
                    .stream()
                    .map(AreaOfActivityEntity::getName)
                    .toList();
            return ResponseEntity.ok(areas);
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(new LinkedList<>(){{ add(e.getMessage()); }});
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(new LinkedList<>(){{ add("Ошибка сервера"); }});
        }
    }

}
