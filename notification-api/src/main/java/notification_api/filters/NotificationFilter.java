package notification_api.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notification_api.controller.AppConstants;
import notification_api.models.context.NotificationContext;
import notification_api.models.context.NotificationContextHolder;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class NotificationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isValidApiPath(request.getRequestURI())) {
            String tenantId = request.getHeader(AppConstants.X_TENANT_ID);
            if (tenantId.isBlank()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized! API Keys required...!");
                return;
            }
            NotificationContext notificationContext = new NotificationContext(tenantId);
            NotificationContextHolder.setNotificationContext(notificationContext);
        }
        filterChain.doFilter(request, response);
        if (isValidApiPath(request.getRequestURI())) {
            NotificationContextHolder.clearNotificationContext();
        }
    }

    private boolean isValidApiPath(String requestPath) {
        return requestPath.startsWith("/api");
    }
}
