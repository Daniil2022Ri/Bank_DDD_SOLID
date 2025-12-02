package service;

import dto.SuspiciousAccountTransferDto;
import dto.SuspiciousCardTransferDto;
import dto.SuspiciousPhoneTransferDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapers.SuspiciousTransferMapper;
import model.SuspiciousAccountTransfer;
import model.SuspiciousCardTransfer;
import model.SuspiciousPhoneTransfer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SuspiciousAccountTransferRepository;
import repository.SuspiciousCardTransferRepository;
import repository.SuspiciousPhoneTransferRepository;



import java.util.List;


@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SuspiciousTransferServiceImpl implements SuspiciousTransferService{

    private final SuspiciousCardTransferRepository cardRepo;
    private final SuspiciousPhoneTransferRepository phoneRepo;
    private final SuspiciousAccountTransferRepository accountRepo;
    private final SuspiciousTransferMapper mapper;

    @Override
    public SuspiciousCardTransferDto createCard(SuspiciousCardTransferDto CardDto) {
        log.info("Создание Подозрительной транзакции Карты: {}" , CardDto.getId());
        try{
            SuspiciousCardTransfer entity = mapper.toCardEntity(CardDto);
            log.info("Детали создания подозрительной транзакции Карты: id={} , blockedReason={} ", CardDto.getId() ,CardDto.getBlockedReason());
            return mapper.toCardDto(cardRepo.save(entity));
        }catch(Exception errorMassage){
            log.error("Ошибка при создании подозрительно транзакции карты: {}" ,
                    CardDto.getId() , errorMassage);
            throw errorMassage;
        }
    }

    @Override
    public SuspiciousPhoneTransferDto createPhone(SuspiciousPhoneTransferDto PhoneDto) {
        log.info("Создание подозрительной транзакции Телефона: {}" ,PhoneDto.getId() );
        try{
            SuspiciousPhoneTransfer entity = mapper.toPhoneEntity(PhoneDto);
            log.info("Детали создания подозрительной транзакции Телефона: ip={}" ,PhoneDto.getId());
            return mapper.toPhoneDto(phoneRepo.save(entity));
        } catch (Exception errorMassage) {
            log.error("Ошибка при создании подозрительной транзакции Телефона: ip={}" , PhoneDto.getId(), errorMassage);
            throw errorMassage;
        }

    }

    @Override
    public SuspiciousAccountTransferDto createAccount(SuspiciousAccountTransferDto AccountDto) {
        log.info("Создание подозрительной транзакции Аккаунта: id={}" , AccountDto.getId());
        try{
            SuspiciousAccountTransfer entity = mapper.toAccountEntity(AccountDto);
            log.info("Детали создания подозрительной транзакции Аккаунта: id={}" , AccountDto.getId());
            return mapper.toAccountDto(accountRepo.save(entity));
        } catch (Exception errorMassage) {
            log.error("Ошбика при создании подозрительной транзакции Аккаунта: ip={}" , AccountDto.getId(),errorMassage);
            throw errorMassage;
        }

    }

    @Override
    public SuspiciousCardTransferDto updateCard(Long id, SuspiciousCardTransferDto CardDto) {
        log.info("Обновление данных подозрительной транзакции Карты: id={}" , CardDto.getId());
        SuspiciousCardTransfer entity = cardRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Подозрительный перевод на карту не найден: " + id));
        try{
            entity.setBlocked(CardDto.isBlocked());
            entity.setSuspicious(CardDto.isSuspicious());
            entity.setBlockedReason(CardDto.getBlockedReason());
            entity.setSuspiciousReason(CardDto.getSuspiciousReason());
            log.info("Обновление Данных Карты: id={} , блокировка={}, Причина Блокировки={} , Причина Подозрительности={}",
                    CardDto.getId(), CardDto.isBlocked(), CardDto.isSuspicious() , CardDto.getBlockedReason() , CardDto.getSuspiciousReason());
            return mapper.toCardDto(cardRepo.save(entity));
        }catch (Exception errorMassage){
            log.error("Ошибка Обновления данных Карты: id={}", CardDto.getId() , errorMassage);
            throw errorMassage;
        }

    }

    @Override
    public SuspiciousPhoneTransferDto updatePhone(Long id, SuspiciousPhoneTransferDto PhoneDto) {
        log.info("Обновление данных подозрительных транзакций Телефона: id={}", PhoneDto.getId());
        SuspiciousPhoneTransfer entity = phoneRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Подозрительный перевод на телефон не найден: " + id));
        try{
            log.info("Обновление Данных Телефона: id={} , Блокировка={} , Причина Блокировки={} , Причина Подозрительности={}",
                    PhoneDto.getId() , PhoneDto.isBlocked() , PhoneDto.isSuspicious() , PhoneDto.getBlockedReason() , PhoneDto.getSuspiciousReason());
            entity.setBlocked(PhoneDto.isBlocked());
            entity.setSuspicious(PhoneDto.isSuspicious());
            entity.setBlockedReason(PhoneDto.getBlockedReason());
            entity.setSuspiciousReason(PhoneDto.getSuspiciousReason());
            return mapper.toPhoneDto(phoneRepo.save(entity));
        }catch (Exception errorMassage){
            log.error("Ошибка Обновления Данных Телефона: id={}" , PhoneDto.getId() , errorMassage);
            throw errorMassage;
        }

    }

    @Override
    public SuspiciousAccountTransferDto updateAccount(Long id, SuspiciousAccountTransferDto AccountDto) {
        SuspiciousAccountTransfer entity = accountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Подозрительный перевод на аккаунт не найден: " + id));
        try{
            log.info("Обновление Данных Аккаунта: id={} , Блокировка={} , Причина Блокировки={} , Причина Подозрительности={}",
                    AccountDto.getId() , AccountDto.isBlocked() , AccountDto.isSuspicious() , AccountDto.getBlockedReason() , AccountDto.getSuspiciousReason());
            entity.setBlocked(AccountDto.isBlocked());
            entity.setSuspicious(AccountDto.isSuspicious());
            entity.setBlockedReason(AccountDto.getBlockedReason());
            entity.setSuspiciousReason(AccountDto.getSuspiciousReason());
            return mapper.toAccountDto(accountRepo.save(entity));
        }catch(Exception errorMassage){
            log.error("Ошибка Обновление Данных Аккаунта: id={}",AccountDto.getId() , errorMassage);
            throw errorMassage;
        }

    }



    @Override
    public void deleteSuspiciousTransfer(Long id, String type) {
        log.info("Удаление Подозрительных транзакций");
        switch (type.toLowerCase()) {
            case "card" -> cardRepo.deleteById(id);
            case "phone" -> phoneRepo.deleteById(id);
            case "account" -> accountRepo.deleteById(id);
            default -> throw new IllegalArgumentException("Ошибка запроса удаления: " + type);
        }
    }

    @Override
    public List<?> getAll(String type) {
        log.info("Получение всех подозрительных транзакций");
        return switch (type.toLowerCase()) {
            case "card" -> cardRepo.findAll().stream().map(mapper::toCardDto).toList();
            case "phone" -> phoneRepo.findAll().stream().map(mapper::toPhoneDto).toList();
            case "account" -> accountRepo.findAll().stream().map(mapper::toAccountDto).toList();
            default -> throw new IllegalArgumentException("Ошибка запроса на получение:" + type);
        };
    }

    @Override
    public Object getById(Long id, String type) {
        log.info("Получение по id:");
        return switch (type.toLowerCase()) {
            case "card" -> mapper.toCardDto(cardRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Карта не найдена")));
            case "phone" -> mapper.toPhoneDto(phoneRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Телефон не найден")));
            case "account" -> mapper.toAccountDto(accountRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден")));
            default -> throw new IllegalArgumentException("Ошибка запроса на получение по ID:" + type);
        };
    }



    @Override
    public List<SuspiciousCardTransferDto> getAllCards() {
        log.info("Получение всех подозрительных карточных транзакций");
        return cardRepo.findAll().stream()
                .map(mapper::toCardDto)
                .toList();
    }

    @Override
    public List<SuspiciousPhoneTransferDto> getAllPhones() {
        log.info("Получение всех подозрительных телефонных транзакций");
        return phoneRepo.findAll().stream()
                .map(mapper::toPhoneDto)
                .toList();
    }

    @Override
    public List<SuspiciousAccountTransferDto> getAllAccounts() {
        log.info("Получение всех подозрительных аккаунтных транзакций");
        return accountRepo.findAll().stream()
                .map(mapper::toAccountDto)
                .toList();
    }

    @Override
    public SuspiciousCardTransferDto getCardById(Long id) {
        log.info("Получение карточной транзакции по ID: {}", id);
        return mapper.toCardDto(cardRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Карта не найдена")));
    }

    @Override
    public SuspiciousPhoneTransferDto getPhoneById(Long id) {
        log.info("Получение телефонной транзакции по ID: {}", id);
        return mapper.toPhoneDto(phoneRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Телефон не найден")));
    }

    @Override
    public SuspiciousAccountTransferDto getAccountById(Long id) {
        log.info("Получение аккаунтной транзакции по ID: {}", id);
        return mapper.toAccountDto(accountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден")));
    }


}
