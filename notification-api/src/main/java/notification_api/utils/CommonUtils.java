package notification_api.utils;


import org.springframework.util.ObjectUtils;

import java.util.UUID;

public class CommonUtils {
    public static boolean isEmpty(final Object object) {
        return ObjectUtils.isEmpty(object);
    }
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
