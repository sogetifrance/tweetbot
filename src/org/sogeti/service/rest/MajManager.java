package org.sogeti.service.rest;

import java.util.List;

import org.sogeti.bo.UserBean;
import org.sogeti.service.ScoreService;
import org.sogeti.service.TwitterService;

public class MajManager {

	public static List<Long> maj(List<Long> followersIds, List<Long> friendIds,
			boolean isNew, UserBean user) {
		if (isNew && !isFriend(friendIds, user.getId())) {
			if (isfollower(followersIds, user.getId())) {
				majBdd(user);
			} else {
				if (!getScoreOk(user.getDescription())) {
					majBdd(user);
				}
			}
			// TODO Recuperer en base
			if (friendIds.contains(user.getId())) {
				UserBean userBdd = new UserBean(); // = friendIds.get(user.getId());
				// TODO voir si c'est bien ce paramétre a tester si il a dejé
				// était delete
				if (userBdd.getStatusesCount() == 0) {
					// Todo MAJ Friends
				}
				if (user != userBdd) {
					majBdd(user);
				}
			} else {
				majBdd(user);
			}
			if (isNew) {
				if (user.getStatusesCount() != 0) {
					TwitterService.getInstance().createFriendship(user.getId());
				}
			} else {
				if (user.getStatusesCount() == 0) {
					TwitterService.getInstance()
							.destroyFriendship(user.getId());
				}
			}

		}
		return friendIds;
	}

	private static boolean isFriend(List<Long> userFriendIds, Long id) {
		if (userFriendIds.contains(id)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isfollower(List<Long> followersIds, Long id) {
		if (followersIds.contains(id)) {
			return true;
		} else {
			return false;
		}
	}

	private static void majBdd(UserBean user) {
		// TODO
	}

	private static boolean getScoreOk(String description) {
		return ScoreService.isScoreOk(description);
	}
}
