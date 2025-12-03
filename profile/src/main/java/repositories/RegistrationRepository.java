package repositories;

import dto.RegistrationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationDto, Long> {
    Optional<RegistrationDto> findByIndex(int index);
}
