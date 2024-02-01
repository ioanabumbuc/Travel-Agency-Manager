package sd.tourismagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.tourismagency.dto.VacationDto;
import sd.tourismagency.model.Vacation;
import sd.tourismagency.repository.VacationRepo;

import java.util.List;

@Service
public class VacationService {
    @Autowired
    private final VacationRepo vacationRepo;

    public VacationService(VacationRepo vacationRepo) {
        this.vacationRepo = vacationRepo;
    }

    public List<Vacation> getAllVacations(){
        return this.vacationRepo.findAll();
    }

    public Vacation getById(Long id){
        return this.vacationRepo.findById(id).orElse(null);
    }

    public Vacation updateVacation(Vacation vacation){
        return this.vacationRepo.save(vacation);
    }

    public void deleteVacation(Long id){
        this.vacationRepo.deleteById(id);
    }

}
