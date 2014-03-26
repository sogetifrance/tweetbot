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
import org.sogeti.service.TwitterService;

import com.googlecode.objectify.ObjectifyService;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class TwitterManagerServlet extends HttpServlet {
	
	private static Logger LOGGER = Logger.getLogger(TwitterManagerServlet.class.toString());
	
	static {
        ObjectifyService.register(UserBean.class); // Fait connaître votre classe-entité à Objectify
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		LOGGER.log(Level.INFO,"Entree servlet TwitterManagerServlet");		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, man");
		
		List<UserBean> userBeanList = new ArrayList<UserBean>();

		
		
		req.setAttribute("users", userBeanList);
		try {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/twitterManager.jsp").forward(req, resp);
		} catch (ServletException e) {
			LOGGER.log(Level.SEVERE,"Un problème est survenu avec le traitement de la jsp '/WEB-INF/jsp/twitterManager.jsp'");
			e.printStackTrace();
		}
	}
}
