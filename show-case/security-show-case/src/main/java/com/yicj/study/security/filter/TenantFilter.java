package com.yicj.study.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * <p>
 * TenantFilter
 * </p>
 *
 * @author yicj
 * @since 2024/08/31 21:59
 */
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String tenantId = request.getHeader("X-Tenant-Id");
        boolean hasAccess = isUserAllowed(tenantId);
        if (hasAccess) {
            chain.doFilter(request, response);
            return;
        }
        throw new AccessDeniedException("Access denied");
    }

    private boolean isUserAllowed(String tenantId) {

        return true;
    }
}
