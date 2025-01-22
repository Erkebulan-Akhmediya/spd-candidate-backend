package kz.afm.candidate.candidate.area_of_activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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

    public Set<AreaOfActivityEntity> getAllSetByNameList(List<String> nameList) throws RuntimeException {
        Set<AreaOfActivityEntity> areas = new LinkedHashSet<>(this.areaOfActivityRepository.findAllByNameIn(nameList));
        if (areas.isEmpty()) throw new RuntimeException("Направления деятельности теста не могут быть пустыми");
        return areas;
    }

}
