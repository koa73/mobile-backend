package ru.mobile.front.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;


@Component
public class Messages {

    @Value("${spring.mvc.locale}")
    private String defaultLocale;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(true);
        return messageSource;
    }

    @Autowired
    MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, new Locale(defaultLocale));
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }

    public String get(String code, String locale) {
        if (locale == null)
            locale = defaultLocale;
        accessor = new MessageSourceAccessor(messageSource, new Locale(locale));
        return accessor.getMessage(code);
    }
}