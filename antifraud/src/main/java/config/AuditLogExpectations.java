package config;

public final class AuditLogExpectations {

    public static final Long TEST_ENTITY_ID = 10L;
    public static final Long TEST_GET_ID = 7L;
    public static final Long TEST_UPDATE_ID = 5L;

    public static final String TYPE_CARD = "CARD";
    public static final String TYPE_PHONE = "PHONE";
    public static final String TYPE_ACCOUNT = "ACCOUNT";

    public static final String METHOD_CREATE_CARD = "createCardTransfer";
    public static final String METHOD_CREATE_PHONE = "createPhoneTransfer";
    public static final String METHOD_CREATE_ACCOUNT = "createAccountTransfer";
    public static final String METHOD_UPDATE_ACCOUNT = "updateAccount";
    public static final String METHOD_GET_CARD = "getCardById";
    public static final String METHOD_DELETE = "deleteSuspiciousTransfer";

    public static final String ERROR_MESSAGE_DB = "Database error";
    public static final String ERROR_MESSAGE_VALIDATION = "Validation failed";

    public static final String LOG_ENTITY_CARD = "CardTransfer";
    public static final String LOG_ENTITY_PHONE = "PhoneTransfer";
    public static final String LOG_ENTITY_ACCOUNT = "AccountTransfer";

    public static final String LOG_BLOCKED_TRUE = "Заблокирована=true";
    public static final String LOG_BLOCKED_FALSE = "Заблокирована=false";
    public static final String LOG_SUSPICIOUS_TRUE = "Подозрительна= true";
    public static final String LOG_SUSPICIOUS_FALSE = "Подозрительна= false";

    public static String LOG_BLOCKED_REASON() {
        return "Причина= " + TestConstants.BLOCKED_REASON;
    }

    public static String LOG_SUSPICIOUS_REASON() {
        return "Причина подозрительности= " + TestConstants.SUSPICIOUS_REASON;
    }
}
