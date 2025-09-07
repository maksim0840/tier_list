package core.services;

import core.repositories.TierRepository;
import org.springframework.stereotype.Service;

@Service
public class TierService {

    private final TierRepository tierRepository;

    public TierService(TierRepository tierRepository) {
        this.tierRepository = tierRepository;
    }
}
