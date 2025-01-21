package kz.afm.candidate.test;

import jakarta.transaction.Transactional;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.variant.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TestService {

    private final VariantService variantService;
    private final TestRepository testRepository;

    @Transactional
    public void create(CreateTestRequest dto) throws NoSuchElementException {
        final TestEntity test = this.testRepository.save(
                new TestEntity(
                        dto.getNameRus(),
                        dto.getNameKaz(),
                        Boolean.parseBoolean(dto.getIsLimitless()),
                        dto.getDuration()
                )
        );
        this.variantService.create(test, dto.getVariants());
    }

}
