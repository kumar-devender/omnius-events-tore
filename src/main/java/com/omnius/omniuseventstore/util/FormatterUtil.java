package com.omnius.omniuseventstore.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;

@UtilityClass
public class FormatterUtil {
    private static final DateTimeFormatter FORMATTER = ofPattern("MM/dd/yyyy hh:mm a");

    public String formatToString(LocalDateTime localDateTime) {
        return ofNullable(localDateTime)
                .map(FORMATTER::format)
                .orElse("");
    }

    public Optional<LocalDateTime> parseFromString(String stringFormat) {
        return ofNullable(stringFormat)
                .map(dateTime -> LocalDateTime.parse(dateTime, FORMATTER));
    }
}
