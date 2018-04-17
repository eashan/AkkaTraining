package com.citi.cpb.akkatraining.day2;

 
import com.citi.cpb.akkatraining.day2.App.Result;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorkerActor2 extends AbstractActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private int state = 0;

	@Override
	public void preStart() {
		log.info("Starting WorkerActor2 instance hashcode # {}",
				this.hashCode());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, (msg)->{
					throw new NullPointerException("Null Value Passed");
				})
				.match(Integer.class, (value)->{
					if (value <= 0) {
						throw new ArithmeticException("Number equal or less than zero");
					} else
						state = value;
				}).match(Result.class, (tmp)->{
					getSender().tell(state,getSelf());
				})
				.matchAny((any)->{
					throw new IllegalArgumentException("Wrong Argument");

				})
				.build();
	}
	

	@Override
	public void postStop() {
		log.info("Stopping WorkerActor2 instance hashcode # {}",
				this.hashCode());

	}

	
}
