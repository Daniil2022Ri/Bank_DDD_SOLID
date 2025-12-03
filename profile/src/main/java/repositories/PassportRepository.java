package repositories;

import dto.PassportDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<PassportDto, Long> {
    Optional<PassportDto> findBySeriesAndNumber(String passportSeries , String passportNumber);
    Optional<PassportDto> findByNamesDetails(String lastName , String firstName , String middleName, String gender);

}
