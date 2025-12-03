package config;

public class ApplicationConstants {

    // API Tags
    public static final String API_TAG_HISTORY = "History Management";
    public static final String API_TAG_AUDIT = "Audit Management";

    // API Descriptions
    public static final String API_DESCRIPTION_HISTORY = "API для агрегации и управления историей изменений из всех микросервисов банка";
    public static final String API_DESCRIPTION_AUDIT = "API для просмотра аудиторских записей";

    // Common Messages
    public static final String HISTORY_NOT_FOUND = "Запись истории не найдена";
    public static final String INVALID_INPUT_DATA = "Неверные входные данные";
    public static final String OPERATION_SUCCESS = "Операция выполнена успешно";

    // Microservice Names
    public static final String MICROSERVICE_ACCOUNT = "account";
    public static final String MICROSERVICE_AUTHORIZATION = "authorization";
    public static final String MICROSERVICE_ANTI_FRAUD = "anti_fraud";
    public static final String MICROSERVICE_PROFILE = "profile";
    public static final String MICROSERVICE_TRANSFER = "transfer";
    public static final String MICROSERVICE_PUBLIC_BANK_INFO = "public_bank_info";

    private ApplicationConstants() {
        throw new UnsupportedOperationException("Это класс утилита и не может быть инстанциирован");
    }
}
