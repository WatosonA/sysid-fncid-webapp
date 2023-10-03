package com.example.sysid.component.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.sysid.util.RequestId;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * リクエストIDの取得or生成してログ等でリクエストを一意に識別できるようにするフィルター。
 */
public class RequestIdGenerateFilter extends OncePerRequestFilter {

    private String requestIdHeaderKey = "x-request-id";

    public RequestIdGenerateFilter(String requestIdHeaderKey) {
        this.requestIdHeaderKey = requestIdHeaderKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String headValue = request.getHeader(requestIdHeaderKey);
        String uuid = StringUtils.hasLength(headValue) ? headValue : UUID.randomUUID().toString();

        try {
            // どこからでもリクエストIDが取得できるように設定しておく
            RequestId.REQUEST_ID.set(uuid);
            // ログに出力できるように設定
            MDC.put(RequestId.ATTRIBUTE_KEY, uuid);
            filterChain.doFilter(request, response);

        } finally {
            MDC.remove(RequestId.ATTRIBUTE_KEY);
            RequestId.REQUEST_ID.remove();
        }
    }
}
