/*
 * Crawl tweets with "Water Sports" related contents
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * <p>
 * This is a code example of Twitter4J Streaming API - sample method support.<br>
 * Usage: java twitter4j.examples.PrintSampleStream<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class GrabWaterSp {
	/**
	 * Main entry of this application.
	 *
	 * @param args
	 */

	static final String JDBC_CONNECTION_URL = "";
	static final String JDBC_USERNAME = "";
	static final String JDBC_PASSWORD = "";

	public static void main(String[] args) throws TwitterException {
		// just fill this
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("")
				.setOAuthConsumerSecret(
						"")
				.setOAuthAccessToken(
						"")
				.setOAuthAccessTokenSecret(
						"");

		try {
			Class.forName("com.mysql.jdbc.Driver"); 

			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}

		Connection connect = null;
		try {
			connect = DriverManager.getConnection(JDBC_CONNECTION_URL,
					JDBC_USERNAME, JDBC_PASSWORD);

			System.out.println("Success connect Mysql server!");

			System.out.println("executed");
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
				.getInstance();

		PreparedStatement pstmt = null;
		String query = "insert into waterSp(id, tweetId, time, latitude, longtitude, keyword, text, favCount) values(null, ?, ?, ?, ?,'dance',?,?)";

		 try {
		 pstmt = connect.prepareStatement(query);
		
		 } catch (SQLException e1) {
		 e1.printStackTrace();
		 }

		final PreparedStatement pstmt2 = pstmt;

		StatusListener listener = new StatusListener() {

			long tweetId;
			String dateCreated = "";
			double latitude;
			double longtitude;
			String text = "";
			int favCount;

			@Override
			public void onStatus(Status status) {

				if (status.getGeoLocation() != null) {

					tweetId = status.getUser().getId();
					dateCreated = status.getCreatedAt().toString();
					latitude = status.getGeoLocation().getLatitude();
					longtitude = status.getGeoLocation().getLongitude();
					text = status.getText();
					favCount = status.getFavoriteCount();

					 try {
					 pstmt2.setLong(1, tweetId);
					 pstmt2.setString(2, dateCreated); // set input parameter
			
					 pstmt2.setDouble(3, latitude);
					 pstmt2.setDouble(4, longtitude);
					 pstmt2.setString(5, text);
					 pstmt2.setInt(6, favCount);
					 pstmt2.executeUpdate(); // execute insert statement
					 } catch (SQLException e) {
					 e.printStackTrace();
					 }


					System.out.println("@" + status.getUser().getScreenName()
							+ " - " + status.getText());

				}

			}

			@Override
			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
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
		FilterQuery fq = new FilterQuery();

		String keywords[] = { 
				"swim,swimming,Canoeing,Kayaking,Skurfing,Sailing,sail,canoe,Rowing,Rafting,Kiteboating,Waterskiing,diving,dive,waterski,fishing,Snorkeling,Waboba,Boating"
				};

		fq.track(keywords);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);
	}
}





