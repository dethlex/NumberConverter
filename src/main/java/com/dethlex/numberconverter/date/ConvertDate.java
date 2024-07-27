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
        value = new ConvertNumber(value).toString(ConvertType.DEC);

        Instant instant;
        if (value.length() > 10) {
            instant = Instant.ofEpochMilli(Long.parseLong(value));
        } else {
            instant = Instant.ofEpochSecond(Long.parseLong(value));
        }

        this.date = Date.from(instant);
    }

    public static Date fromString(String value) {
        String pattern = PluginPersistentStateComponent.getInstance().getDateTimeFormat();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

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
