package config;

/**
 * Класс для хранения констант приложения
 * Используется для Swagger документации, сообщений об ошибках и других констант
 */
public class ApplicationConstants {

    // ========== API TAGS ==========
    public static final String API_TAG_ACCOUNT = "Account Management";
    public static final String API_TAG_AUDIT = "Audit Management";

    // ========== API DESCRIPTIONS ==========
    public static final String API_DESCRIPTION_ACCOUNT = "API для управления банковскими учетными записями и аудитом операций";
    public static final String API_DESCRIPTION_AUDIT = "API для просмотра аудиторских записей и отслеживания изменений";

    // ========== COMMON MESSAGES ==========
    public static final String ACCOUNT_NOT_FOUND = "Учетная запись не найдена";
    public static final String ACCOUNT_ALREADY_EXISTS = "Учетная запись с таким номером уже существует";
    public static final String INVALID_INPUT_DATA = "Неверные входные данные";
    public static final String OPERATION_SUCCESS = "Операция выполнена успешно";
    public static final String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";

    // ========== VALIDATION MESSAGES ==========
    public static final String ENTITY_TYPE_REQUIRED = "Тип сущности обязателен для заполнения";
    public static final String OPERATION_TYPE_REQUIRED = "Тип операции обязателен для заполнения";
    public static final String CREATED_BY_REQUIRED = "Поле 'создано' обязательно для заполнения";

    // ========== OPERATION TYPES ==========
    public static final String OPERATION_CREATE = "CREATE";
    public static final String OPERATION_UPDATE = "UPDATE";
    public static final String OPERATION_DELETE = "DELETE";
    public static final String OPERATION_READ = "READ";

    // ========== ENTITY TYPES ==========
    public static final String ENTITY_TYPE_ACCOUNT = "ACCOUNT";
    public static final String ENTITY_TYPE_USER = "USER";
    public static final String ENTITY_TYPE_TRANSACTION = "TRANSACTION";

    // ========== DATE FORMATS ==========
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // ========== HEADER NAMES ==========
    public static final String HEADER_USER_ID = "user_id";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    // ========== SWAGGER DESCRIPTIONS ==========
    public static final String SWAGGER_DESC_CREATE_ACCOUNT = "Создает новую учетную запись с указанными данными";
    public static final String SWAGGER_DESC_GET_ACCOUNT = "Возвращает учетную запись по указанному идентификатору";
    public static final String SWAGGER_DESC_GET_ALL_ACCOUNTS = "Возвращает список всех учетных записей";
    public static final String SWAGGER_DESC_UPDATE_ACCOUNT = "Обновляет данные существующей учетной записи";
    public static final String SWAGGER_DESC_DELETE_ACCOUNT = "Удаляет учетную запись по указанному идентификатору";

    // ========== LOG MESSAGES ==========
    public static final String LOG_CREATE_ACCOUNT = "Создание учетной записи типа: {}";
    public static final String LOG_UPDATE_ACCOUNT = "Обновление учетной записи ID: {}";
    public static final String LOG_DELETE_ACCOUNT = "Удаление учетной записи ID: {}";
    public static final String LOG_GET_ACCOUNT = "Получение учетной записи ID: {}";

    // ========== ERROR MESSAGES ==========
    public static final String ERROR_ACCOUNT_NOT_FOUND = "Учетная запись с ID {} не найдена";
    public static final String ERROR_DUPLICATE_ACCOUNT = "Учетная запись с такими параметрами уже существует";
    public static final String ERROR_VALIDATION_FAILED = "Ошибка валидации данных: {}";

    // ========== SUCCESS MESSAGES ==========
    public static final String SUCCESS_ACCOUNT_CREATED = "Учетная запись успешно создана с ID: {}";
    public static final String SUCCESS_ACCOUNT_UPDATED = "Учетная запись с ID: {} успешно обновлена";
    public static final String SUCCESS_ACCOUNT_DELETED = "Учетная запись с ID: {} успешно удалена";

    // ========== PAGINATION CONSTANTS ==========
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_FIELD = "createdAt";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";

    // ========== SECURITY CONSTANTS ==========
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_OPERATOR = "ROLE_OPERATOR";

    // Приватный конструктор для предотвращения создания экземпляров
    private ApplicationConstants() {
        throw new UnsupportedOperationException("Это класс утилита и не может быть инстанциирован");
    }
}
