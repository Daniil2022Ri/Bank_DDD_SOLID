package config;

public class TestConstants {
    private TestConstants() {}

    public static final Long TEST_ID = 1L;
    public static final Long NON_EXISTENT_ID = 999L;

    public static final boolean BLOCKED_TRUE = true;
    public static final boolean BLOCKED_FALSE = false;
    public static final boolean SUSPICIOUS_TRUE = true;
    public static final boolean SUSPICIOUS_FALSE = false;

    public static final String BLOCKED_REASON = "Тестовая блокировка";
    public static final String SUSPICIOUS_REASON = "Подозрительная активность";
    public static final String USERNAME = "test-user";
    public static final String ENTITY_TYPE_CARD = "SUSPICIOUS_CARD_TRANSFER";
    public static final String ENTITY_TYPE_PHONE = "SUSPICIOUS_PHONE_TRANSFER";
    public static final String ENTITY_TYPE_ACCOUNT = "SUSPICIOUS_ACCOUNT_TRANSFER";

    public static final String OPERATION_CREATE = "CREATE";
    public static final String OPERATION_UPDATE = "UPDATE";
}
