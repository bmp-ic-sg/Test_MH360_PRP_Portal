package sg.ic.pg.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter T_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter D_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");//YYYY-MM-DDTHH:MM:SSZ
    private static final DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ofPattern("yyyy MM dd");
    private static final DateTimeFormatter GW_DOB_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private DateHelper() {
        // Empty Constructor
    }

    public static String formatDateTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(DT_FORMATTER);
        }
        return null;
    }

    public static String formatTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(T_FORMATTER);
        }
        return null;
    }

    public static String formatMEDateTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(ME_FORMATTER);
        }
        return null;
    }

    public static String parseMEDateTime(String str) {
        LocalDate ld = parseDate(str);
        LocalDateTime ldt = ld.atStartOfDay();
        return formatMEDateTime(ldt);
    }

    public static String formatDate(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(D_FORMATTER);
        }
        return null;
    }

    public static String formatDate(LocalDate dt) {
        if (dt != null) {
            return dt.format(D_FORMATTER);
        }
        return null;
    }

    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, DT_FORMATTER);
    }

    public static LocalDate parseDate(String str) {
        return LocalDate.parse(str, D_FORMATTER);
    }

    public static LocalDate parseDob(String str) {
        return LocalDate.parse(str, DOB_FORMATTER);
    }

    public static String formatGWDate(LocalDate dt) {
        if (dt != null) {
            return dt.format(GW_DOB_FORMATTER);
        }
        return null;
    }
}
