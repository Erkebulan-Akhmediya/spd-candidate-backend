package kz.afm.candidate.test.test_type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TestTypeService {

    private final TestTypeRepository testTypeRepository;

    public List<TestTypeEntity> getAll() {
        return this.testTypeRepository.findAll();
    }

    public TestTypeEntity getById(int id) throws NoSuchElementException {
        return this.testTypeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Тип теста с ID: " + id + " не найден")
        );
    }

}
