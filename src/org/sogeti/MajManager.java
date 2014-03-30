package org.sogeti;

import java.util.Map;

import org.sogeti.bo.UserBean;

public class MajManager {

	
	public void maj(Map<String, UserBean> userFriends, Map<String, String> followersIds, Map<String, String> friendIds, boolean isNew, UserBean user){
		if(!isNew && !isFriend(friendIds, user.getId())){
			if(isfollower(followersIds, user.getId())){
				deleteFriend(user.getId());
			}
			else{
				if(!getScoreOk(user.getDescription())){
					deleteFriend(user.getId());
				}
			}
			if(userFriends.containsKey(user.getId())){
				UserBean userBdd = userFriends.get(user.getId());
				//TODO voir si c'est bien ce paramétre a tester si il a dejé était delete
				if(userBdd.getStatusesCount()==0){
					//Todo MAJ Friends
				}
				if(user!=userBdd){
					majBdd(user);
				}
			}
			else{
				majBdd(user);
			}
			if(isNew){
				if(user.getStatusesCount()!=0){
					addFriend(user.getId());
				}
			}
			else{
				if(user.getStatusesCount()==0){
					deleteFriend(user.getId());
				}
			}
			
		}
	}
	
	private boolean isFriend(Map<String, String> userFriendIds, String id){
		if(userFriendIds.containsKey(id)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean isfollower(Map<String, String> followersIds, String id){
		if(followersIds.containsKey(id)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private void deleteFriend(String id){
		//TODO
	}
	
	private void addFriend(String id){
		//TODO
	}
	
	private void majBdd(UserBean user){
		//TODO
	}
	
	private boolean getScoreOk(String description){
		ScoreService scoreService = new ScoreService();
		return scoreService.isScoreOk(description);
	}
}
