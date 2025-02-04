package kz.afm.candidate.candidate.education.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
