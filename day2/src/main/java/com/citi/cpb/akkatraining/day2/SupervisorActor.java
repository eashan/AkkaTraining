package com.citi.cpb.akkatraining.day2;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import com.citi.cpb.akkatraining.day2.App.Result;
import com.citi.cpb.akkatraining.day2.UserActor.UserRecord;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

public class SupervisorActor extends AbstractActor {
	public ActorRef childActor ;
	public ActorRef workerActor1;
	public ActorRef workerActor2;
	
	public SupervisorActor() {
		childActor = getContext().actorOf(Props.create(UserActor.class),"userActor");
		workerActor1 = getContext().actorOf( Props.create(WorkerActor1.class),
				"workerActor1");
		workerActor2 = getContext().actorOf( Props.create(WorkerActor2.class),
				"workerActor2");
	}
	private static SupervisorStrategy strategy =
			  new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.
			    match(ArithmeticException.class, e -> restart()).
			    match(NullPointerException.class, e -> restart()).
			    match(IllegalArgumentException.class, e -> stop()).
			    matchAny(o -> escalate()).build());

			@Override
			public SupervisorStrategy supervisorStrategy() {
			  return strategy;
			}
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(UserRecord.class, (rec)->{
			childActor.tell(rec, getSender());
				})
				.match(Result.class, (tmp)->{
					workerActor1.tell(tmp, getSender());
				})
				.matchAny((msg)->{
					workerActor1.tell(msg, getSender());

				})
				.build();
				}
	}


