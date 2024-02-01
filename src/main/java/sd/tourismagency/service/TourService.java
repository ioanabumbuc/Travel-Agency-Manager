package sd.tourismagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Tour;
import sd.tourismagency.repository.TourRepo;

import java.util.List;

@Service
public class TourService {
    @Autowired
    private final TourRepo tourRepo;

    public TourService(TourRepo tourRepo) {
        this.tourRepo = tourRepo;
    }

    public List<Tour> getAllTours(){
        return tourRepo.findAll();
    }

    public Tour findById(Long id){
        return tourRepo.findById(id).orElse(null);
    }

    public Tour addTour(Tour tour){
        return tourRepo.save(tour);
    }

    public Tour updateTour(Tour tour){
        if(!tourRepo.existsById(tour.getId())){
            return null;
        }
        return tourRepo.save(tour);
    }
}
