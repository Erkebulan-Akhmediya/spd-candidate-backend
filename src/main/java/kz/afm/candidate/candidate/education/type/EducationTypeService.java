package kz.afm.candidate.candidate.education.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class EducationTypeService {

    private final EducationTypeRepository educationTypeRepository;

    public List<EducationTypeEntity> getAll() throws NoSuchElementException {
        List<EducationTypeEntity> types = this.educationTypeRepository.findAll();
        if (types.isEmpty()) throw new NoSuchElementException("Типы образований не найдены");
        return types;
    }

    public EducationTypeEntity getById(int id) throws NoSuchElementException {
        return this.educationTypeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Тип образования с ID: " + id + " не найден")
        );
    }

    public Map<Integer, EducationTypeEntity> getAllMap() {
        final List<EducationTypeEntity> types = this.educationTypeRepository.findAll();
        final Map<Integer, EducationTypeEntity> map = new HashMap<>();
        for (EducationTypeEntity type : types) {
            map.putIfAbsent(type.id, type);
        }
        return map;
    }

}
