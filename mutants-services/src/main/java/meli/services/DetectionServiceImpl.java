package meli.services;

import meli.interfaces.DetectionService;
import org.springframework.stereotype.Service;

/**
 * A sample service
 */
@Service
public class DetectionServiceImpl implements DetectionService {

    @Override
    public boolean isMutant(String[] dna) {
        return false;
    }

}