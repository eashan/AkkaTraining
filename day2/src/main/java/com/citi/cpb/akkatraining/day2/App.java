package com.citi.cpb.akkatraining.day2;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


import com.citi.cpb.akkatraining.day2.UserActor.UserRecord;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static class Result {
	}

    public static void main( String[] args ) throws Exception
    {
    	ActorSystem system =  ActorSystem.create("Day2-Training");
    	Timeout timeout = new Timeout(Duration.create(5, "seconds"));

    	ActorRef supervisor = system.actorOf( Props.create(SupervisorActor.class),"SuperVisorActor");
    	UserRecord userRec = new UserRecord("EK65978","Eashan Kadam");
    	scala.concurrent.Future<Object> future = Patterns.ask(supervisor,userRec,timeout);
    	String result = (String)Await.result(future, Duration.create(5, "seconds"));
    	System.out.println(result);
    	
    	LoggingAdapter log = Logging.getLogger(system, system);

		Integer originalValue = Integer.valueOf(0);
		
		log.info("Sending value 8, no exceptions should be thrown! ");
		supervisor.tell(Integer.valueOf(-8),ActorRef.noSender());
		
		Integer result1 = (Integer) Await.result(
				Patterns.ask(supervisor, new Result(), 5000),
				Duration.create(5000, TimeUnit.MILLISECONDS));
		
		log.info("Value Received-> {}", result1);
		assert result.equals(Integer.valueOf(8));
//		log.info("Sending value null, NullPointerException should be thrown! Our Supervisor strategy says restart !");
/*		supervisor.tell(null,ActorRef.noSender());
		
		Integer result2 = (Integer) Await.result(
				Patterns.ask(supervisor, new Result(), 5000),
				Duration.create(5000, TimeUnit.MILLISECONDS));*/
    	/*CompletionStage<String> receivedMessage = PatternsCS.ask(supervisorActor,userRec,timeout)
    												.thenApply((msg)->(String)msg);
    	receivedMessage.whenComplete((response,throwable)->{
			System.out.println(response);
		});*/
    												
    }
}
