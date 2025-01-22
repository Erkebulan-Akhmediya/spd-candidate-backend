package kz.afm.candidate.candidate.area_of_activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AreaOfActivityService {

    private final AreaOfActivityRepository areaOfActivityRepository;

    public List<AreaOfActivityEntity> getAll() throws NoSuchElementException {
        List<AreaOfActivityEntity> areas = areaOfActivityRepository.findAll();
        if (areas.isEmpty()) throw new NoSuchElementException("Сферы деятельности не найдены");
        return areas;
    }

    public AreaOfActivityEntity getByName(String name) throws NoSuchElementException {
        return this.areaOfActivityRepository.findById(name).orElseThrow(
                () -> new NoSuchElementException("Сфера деятельности '" + name + "' не найдена")
        );
    }

}
