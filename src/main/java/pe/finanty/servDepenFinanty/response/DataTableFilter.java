package pe.finanty.servDepenFinanty.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataTableFilter {

    private int page;
    private String[] filters;
    private int rows;

    public DataTableFilter() {
    }


}
