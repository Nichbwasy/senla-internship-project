package com.senla.common.formaters;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Slf4j
public class TimestampFormatter {

    private final static String DEFAULT_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public static String formatToString(Timestamp timestamp) {
        return format(timestamp, DEFAULT_FORMAT);
    }

    public static String formatToString(Timestamp timestamp, String format) {
        return format(timestamp, format);
    }

    public static Timestamp formatToTimestamp(String time) {
        return formatString(time, DEFAULT_FORMAT);
    }

    public static Timestamp formatToTimestamp(String time, String format) {
        return formatString(time, format);
    }

    private static Timestamp formatString(String time, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return new Timestamp(dateFormat.parse(time).getTime());
        } catch (Exception e) {
            log.warn("Exception while parsing string to timestamp! {}.", e.getMessage());
            throw new RuntimeException("Exception while parsing string to timestamp! " + e.getMessage());
        }
    }

    private static String format(Timestamp timestamp, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(timestamp);
        } catch (Exception e) {
            log.warn("Exception while parsing timestamp to string! {}.", e.getMessage());
            throw new RuntimeException("Exception while parsing timestamp to string! " + e.getMessage());
        }
    }

}
