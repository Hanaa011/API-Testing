package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    // Generate today's date in yyyy-MM-dd format
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
