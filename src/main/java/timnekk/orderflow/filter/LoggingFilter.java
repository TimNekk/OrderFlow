package timnekk.orderflow.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails userDetails) {
                String username = userDetails.getUsername();
                MDC.put("username", username);
            }

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
