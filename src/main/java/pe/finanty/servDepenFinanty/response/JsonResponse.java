package pe.finanty.servDepenFinanty.response;

import lombok.Data;

@Data
public class JsonResponse {
	Boolean success;
	Object data;
	String message;

	public boolean isSuccess() {
		if (success == null){
			return true;
		}
		return success;
	}
}
