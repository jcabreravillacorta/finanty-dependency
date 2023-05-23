package pe.finanty.servDepenFinanty.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataTableResponse {

    private Object data;
    private long totalData;
    private Integer totalPage;

}
