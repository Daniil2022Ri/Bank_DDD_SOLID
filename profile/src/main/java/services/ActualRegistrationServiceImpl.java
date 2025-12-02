package services;

import entity.ActualRegistration;
import io.fabric8.kubernetes.client.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repositories.ActualRegistrationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActualRegistrationServiceImpl implements ActualRegistrationService{

    private final ActualRegistrationRepository actualRegistrationRepository;

    @Override
    public ActualRegistration create(ActualRegistration registration) {


        if(registration == null){
            throw new IllegalArgumentException("Registration cannot be null");
        }
        return actualRegistrationRepository.save(registration);
    }

    @Override
    public ActualRegistration getById(Long id) {
        log.info("Get ID");
        return actualRegistrationRepository.getById(id);

    }

    @Override
    public List<ActualRegistration> getAll() {
        return actualRegistrationRepository.findAll();
    }

    @Override
    public ActualRegistration update(Long id, ActualRegistration registration) {
        log.info("Update Actual Registration with ID: {}", id);
        return null;
    }

    @Override
    public void delete(Long id) {
        if (!actualRegistrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registration not found with id: " + id);
        }
        actualRegistrationRepository.deleteById(id);
    }
}
