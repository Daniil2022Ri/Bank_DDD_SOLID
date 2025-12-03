package repositories;

import dto.ProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileDto, Long> {
    Optional<ProfileDto> findByPhoneNumber(int phoneNumber);
    Optional<ProfileDto> findByEmail(String email);
    Optional<ProfileDto> findByNameOnCard(String nameCard);
    Optional<ProfileDto> findByInn(int inn);
    Optional<ProfileDto> findBySnils(int snils);

}
