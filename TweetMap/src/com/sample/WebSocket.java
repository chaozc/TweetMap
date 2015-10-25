package com.sample;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@ServerEndpoint(value = "/echo")
public class WebSocket {
	private ConfigurationBuilder cb = new ConfigurationBuilder();
    private TwitterStream twitterStream;
	@OnOpen  
    public void open(Session session) {  
       try {
    	   cb.setDebugEnabled(true)
    	    .setOAuthConsumerKey("SXpEpp1jcBK8M4Ta8NpeK1ORV")
    	    .setOAuthConsumerSecret("4YGvbnvBYAH18rTcGb34xWQ9YmIij2XElRenpxIDMQkHAqijvD")
    	    .setOAuthAccessToken("3098163327-TOuw7MmR1u2xcDODfy1B4jPAmzAHZ1HmVoLpQfx")
    	    .setOAuthAccessTokenSecret("M9lh8U65ZE3BkDuvlTfUBkV28OW1rkklZmlgb5rupuorq");
    	   twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    	   StatusListener listener = new StatusListener() {
		        @Override
		        public void onStatus(Status status) {
		     	   try {
		     		   if (status.getGeoLocation() != null){
		     		System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText()+"-");
		     		double lat = status.getGeoLocation().getLatitude();
		     		double lon = status.getGeoLocation().getLongitude();
		     		System.out.println(Double.toString(lat)+","+Double.toString(lon));
					session.getBasicRemote().sendText(Double.toString(lat)+","+Double.toString(lon));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		            //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText()+"-");
		        }

		        @Override
		        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		            //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
		        }

		        @Override
		        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		            //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
		        }

		        @Override
		        public void onScrubGeo(long userId, long upToStatusId) {
		            //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
		        }

		        @Override
		        public void onStallWarning(StallWarning warning) {
		            System.out.println("Got stall warning:" + warning);
		        }

		        @Override
		        public void onException(Exception ex) {
		            ex.printStackTrace();
		        }
		    };
		    twitterStream.addListener(listener);
		    
		    FilterQuery filter = new FilterQuery();
		    String[] keywordsArray = {"ball,soccer,hockey,football,volleyball,badminton,cricket,pingpang,tabletennis,golf,tennis,baseball,rugby,basketball", 
					"running,jogging,run,walk,jog,step",
					"dance,tango,yoga,Hip-hop,Belly,Ballet,jazz,morden,swing,country,dancing",
					"swim,swimming,Canoeing,Kayaking,Skurfing,Sailing,sail,canoe,Rowing,Rafting,Kiteboating,Waterskiing,diving,dive,waterski,fishing,Snorkeling,Waboba,Boating"};
		    filter.track(keywordsArray);
		    twitterStream.filter(filter);
   		System.out.println(session.getId()+"opened");
		session.getBasicRemote().sendText(session.getId()+"opened");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
    }  
    @OnMessage
    public void set_Keywords(Session session, String msg, boolean last) {
        if (session.isOpen()) {
        	FilterQuery filter = new FilterQuery();
        	 String[] kw_any = {"."};
		     String[] kw_ball = {"ball,soccer,hockey,football,volleyball,badminton,cricket,pingpang,tabletennis,golf,tennis,baseball,rugby,basketball"};
		     String[] kw_run = {"running,jogging,run,walk,jog,step"};
		     String[] kw_dance = {"dance,tango,yoga,Hip-hop,Belly,Ballet,jazz,morden,swing,country,dancing"};
		     String[] kw_water = {"swim,swimming,Canoeing,Kayaking,Skurfing,Sailing,sail,canoe,Rowing,Rafting,Kiteboating,Waterskiing,diving,dive,waterski,fishing,Snorkeling,Waboba,Boating"};
		     String[] kw_all = {"ball,soccer,hockey,football,volleyball,badminton,cricket,pingpang,tabletennis,golf,tennis,baseball,rugby,basketball", 
						"running,jogging,run,walk,jog,step",
						"dance,tango,yoga,Hip-hop,Belly,Ballet,jazz,morden,swing,country,dancing",
						"swim,swimming,Canoeing,Kayaking,Skurfing,Sailing,sail,canoe,Rowing,Rafting,Kiteboating,Waterskiing,diving,dive,waterski,fishing,Snorkeling,Waboba,Boating"};
		    switch (msg) {
		    case "ball": 
		    		filter.track(kw_ball);
		    		twitterStream.filter(filter);
		    		break;
		    case "run":
		    		filter.track(kw_run);
		    		twitterStream.filter(filter);
		    		break;
		    case "dance":
		    		filter.track(kw_dance);
		    		twitterStream.filter(filter);
		    		break;
		    case "water":
		    		filter.track(kw_water);
		    		twitterStream.filter(filter);
		    		break;
		    case "all":
		    		filter.track(kw_all);
		    		twitterStream.filter(filter);
		    		break;
		    case "any":
		    	twitterStream.sample();
		    	break;
		    	
		    	
		    }

		    
		}
    }
    
    @OnClose
    public void close(Session session){
    	
    	twitterStream.shutdown();
    }
    


}