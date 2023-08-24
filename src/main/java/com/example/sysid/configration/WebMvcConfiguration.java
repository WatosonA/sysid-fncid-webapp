package com.example.sysid.configration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEBアプリケーションの設定.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // インターセプターの追加
    }

}
