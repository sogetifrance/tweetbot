package org.sogeti;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sogeti.bo.UserBean;
import org.sogeti.service.rest.ManageUsersService;

import com.google.appengine.api.ThreadManager;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class TwitterManagerServlet extends HttpServlet {

	private static Logger LOGGER = Logger.getLogger(TwitterManagerServlet.class
			.toString());

	private ManageUsersService manageService;
	static {
		ObjectifyService.register(UserBean.class); // Fait connaître votre
													// classe-entité à Objectify
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		LOGGER.log(Level.INFO, "Entree servlet TwitterManagerServlet");
		req.setAttribute("isRunning", String.valueOf(isRunningService()));

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
		String typeSubmit = req.getParameter("typeSubmit");
		if (typeSubmit != null) {
			if (typeSubmit.equals("start")) {
				startService(req);
			} else if (typeSubmit.equals("stop")) {
				stopService();
			}
		}
		
		req.setAttribute("isRunning", String.valueOf(this.isRunningService()));
		
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

	private void startService(HttpServletRequest req) {
		if (!isRunningService()) {
			try {
				Runnable send = new Runnable() {
					public void run() {
						manageService = new ManageUsersService();
						manageService.traitementPrincipal();
					}
				};

				ThreadFactory threadFactory = ThreadManager
						.backgroundThreadFactory();
				Thread thread = threadFactory.newThread(send);
				thread.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			req.setAttribute("erreurMessage",
					"Le service est déjà démarré");
		}

	}

	private void stopService() {
		manageService.stopManagement();
	}

	private boolean isRunningService() {
		return (manageService!=null && manageService.isRunning());
	}
}
