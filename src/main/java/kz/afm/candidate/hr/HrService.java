package kz.afm.candidate.hr;

import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HrService {

    private final HrRepository hrRepository;

    public int getRegionByUser(UserEntity user) {
        HrEntity hr = this.hrRepository.findByUser(user).orElseThrow();
        return hr.getRegion().getId();
    }

}
