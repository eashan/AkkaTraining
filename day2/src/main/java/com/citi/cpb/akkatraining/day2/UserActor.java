package com.citi.cpb.akkatraining.day2;

import akka.actor.AbstractActor;

public class UserActor extends AbstractActor{

	public static class UserRecord{
		public String userName;
		public String userId;
		public UserRecord(String userName, String userId) {
			super();
			this.userName = userName;
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		
	}
	
	
	@Override
	public Receive createReceive(){
		return receiveBuilder().match(UserRecord.class, (userRec)-> {
//			throw new NullPointerException("Null Value Passed");
			getSender().tell("Saved the user "+userRec.getUserName(), getSelf());
		}).build();
	}

}
