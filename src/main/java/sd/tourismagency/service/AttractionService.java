package sd.tourismagency.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Attraction;
import sd.tourismagency.repository.AttractionRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttractionService {
    @Autowired
    private final AttractionRepo attractionRepo;
    @Autowired
    private final StayService stayService;

    public AttractionService(AttractionRepo attractionRepo, StayService stayService) {
        this.attractionRepo = attractionRepo;
        this.stayService = stayService;
    }

    public List<Attraction> getAllAttractions() {
        return attractionRepo.findAll();
    }

    public List<Attraction> getAllByIds(List<Long> ids) {
        return ids.stream().map(id -> attractionRepo.findById(id).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Attraction addAttraction(Attraction attraction) {
        return attractionRepo.save(attraction);
    }

    public Attraction updateAttraction(Attraction attraction) {
        if (attractionRepo.findById(attraction.getId()).isEmpty()) {
            return null;
        }
        return attractionRepo.save(attraction);
    }

    public void deleteAttraction(Long id) {
        attractionRepo.deleteById(id);
    }

    public boolean existsById(Long id) {
        return attractionRepo.existsById(id);
    }
}
