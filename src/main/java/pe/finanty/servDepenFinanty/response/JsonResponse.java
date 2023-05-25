package pe.finanty.servDepenFinanty.response;

import lombok.Data;

@Data
public class JsonResponse {

	private Boolean success;
	private Object data;
	private String message;

	public boolean isSuccess() {
		if (success == null){
			return true;
		}
		return success;
	}
}
