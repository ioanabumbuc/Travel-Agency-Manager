package sd.tourismagency.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Cruise;
import sd.tourismagency.repository.CruiseRepo;

import java.util.List;

@Service
@Transactional
public class CruiseService {

    @Autowired
    private final CruiseRepo cruiseRepo;

    public CruiseService(CruiseRepo cruiseRepo) {
        this.cruiseRepo = cruiseRepo;
    }


    public List<Cruise> getAllCruises() {
        return cruiseRepo.findAll();
    }

    public Cruise findById(Long id) {
        return cruiseRepo.findById(id).orElse(null);
    }

    public Cruise addCruise(Cruise cruise) {
        return cruiseRepo.save(cruise);
    }

    public Cruise updateCruise(Cruise cruise) {
        if (!cruiseRepo.existsById(cruise.getId())) {
            return null;
        }
        return cruiseRepo.save(cruise);
    }
}
