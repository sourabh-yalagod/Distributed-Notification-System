package notification_api.models.context;

public final class NotificationContextHolder {
    public static final ThreadLocal<NotificationContext> NOTIFICATION_CONTEXT_THREAD_LOCAL = new ThreadLocal<NotificationContext>();

    public static void setNotificationContext(NotificationContext context) {
        NOTIFICATION_CONTEXT_THREAD_LOCAL.set(context);
    }

    public static NotificationContext getNotificationContext() {
        return NotificationContextHolder.NOTIFICATION_CONTEXT_THREAD_LOCAL.get();
    }

    public static void clearNotificationContext() {
        NotificationContextHolder.NOTIFICATION_CONTEXT_THREAD_LOCAL.remove();
    }
}