package net.javaspring.kafka.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtil {

    // LocalDateTime to epoch timestamp
    public static long toEpochTimestamp(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
