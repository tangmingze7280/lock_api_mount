package cn.mzmzup.config;


import cn.mzmzup.awt.TokenAwt;
import cn.mzmzup.inteceptor.ApiLockUnlockInteceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AutoConfigureAfter(value = TokenAwt.class)
public class ApiLockAutoConfiguration implements WebMvcConfigurer {
    @Value("${cn.mzmzup.pattens:'/**/**'}")
    private List<String> pattens;

    @Bean
    public ApiLockUnlockInteceptor apiLockUnlockInteceptor(){
        return new ApiLockUnlockInteceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLockUnlockInteceptor()).excludePathPatterns(pattens);
    }
}
