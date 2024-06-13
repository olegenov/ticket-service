package org.tickets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.tickets.filters.JwtAuthenticationFilter;

@Configuration
public class FilterConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(restTemplate));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}

