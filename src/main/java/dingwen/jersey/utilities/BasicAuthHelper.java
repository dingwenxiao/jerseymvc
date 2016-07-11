package dingwen.jersey.utilities;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dingwen.jersey.entity.User;
import dingwen.jersey.service.SettingService;
import dingwen.jersey.service.UserService;

public class BasicAuthHelper {
	private static final Logger log = LogManager
			.getLogger(BasicAuthHelper.class);

	public static boolean checkHeaderAuth(HttpServletRequest request)
			throws IOException {
		final int basicLength = 6;
		if (request.getSession().getAttribute("auth") != null) {
			return true;
		}

		String auth = request.getHeader("Authorization");

		if ((auth != null) && (auth.length() > basicLength)) {
			auth = auth.substring(basicLength, auth.length());
			String decodedAuth = getFromBASE64(auth);
			
			String[] nameAndPass = decodedAuth.split(":");
			User user = UserService.getUser(nameAndPass[0], nameAndPass[1]);
			log.debug("user name is {}", nameAndPass[0]);
			log.debug("password is {}", nameAndPass[1]);
			if (user != null) {
				request.getSession().setAttribute("userId", user.getUserId());
				request.getSession().setAttribute("settingMap",
						SettingService.getUserSettings(user.getUserId()));
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param s
	 *            base64 encoded authorization header
	 * @return plain text of authorization header
	 */
	public static String getFromBASE64(final String s) {
		if (s == null) {
			return null;
		}
		try {
			byte[] byteArray = Base64.decodeBase64(s.getBytes());
			return new String(byteArray);
		} catch (Exception e) {
			// log.error(e);
			return null;
		}
	}

}
