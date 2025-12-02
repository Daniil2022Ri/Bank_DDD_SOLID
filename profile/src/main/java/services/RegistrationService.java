package services;

import dto.RegistrationDto;

import java.util.List;

public interface RegistrationService {
    List<RegistrationDto> findAll();
    RegistrationDto update(Long id, RegistrationDto registrationDto);
}
