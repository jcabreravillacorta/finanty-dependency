package pe.finanty.servDepenFinanty;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.text.SimpleDateFormat;
@Slf4j
public class UtilsExcel {

    public static String formatDateParam = "dd/MM/yyyy";

    public  static String getDatoString(Cell cell){

        return getDatoString(cell, formatDateParam, null);
     }

    public static String getDatoString(Cell cell, String formatDate, CellType cellType){
        DataFormatter formatter = new DataFormatter();

        if (cell == null) {
            return "";
        }
        if (cellType != null) {
            cell.setCellType(cellType);
        }
        if (CellType.NUMERIC == cell.getCellType()) {

            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
                return simpleDateFormat.format(cell.getDateCellValue());
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

    public static CellReference getNameCelda(Row row, Cell cell){

        return new CellReference(row.getRowNum(), cell.getColumnIndex());
    }
}
