package kz.afm.candidate.test.test_type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestTypeService {

    private final TestTypeRepository testTypeRepository;

    public List<TestTypeEntity> getAll() {
        return this.testTypeRepository.findAll();
    }

}
