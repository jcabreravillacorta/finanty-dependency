package pe.finanty.servDepenFinanty;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;

public interface UtilsExcel {

    static String formatDateParam = "dd/MM/yyyy";

     static String getDatoString(Cell cell){

        return getDatoString(cell, formatDateParam, null);
     }

    static String getDatoString(Cell cell, String formatDate, CellType cellType){
        DataFormatter formatter = new DataFormatter();

        if (cell == null) {
            return "";
        }
        if (cellType != null) {
            cell.setCellType(cellType);
        }
        if (CellType.NUMERIC == cell.getCellType()) {

            if (DateUtil.isCellDateFormatted(cell)) {
                String pattern = formatDate;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String value = simpleDateFormat.format(cell.getDateCellValue());
                return value;
            } else {
                String value = formatter.formatCellValue(cell);
                if (Utils.esNumerico(value)) {
                    return value;
                }
                return value;
            }
        } else if (CellType.STRING == cell.getCellType()) {
            String value = formatter.formatCellValue(cell) == null ? "" : formatter.formatCellValue(cell);

            if (Utils.esNumerico(value)) {
                return value;
            }

        }

        return formatter.formatCellValue(cell);
    }
}
