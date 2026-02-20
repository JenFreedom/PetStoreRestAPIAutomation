package api.utilities;

import java.util.Properties;
import java.util.ResourceBundle;

public class Routes {
	
//	private static Properties prop;
	
	// User Routes
	
	public static final String POST_URL;
	public static final String GET_URL;
	public static final String UPDATE_URL;
	public static final String DELETE_URL;
	public static String baseURL;
	
	static {
		ResourceBundle routes = ResourceBundle.getBundle("routes");
	//	String env = routes.getString("env");
		String env = System.getProperty("env")!=null
				? System.getProperty("env")
				:routes.getString("env");
		baseURL = routes.getString("base.url."+env);
		
		POST_URL = routes.getString("user.post");
		GET_URL = routes.getString("user.get");
		UPDATE_URL = routes.getString("user.update"); 
		DELETE_URL = routes.getString("user.delete"); 
	}

}
