package com.dethlex.numberconverter.date;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.common.IConverter;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;
import com.dethlex.numberconverter.number.ConvertNumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

public final class ConvertDate extends IConverter {
    private final Date date;

    public ConvertDate(String value) {
        value = value.strip();

        Instant instant;
        Date parsed = fromString(value);
        if (parsed != null) {
            instant = parsed.toInstant();
        } else {
            String decimal = new ConvertNumber(value).toString(ConvertType.DEC);
            String digits = decimal.startsWith("-") ? decimal.substring(1) : decimal;
            if (digits.length() > 10) {
                instant = Instant.ofEpochMilli(Long.parseLong(decimal));
            } else {
                instant = Instant.ofEpochSecond(Long.parseLong(decimal));
            }
        }

        this.date = Date.from(instant);
    }

    public static Date fromString(String value) {
        var state = PluginPersistentStateComponent.getInstance();
        String pattern = state.getDateTimeFormat();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        if (state.isDateTimeUTC()) {
            formatter.setTimeZone(TimeZone.getTimeZone(ZoneId.from(ZoneOffset.UTC)));
        }
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String toString(ConvertType system) {
        var state = PluginPersistentStateComponent.getInstance();
        String pattern = state.getDateTimeFormat();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        if (state.isDateTimeUTC()) {
            formatter.setTimeZone(TimeZone.getTimeZone(ZoneId.from(ZoneOffset.UTC)));
        }
        return formatter.format(date);
    }
}
