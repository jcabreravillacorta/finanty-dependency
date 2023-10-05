package pe.finanty.servDepenFinanty;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateUtils {

    /**
     * Método que convierte un valor String a LocalDate
     *
     * @param fechaStr ejemplo "2023-09-06" como formato predetermiando yyyy-MM-dd.
     * @return retorna un LocalDate.
     */
    public static LocalDate convertLocalDate(String fechaStr){

        return LocalDate.parse(fechaStr);
    }

    /**
     * Método que convierte un valor String a LocalDate
     *
     * @param fechaStr ejemplo "06/09/2023".
     * @param formatter  En formato ejemplo dd/MM/yyyy.
     * @return retorna un LocalDate.
     */
    public static LocalDate convertLocalDateFormatter(String fechaStr, String formatter){

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formatter);
        return LocalDate.parse(fechaStr, formateador);
    }

    /**
     * Método que convierte un LocalDate a un String
     *
     * @param fecha ejemplo "06/09/2023".
     * @param formatter  En formato ejemplo dd/MM/yyyy.
     * @return retorna un LocalDate.
     */
    public static String convertLocalDateToStringFormatter(LocalDate fecha, String formatter){

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formatter);
        return fecha.format(formateador);
    }
}
