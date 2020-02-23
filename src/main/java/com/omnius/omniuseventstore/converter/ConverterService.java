package com.omnius.omniuseventstore.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class ConverterService extends GenericConversionService {

    @Autowired
    private List<Converter<?, ?>> converters;

    @PostConstruct
    public void init() {
        converters.forEach(this::addConverter);
    }
}
