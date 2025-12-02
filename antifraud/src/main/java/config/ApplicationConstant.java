package config;

public class ApplicationConstant {

    public static final String OPERATION_UPDATE = "UPDATE";
    public static final String OPERATION_CREATE = "CREATE";
    public static final String OPERATION_DELETE = "DELETE";

    public static final String TYPE_CARD = "card";
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_ACCOUNT = "account";

    public static final String MSG_CREATED = "СОЗДАНА подозрительная транзакция: Тип={}, Метод={}, Данные={}";
    public static final String MSG_UPDATED = "ОБНОВЛЕНА подозрительная транзакция: Тип={}, ID={}, Данные={}";
    public static final String MSG_DELETED = "УДАЛЕНИЕ подозрительной транзакции: Тип={}, ID={}";
    public static final String MSG_RECEIVED = "Получена подозрительная транзакция: Тип={}, ID={}";
    public static final String MSG_ERR_SERVICE = "ОШИБКА в сервисе подозрительных транзакций: Метод={}, Ошибка={}";
    public static final String MSG_ERR_ASPECT_CREATE = "Ошибка в аспекте при логировании создания: {}";
    public static final String MSG_ERR_ASPECT_UPDATE = "Ошибка в аспекте при логировании обновления: {}";
    public static final String MSG_ERR_ASPECT_DELETE = "Ошибка в аспекте при логировании удаления: {}";
    public static final String MSG_ERR_ASPECT_GET = "Ошибка в аспекте при логировании получения по ID: {}";

    public static final String ERR_NOT_FOUND_TEMPLATE = "%s не найден с ID: %s";
    public static final String ERR_CARD_NOT_FOUND = "Карта";
    public static final String ERR_PHONE_NOT_FOUND = "Телефон";
    public static final String ERR_ACCOUNT_NOT_FOUND = "Аккаунт";
    public static final String ERR_INVALID_TYPE = "Недопустимый тип транзакции: ";

    public static final String POINTCUT_SERVICE = "execution(* service.SuspiciousTransferServiceImpl.*(..))";
    public static final String POINTCUT_CREATE = "execution(* service.SuspiciousTransferServiceImpl.create*(..))";
    public static final String POINTCUT_UPDATE = "execution(* service.SuspiciousTransferServiceImpl.update*(..))";
    public static final String POINTCUT_DELETE = "execution(* service.SuspiciousTransferServiceImpl.deleteSuspiciousTransfer(..))";
    public static final String POINTCUT_GET_BY_ID = "execution(* service.SuspiciousTransferServiceImpl.get*ById(..))";
}
