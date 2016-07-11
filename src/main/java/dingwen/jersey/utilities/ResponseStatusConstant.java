package dingwen.jersey.utilities;
/**
 * response status constant.
 * @author dixiao
 *
 */
public class ResponseStatusConstant {
	public final static int OK = 200;
	public final static int No_Content = 204;
	public final static int Moved_Permanently = 301;
	public final static int Temporary_Redirect = 307;
	public final static int Bad_Request = 400;
	public final static int Not_Found = 404;
	public final static int Unprocessable_Entity = 422;
	public final static int Internal_Server_Error = 500;
	public final static int Service_Unavailable = 503;


	private ResponseStatusConstant() {
	}

}
