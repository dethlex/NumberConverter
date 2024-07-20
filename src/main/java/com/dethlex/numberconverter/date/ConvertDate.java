package com.dethlex.numberconverter.date;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.common.IConverter;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;
import com.dethlex.numberconverter.number.ConvertNumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
        String pattern = PluginPersistentStateComponent.getInstance().getDateTimeFormat();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
