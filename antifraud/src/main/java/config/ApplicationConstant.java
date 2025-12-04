package config;

public class ApplicationConstant {
    private ApplicationConstant() {}

    public static final String TYPE_CARD = "card";
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_ACCOUNT = "account";

    public static final String ERR_NOT_FOUND_TEMPLATE = "%s не найден с ID: %s";
    public static final String ERR_CARD_NOT_FOUND = "Карта";
    public static final String ERR_PHONE_NOT_FOUND = "Телефон";
    public static final String ERR_ACCOUNT_NOT_FOUND = "Аккаунт";
    public static final String ERR_INVALID_TYPE = "Недопустимый тип транзакции: ";

    public static final String MSG_CREATED = "СОЗДАНА подозрительная транзакция: Тип={}, Метод={}, Данные={}";
    public static final String MSG_UPDATED = "ОБНОВЛЕНА подозрительная транзакция: Тип={}, ID={}, Данные={}";
    public static final String MSG_DELETED = "УДАЛЕНИЕ подозрительной транзакции: Тип={}, ID={}";
    public static final String MSG_RECEIVED = "Получена подозрительная транзакция: Тип={}, ID={}";
    public static final String MSG_ERR_SERVICE = "ОШИБКА в сервисе подозрительных транзакций: Метод={}, Ошибка={}";

    public static final String MSG_BLOCKED = "Заблокирована=";
    public static final String MSG_SUSPICIOUS = "Подозрительна=";
    public static final String MSG_BLOCKED_REASON = "Причина=";
    public static final String MSG_NOT_DATE = "Нет данных";

    public static final String METHOD_CREATE = "create";
    public static final String METHOD_UPDATE = "update";
    public static final String METHOD_GET = "get";
    public static final String SUFFIX_BY_ID = "ById";

    public static final String REFLECT_GET_ID = "getId";
    public static final String REFLECT_IS_BLOCKED = "isBlocked";
    public static final String REFLECT_IS_SUSPICIOUS = "isSuspicious";
    public static final String REFLECT_GET_BLOCKED_REASON = "getBlockedReason";
    public static final String REFLECT_GET_SUSPICIOUS_REASON = "getSuspiciousReason";

    public static final String POINTCUT_SERVICE = "execution(* service.SuspiciousTransferServiceImpl.*(..))";
    public static final String POINTCUT_CREATE = "execution(* service.SuspiciousTransferServiceImpl.create*(..))";
    public static final String POINTCUT_UPDATE = "execution(* service.SuspiciousTransferServiceImpl.update*(..))";
    public static final String POINTCUT_DELETE = "execution(* service.SuspiciousTransferServiceImpl.deleteSuspiciousTransfer(..))";
    public static final String POINTCUT_GET_BY_ID = "execution(* service.SuspiciousTransferServiceImpl.get*ById(..))";

    public static final String OPERATION_UPDATE = "UPDATE";
    public static final String OPERATION_CREATE = "CREATE";
    public static final String OPERATION_DELETE = "DELETE";

    public static final String ENTITY_TYPE_CARD = "SUSPICIOUS_CARD_TRANSFER";
    public static final String ENTITY_TYPE_PHONE = "SUSPICIOUS_PHONE_TRANSFER";
    public static final String ENTITY_TYPE_ACCOUNT = "SUSPICIOUS_ACCOUNT_TRANSFER";
    public static final String ENTITY_TYPE_UNKNOWN = "UNKNOWN";

    public static final String MSG_ERR_ASPECT_CREATE = "Ошибка в аспекте при логировании создания: {}";
    public static final String MSG_ERR_ASPECT_UPDATE = "Ошибка в аспекте при логировании обновления: {}";
    public static final String MSG_ERR_ASPECT_DELETE = "Ошибка в аспекте при логировании удаления: {}";
    public static final String MSG_ERR_ASPECT_GET = "Ошибка в аспекте при логировании получения по ID: {}";
    public static final String ERR_MSG_INP_DETAIL = "Ошибка извлечения деталей: ";

    public static final String AUDIT_LOG_CREATE = "Аудит: Создана {} пользователем {}";
    public static final String AUDIT_LOG_UPDATE = "Аудит: Обновлена {} ID {} пользователем {}";
    public static final String AUDIT_LOG_ERROR_CREATE = "Ошибка при логировании создания {}: {}";
    public static final String AUDIT_LOG_ERROR_UPDATE = "Ошибка при логировании обновления {} ID {}: {}";

    public static final String AUDIT_SERIALIZATION_ERROR = "{\"error\": \"serialization_failed\"}";
    public static final String ERR_CRITICAL_MSG = "Критическая ошибка в аспекте логирования исключений";
    public static final String NAME_METHOD_IS_BLOCKED = "isBlocked";
    public static final String NAME_METHOD_IS_SUSPICIOUS = "isSuspicious";
    public static final String NAME_METHOD_GET_BLOCKED_REASON = "getBlockedReason";
    public static final String NAME_METHOD_GET_SUSPICIOUS_REASON = "getSuspiciousReason";
    public static final String NAME_RETURNING = "result";
    public static final String BY_ID_TARGET = "ById";
    public static final String GET_NAME_TARGET = "get";
    public static final String ENTITY_NAME_GET_ID = "getId";
    public static final String EXCEPTION_NAME = "ex";
    public static final String UNKNOWN_NUM= "Unknown";

    public static final String RETURNING_RES = "result";
    public static final String METHOD_TARGET_CREATE = "create";
    public static final String METHOD_TARGET_UPDATE = "update";

    public static final String CASE_CARD = "card";
    public static final String CASE_PHONE = "phone";
    public static final String CASE_ACCOUNT = "account";

    public static final String MSG_INVALID_TYPE = "Invalid type";

    public static final String MSG_ERROR_NOT_FOUND = "NOT_FOUND";
    public static final String MSG_ERROR_VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String MSG_ERROR_INTERNAL_ERROR = "INTERNAL_ERROR";

    public static final String MSG_ERROR_UNEXPECTED_ERROR = "Unexpected error";

    public static final Long TEST_ID = 1L;
    public static final boolean TEST_BLOCKED = true;
    public static final boolean TEST_SUSPICIOUS = true;
    public static final String TEST_SUSPICIOUS_REASON = "Подозрительные действия";
    public static final String TEST_BLOCKED_REASON = "Причина блокировки";
    public static final String TEST_USERNAME = "testuser";
    public static final Object TEST_ENTITY = "Test Entity";
    public static final Object TEST_NEW_ENTITY = "New Entity";
    public static final Object TEST_OLD_ENTITY = "Old Entity";

    public static final String ERR_TEST_RUNTIME_EXEPTION_MSG = "Database error";
    public static final String ERR_TEST_RUNTIME_EXEPTION_MS_SER = "Serialization error";
    public static final String LOG_AUDIT_FAILED = "Audit logging failed";

}
