package org.sogeti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sogeti.bo.UserBean;
import org.sogeti.service.rest.RestServiceResponse;

import com.googlecode.objectify.ObjectifyService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.json.impl.provider.entity.JSONObjectProvider;
import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;


@SuppressWarnings("serial")
public class TwitterManagerServlet extends HttpServlet {

	private static Logger LOGGER = Logger.getLogger(TwitterManagerServlet.class
			.toString());
	
	private static String START_SERVICE_ENDPOINT="v1/start";
	private static String STOP_SERVICE_ENDPOINT="v1/stop";
	private static String ISRUNNING_SERVICE_ENDPOINT="v1/isRunning";
	

	static {
		ObjectifyService.register(UserBean.class); // Fait connaître votre
													// classe-entité à Objectify
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		LOGGER.log(Level.INFO, "Entree servlet TwitterManagerServlet");
		req.setAttribute("isRunning", isRunnningService().contains("true")?"true":"false");
		
		try {
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/jsp/twitterManager.jsp")
					.forward(req, resp);
		} catch (ServletException e) {
			LOGGER.log(
					Level.SEVERE,
					"Un problème est survenu avec le traitement de la jsp '/WEB-INF/jsp/twitterManager.jsp'");
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String isRunning = req.getParameter("isRunning");
		String typeSubmit = req.getParameter("typeSubmit");
		if(typeSubmit==null) {
			isRunning=isRunnningService().contains("true")?"true":"false";
		} else if(typeSubmit.equals("start")){
			startService();
		} else if(typeSubmit.equals("stop")){
			stopService();
		} else {
			isRunning=isRunnningService().contains("true")?"true":"false";
		}
		isRunning=isRunnningService().contains("true")?"true":"false";
		req.setAttribute("isRunning", isRunning);
		try {
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/jsp/twitterManager.jsp")
					.forward(req, resp);
		} catch (ServletException e) {
			LOGGER.log(
					Level.SEVERE,
					"Un problème est survenu avec le traitement de la jsp '/WEB-INF/jsp/twitterManager.jsp'");
			e.printStackTrace();
		}
	}
	
	private String startService() {
		 return callRestService(START_SERVICE_ENDPOINT);
	}
	
	private String stopService() {
		 return callRestService(STOP_SERVICE_ENDPOINT);
	}
	
	private String isRunnningService() {
		 return callRestService(ISRUNNING_SERVICE_ENDPOINT);
	}

	private String callRestService(String action) {

		String reponse = "";
		try {

			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);
			WebResource webResource = client
					.resource("http://localhost:8888/_ah/api/manageUsers/"+action);

			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException(
						" Erreur lors de l'appel du service "+action+" : "
								+ response.getStatus());
			}

			reponse = response
					.getEntity(String.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reponse;
		
	}
	
	
}
