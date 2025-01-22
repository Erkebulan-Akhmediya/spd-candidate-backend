package kz.afm.candidate.candidate.area_of_activity;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AreaOfActivityInitializer implements CommandLineRunner {

    private final AreaOfActivityRepository areaOfActivityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<AreaOfActivityEntity> areas = new LinkedList<>(){{
            add(new AreaOfActivityEntity("01"));
            add(new AreaOfActivityEntity("02"));
            add(new AreaOfActivityEntity("03"));
        }};
        this.areaOfActivityRepository.saveAll(areas);
    }

}
