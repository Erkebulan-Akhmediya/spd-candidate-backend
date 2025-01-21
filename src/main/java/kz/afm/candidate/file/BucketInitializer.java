package kz.afm.candidate.file;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Order(5)
@Component
public class BucketInitializer implements CommandLineRunner {

    private final FileService fileService;

    @Override
    public void run(String... args) throws Exception {
        this.fileService.createBucket("files");
    }

}
