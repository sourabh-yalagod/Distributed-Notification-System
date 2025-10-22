package notification_api.utils;


import org.springframework.util.ObjectUtils;

public class CommonUtils {
    public static boolean isEmpty(final Object object) {
        return ObjectUtils.isEmpty(object);
    }
}
