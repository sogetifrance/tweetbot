package org.sogeti.service.rest;

import java.util.concurrent.ThreadFactory;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.ThreadManager;

@Api(
		name = "manageUsers",
		description = "permet de controler le traitement de reactualisation des friends"
		)
public class ManageUsersService {
	
	private boolean mustContinue=false;

	@ApiMethod(
	        path = "start",
	        httpMethod = HttpMethod.GET
	)
	public RestServiceResponse startManagement(){
		
		this.mustContinue=true;
		Runnable manage = new Runnable(){
		    public void run() {
		    	manageFriends();
		    }
		};

		ThreadFactory threadFactory = ThreadManager.backgroundThreadFactory();
		Thread thread = threadFactory.newThread(manage);
		thread.start();
		return new RestServiceResponse("startManagement", this.mustContinue);
	}
	
	@ApiMethod(
	        path = "stop",
	        httpMethod = HttpMethod.GET
	)
	public RestServiceResponse stopManagement(){
		this.mustContinue=false;
		return new RestServiceResponse("stopManagement", this.mustContinue);
	}
	
	@ApiMethod(
	        path = "isRunning",
	        httpMethod = HttpMethod.GET
	)
	public RestServiceResponse isRunning(){
		return new RestServiceResponse("isRunning", this.mustContinue);
	}
	
	private void manageFriends(){
		int count=1;
		Thread thread = Thread.currentThread();
		
		synchronized(thread) {
			while(this.mustContinue) {
				System.out.println(count++);
				try {
					Thread.currentThread().wait(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
