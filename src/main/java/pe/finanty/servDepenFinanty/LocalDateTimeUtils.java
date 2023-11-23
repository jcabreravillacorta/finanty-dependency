package pe.finanty.servDepenFinanty;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateTimeUtils {

    /**
     * Método que convierte un valor String a LocalDateTime
     *
     * @param fechaStr ejemplo "2023-11-23T15:30:00" como formato predetermiando yyyy-MM-ddTHH:mm:ss.
     * @return retorna un LocalDateTime.
     */
    public static LocalDateTime convertLocalDateTime(String fechaStr){

        return LocalDateTime.parse(fechaStr);
    }

    /**
     * Método que convierte un valor String a LocalDateTime
     *
     * @param fechaStr ejemplo "23-11-2023 15:30:59".
     * @param formatter  En formato ejemplo dd-MM-yyyy HH:mm:ss.
     * @return retorna un LocalDateTime.
     */
    public static LocalDateTime convertLocalDateTimeFormatter(String fechaStr, String formatter){

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formatter);
        return LocalDateTime.parse(fechaStr, formateador);
    }

    /**
     * Método que convierte un LocalDateTime a un String
     *
     * @param fecha ejemplo "23-11-2023 15:30:59".
     * @param formatter  En formato ejemplo yyyy-MM-dd HH:mm:ss.
     * @return retorna un LocalDate.
     */
    public static String convertLocalDateTimeToStringFormatter(LocalDateTime fecha, String formatter){

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formatter);
        return fecha.format(formateador);
    }
}
