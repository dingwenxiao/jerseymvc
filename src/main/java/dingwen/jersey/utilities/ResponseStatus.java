package dingwen.jersey.utilities;

public enum ResponseStatus {
	No_Content(204), Moved_Permanently(301), Temporary_Redirect(307), Bad_Request(400), Not_Found(
			404), Unprocessable_Entity(422), Internal_Server_Error(500), Service_Unavailable(503);

	private final int statusCode;

	ResponseStatus(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		switch (statusCode) {
		case 204:
			return "successfully : " + statusCode;
		case 301:
			return "Moved Permanently : " + statusCode;
		case 307:
			return "Temporary Redirect : " + statusCode;
		case 400:
			return "Bad Request : " + statusCode;
		case 404:
			return "Not Found : " + statusCode;
		case 422:
			return "Unprocessable Entity : " + statusCode;
		case 500:
			return "Internal Server Error : " + statusCode;
		default:
			return "Service Unavailable : " + statusCode;
		}
	}
}
