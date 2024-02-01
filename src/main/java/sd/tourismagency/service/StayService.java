package sd.tourismagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Stay;
import sd.tourismagency.repository.StayRepo;

import java.util.List;
import java.util.Optional;

@Service
public class StayService {

    @Autowired
    private final StayRepo stayRepo;

    public StayService(StayRepo stayRepo) {
        this.stayRepo = stayRepo;
    }

    public Optional<Stay> findStayById(Long id) {
        return stayRepo.findById(id);
    }

    public List<Stay> getAllStays() {
        return stayRepo.findAll();
    }

    public Stay findById(Long id){
        return stayRepo.findById(id).orElse(null);
    }

    public Stay addStay(Stay stay) {
        return stayRepo.save(stay);
    }

    public Stay updateStay(Stay stay) {
        if (!stayRepo.existsById(stay.getId())) {
            return null;
        }
        return stayRepo.save(stay);
    }

    public void deleteStay(Long id){
        stayRepo.deleteById(id);
    }
}
