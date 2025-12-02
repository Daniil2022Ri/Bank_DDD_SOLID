package services;

import entity.ActualRegistration;

import java.util.List;

public interface ActualRegistrationService {

    ActualRegistration create(ActualRegistration registration);
    ActualRegistration getById(Long id);
    List<ActualRegistration> getAll();
    ActualRegistration update(Long id, ActualRegistration registration);
    void delete(Long id);


}
