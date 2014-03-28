package org.sogeti.service.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import org.sogeti.bo.UserBean;
import org.sogeti.service.TwitterService;

import twitter4j.TwitterException;
import twitter4j.User;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.ThreadManager;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Api(name = "tweetSenderService", description = "permet de controler le traitement de reactualisation des friends")
public class TweetSenderService {

	private boolean serviceRunning;
	private int nbFollowersTotal;
	private int nbSent;

	@ApiMethod(path = "send", httpMethod = HttpMethod.POST)
	public RestServiceResponse sendMessage(@Named("message") String message) {
		if (!serviceRunning) {
			final String messageToSend = message;
			nbFollowersTotal=0;
			nbSent=0;
			this.serviceRunning = true;
			try {
				Runnable send = new Runnable() {
					public void run() {
						sendDirecMessage(messageToSend);
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
			return new RestServiceResponse("sendMessage", String.valueOf(this.serviceRunning),
					new ArrayList<String>());
		}
		return new RestServiceResponse("sendMessage", String.valueOf(this.serviceRunning),
				new ArrayList<String>());
	}

	private void sendDirecMessage(String message) {

		List<Long> listIds = TwitterService.getInstance().getFollowersIDList(
				TwitterService.APP_ACCOUNT_SCREENNAME);
		this.nbFollowersTotal = listIds.size();
		for (Long id : listIds) {
			// try {
			try {
				Thread.currentThread().sleep(500);
				this.nbSent++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TwitterService.getInstance().getTwitter()
			// .sendDirectMessage(id, message);
			// } catch (TwitterException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
		this.serviceRunning = false;
	}

	@ApiMethod(path = "isRunning", httpMethod = HttpMethod.GET)
	public RestServiceResponse isRunning() {
		List<String> result = new ArrayList<String>();
		result.add(String.valueOf(this.nbSent));
		result.add(String.valueOf(this.nbFollowersTotal));
		RestServiceResponse rsr = new RestServiceResponse("sendMessage", String.valueOf(this.serviceRunning),
				result);
		return rsr;
	}
}
