package com.nc.resume._project.resume.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageSourceUtil {

	private final MessageSource messageSource;
	private  Locale locale = LocaleContextHolder.getLocale();
	
	public MessageSourceUtil(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String getMessage(String code, Object[] args, Locale locale) {
	    return messageSource.getMessage(code, args, locale);
	}
	
	public String getMessage(String code, Object[] args) {
		this.locale=Locale.KOREA;
	    return messageSource.getMessage(code, args, this.locale);
	}
	
	public String getMessage(String code) {
		this.locale=Locale.KOREA;
	    return messageSource.getMessage(code, null, this.locale);
	}
	
}
