package org.sogeti.service.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import org.sogeti.bo.UserBean;
import org.sogeti.service.TwitterService;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
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
		// on recupere les friends et followers du user API
		init();
		// on nettoie la liste de friends
		Map<String, UserBean> mapClean = clean();
		// on boucle sur la map nettoyee
		while (mapClean.size() < 2000) {
			for (UserBean userBean : mapFriendUserBean.values()) {
				findNewFriends(Long.parseLong(userBean.getId()));
			}
		}

	}

	private void init() {
		// recherche des friends du user API
		mapFriendUserBean = TwitterService.getInstance().getFriendsUserBeanMap(
				TwitterService.APP_ACCOUNT_SCREENNAME);
		// recherche des followers du user API
		followersIdList = TwitterService.getInstance().getFollowersIDList(
				TwitterService.APP_ACCOUNT_SCREENNAME);
	}

	private Map<String, UserBean> clean() {
		// on boucle sur tous les friends et on nettoie
		Map<String, UserBean> list = null;
		return list;
	}

	private void maj(Map<String, UserBean> cleanedMap, User user) {
		// TODO
	}

	private void findNewFriends(long userId) {
		Twitter twitter = TwitterService.getInstance().getTwitter();
		try {
			//recupération des 5000 premiers ids
			IDs ids = null;
			do {
				if(ids!= null && ids.getRateLimitStatus().getRemaining()==0) {
						Thread.sleep(ids.getRateLimitStatus().getSecondsUntilReset()*1000 +5);
				}
				
				if(ids == null){
					//on recuperation des premiers 5000 ids
					ids = twitter.getFollowersIDs(userId,-1);
				}else{
					//recupération des ids au delà des 50000 premiers
					ids = twitter.getFollowersIDs(userId,ids.getNextCursor());
				}
				
				//à partir des ids, on recupère de vrais users 100 par 100
				long[] ids5000 = ids.getIDs();
				int startCurs=0;
				ResponseList<User> newUserList100 = null;
				while(startCurs<ids5000.length){
					long[] tab = Arrays.copyOfRange(ids5000, startCurs, startCurs+100);
					newUserList100 = twitter.lookupUsers(tab);
					//on pause pour pas bouuffer la limite imposer par Twitter
					Thread.sleep(newUserList100.getRateLimitStatus().getSecondsUntilReset()*1000/newUserList100.getRateLimitStatus().getRemaining());
					for (User user : newUserList100) {
						//on teste si le user peut être ajouter ou non
					}
					startCurs=startCurs+100;
				}
					
				
			
				
				
			} while (ids != null && ids.hasNext());
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//on boucle sur les ids  et on les split par 100
	
		//on fait une pause pour pas cramer les quotats Twitter
		
		// on recupere les followers sous forme de Twiiter4J.user
		
		
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
		return new RestServiceResponse("startManagement",
				String.valueOf(this.isStarted), new ArrayList<String>());
	}

	@ApiMethod(path = "stop", httpMethod = HttpMethod.POST)
	public RestServiceResponse stopManagement() {

		if (this.isStarted) {
			this.isStarted = false;
		}
		return new RestServiceResponse("stopManagement",
				String.valueOf(this.isStarted), new ArrayList<String>());
	}

	@ApiMethod(path = "isRunning", httpMethod = HttpMethod.POST)
	public RestServiceResponse isRunning() {
		return new RestServiceResponse("isRunning",
				String.valueOf(this.isStarted), new ArrayList<String>());
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
