package com.dethlex.numberconverter.date;

import com.dethlex.numberconverter.ConvertTypes;
import com.dethlex.numberconverter.common.IConverter;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;
import com.dethlex.numberconverter.number.ConvertNumber;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public final class ConvertDate extends IConverter {

    Date date;

    public ConvertDate(String value) {
        value = new ConvertNumber(value).toString(ConvertTypes.DEC);

        Instant instant;
        if (value.length() > 10) {
            instant = Instant.ofEpochMilli(Long.parseLong(value));
        } else {
            instant = Instant.ofEpochSecond(Long.parseLong(value));
        }

        date = Date.from(instant);
    }

    public String toString(ConvertTypes system) {
        String pattern = PluginPersistentStateComponent.getInstance().getDateTimeFormat();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
