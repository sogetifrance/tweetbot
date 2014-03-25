package org.sogeti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sogeti.bo.UserBean;

import com.googlecode.objectify.ObjectifyService;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class TwitterManagerServlet extends HttpServlet {
	
	
	static {
        ObjectifyService.register(UserBean.class); // Fait connaître votre classe-entité à Objectify
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, man");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setPrettyDebugEnabled(true);
		cb.setOAuthConsumerKey("7JacUNxCHthyzaOqagu43Q");// API key
		cb.setOAuthConsumerSecret("HtWXpUTat3S6efysTDLB6xOWswZvgjoho9S0geHRO0");// API
																				// secret
		cb.setOAuthAccessToken("141688408-mJPOCIKbM9s27F8X4IqlEkQWwlgzAS56HIUwUL39");
		cb.setOAuthAccessTokenSecret("LMmXnjMBbQxbs0sTTJAxyHO3X6NWgrssiiW6mykeMMm0X");

		try {
			String screenName = "mfoucaud";
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			List<User> usersList = new ArrayList<User>();
			List<UserBean> userBeanList = new ArrayList<UserBean>();
			usersList = twitter.getFollowersList(screenName, -1);
			for (User user : usersList) {
				UserBean ub = BeanMapper.getUserBeanFromUser(user);
				userBeanList.add(ub);
				ofy().save().entity(ub);
			}
			
			req.setAttribute("users", userBeanList);
			this.getServletContext().getRequestDispatcher("/WEB-INF/twitterManager.jsp").forward(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
