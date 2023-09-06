package pe.finanty.servDepenFinanty;

import lombok.extern.slf4j.Slf4j;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que para conversión a localTime
 */
@Slf4j
public class LocalTimeUtils {

    /**
     * Método que convierte un valor String a LocalTime
     *
     * @param timeStr  En formato predeterminado HH:mm:ss ejemplo "12:34:56".
     * @return retorna un LocalTime.
     */
    public static LocalTime convertLocalTime(String timeStr){

        return LocalTime.parse(timeStr);
    }

    /**
     * Método que convierte un valor String a LocalTime
     *
     * @param timeStr ejemplo "12.34.56".
     * @param formatter  En formato ejemplo HH.mm.ss.
     * @return retorna un LocalTime.
     */
    public static LocalTime convertLocalTimeFormatter(String timeStr, String formatter){

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formatter);
        return LocalTime.parse(timeStr, formateador);
    }
}
