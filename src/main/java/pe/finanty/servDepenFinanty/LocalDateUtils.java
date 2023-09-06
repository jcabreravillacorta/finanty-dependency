package pe.finanty.servDepenFinanty;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateUtils {

    public static LocalDate convertLocalDate(String fechaStr){

        return LocalDate.parse(fechaStr);
    }

    public static LocalDate convertLocalDateFormatter(String fechaStr, String formatter){

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formatter);
        return LocalDate.parse(fechaStr, formateador);
    }
}
