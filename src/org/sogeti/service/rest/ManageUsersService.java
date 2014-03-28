package org.sogeti.service.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import org.sogeti.bo.UserBean;

import twitter4j.User;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.ThreadManager;

@Api(name = "manageUsers", description = "permet de controler le traitement de reactualisation des friends")
public class ManageUsersService {

	private boolean isStarted = false;
	private Map<String, UserBean> mapFriendUserBean;
	private List<Long> followersIdList;

	private void traitementPrincipal() {
		init();
		clean();
	}

	private void init() {
		// TODO
	}

	private void clean() {
		// TODO
	}

	private void maj() {
		// TODO
	}

	private void findNewFriends(User friend) {

	}

	@ApiMethod(path = "start", httpMethod = HttpMethod.POST

	)
	public RestServiceResponse startManagement() {
		if (!this.isStarted) {
			this.isStarted = true;
			try {
				Runnable manage = new Runnable() {
					public void run() {
						manageFriends();
					}
				};

				ThreadFactory threadFactory = ThreadManager
						.backgroundThreadFactory();
				Thread thread = threadFactory.newThread(manage);
				thread.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.isStarted = false;
			}
		}
		return new RestServiceResponse("startManagement", String.valueOf(this.isStarted),
				new ArrayList<String>());
	}

	@ApiMethod(path = "stop", httpMethod = HttpMethod.POST)
	public RestServiceResponse stopManagement() {

		if (this.isStarted) {
			this.isStarted = false;
		}
		return new RestServiceResponse("stopManagement", String.valueOf(this.isStarted),
				new ArrayList<String>());
	}

	@ApiMethod(path = "isRunning", httpMethod = HttpMethod.POST)
	public RestServiceResponse isRunning() {
		return new RestServiceResponse("isRunning", String.valueOf(this.isStarted),
				new ArrayList<String>());
	}

	private void manageFriends() {
		int count = 1;
		Thread thread = Thread.currentThread();

		synchronized (thread) {
			while (this.isStarted) {
				try {
					traitementPrincipal();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
