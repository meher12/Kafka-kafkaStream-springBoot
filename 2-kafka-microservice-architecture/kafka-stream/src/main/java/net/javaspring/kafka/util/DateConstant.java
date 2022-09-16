package net.javaspring.kafka.util;

import java.time.format.DateTimeFormatter;

public interface DateConstant {

    String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_FORMAT);

}