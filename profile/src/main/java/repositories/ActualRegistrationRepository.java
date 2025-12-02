package repositories;

import entity.ActualRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActualRegistrationRepository extends JpaRepository<ActualRegistration, Long> {
    List<ActualRegistration> findByIndex(int index);
    List<ActualRegistration> findByCountryAndRegion(String county , String region);
    List<ActualRegistration> findByCityAndDistrict(String city, String district);
    List<ActualRegistration> findByLocalityAndStreet(String locality , String street);
    List<ActualRegistration> findByHouseNumberAndHouseBlockAndFlatNumber(String HouseNumber, String HouseBlock);
}
