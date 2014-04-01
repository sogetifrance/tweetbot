package org.sogeti.service.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.sogeti.BeanMapper;
import org.sogeti.bo.UserBean;
import org.sogeti.service.TwitterService;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;


public class ManageUsersService {

	private boolean isStarted = false;
	private Map<String, UserBean> mapFriendUserBean;
	private List<Long> followersIdList;
	private List<Long> friendsIdList;

	public void traitementPrincipal() {
		// on recupere les friends et followers du user API
		isStarted = true;
		try {
			for (int i = 1; i < 12; i++) {
				Thread.currentThread().sleep(60000);
				System.out.println(i+ " minute(s) de traitement");
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("service tterminé");
//		init();
//		// on nettoie la liste de friends
//		clean();
//		// on boucle sur la map nettoyee
//		for (UserBean userBean : mapFriendUserBean.values()) {
//			if (friendsIdList.size() < 2000) {
//				findNewFriends(userBean.getId());
//			} else {
//				break;
//			}
//		}
		isStarted = false;
	}

	private void init() {
		// recherche des friends du user API
		mapFriendUserBean = TwitterService.getInstance().getFriendsUserBeanMap(
				TwitterService.APP_ACCOUNT_SCREENNAME);
		// recherche des followers du user API
		followersIdList = TwitterService.getInstance().getFollowersIDList(
				TwitterService.APP_ACCOUNT_SCREENNAME);
		friendsIdList = new ArrayList<Long>();
	}

	private void clean() {
		// on boucle sur tous les friends et on nettoie
		for (UserBean friend : mapFriendUserBean.values()) {
			friendsIdList = MajManager.maj(followersIdList, friendsIdList,
					false, friend);
		}
	}

	private void findNewFriends(Long userId) {
		Twitter twitter = TwitterService.getInstance().getTwitter();
		try {
			// recupération des 5000 premiers ids
			IDs ids = null;
			boolean isFriend2000 = false;
			do {
				if (ids != null && ids.getRateLimitStatus().getRemaining() == 0) {
					Thread.sleep(ids.getRateLimitStatus()
							.getSecondsUntilReset() * 1000 + 5);
				}

				if (ids == null) {
					// on recuperation des premiers 5000 ids
					ids = twitter.getFollowersIDs(userId, -1);
				} else {
					// recupération des ids au delà des 50000 premiers
					ids = twitter.getFollowersIDs(userId, ids.getNextCursor());
				}

				// à partir des ids, on recupère de vrais users 100 par 100
				long[] ids5000 = ids.getIDs();
				int startCurs = 0;
				ResponseList<User> newUserList100 = null;
				while (isStarted && startCurs < ids5000.length && !isFriend2000) {
					long[] tab = Arrays.copyOfRange(ids5000, startCurs,
							startCurs + 100);
					newUserList100 = twitter.lookupUsers(tab);
					// on pause pour pas bouuffer la limite imposer par Twitter
					Thread.sleep(newUserList100.getRateLimitStatus()
							.getSecondsUntilReset()
							* 1000
							/ newUserList100.getRateLimitStatus()
									.getRemaining());
					for (User user : newUserList100) {
						// on teste si le user peut être ajouter ou non
						if (friendsIdList.size() < 2000) {
							friendsIdList = MajManager.maj(followersIdList,
									friendsIdList, true,
									BeanMapper.getUserBeanFromUser(user));
						} else {
							isFriend2000 = true;
							break;
						}
					}
					startCurs = startCurs + 100;
				}
			} while (isStarted && ids != null && ids.hasNext() && !isFriend2000);

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

	}


	public void stopManagement() {

		if (this.isStarted) {
			this.isStarted = false;
		}
	}

	public boolean isRunning() {
		return this.isStarted;
	}

}
