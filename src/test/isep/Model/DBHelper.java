package test.isep.Model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBHelper 
{
	private static String DBURL = "jdbc:mysql://localhost/projetweb";
	private static String DBLOGIN = "root";
	private static String DBPASSWORD = "";
	
	private static Connection conn = null;
	
	public static boolean connection()
	{			
		try
		{
			// Get a connection to the DB
			conn = (Connection) DriverManager.getConnection(DBURL, DBLOGIN, DBPASSWORD);
			System.out.println("Connection to the DB is a success!");
			return true;
		}
		catch(Exception e)
		{
			System.err.println("Connection to the DB has failed! See why below: ");
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<User> getUsers()
	{
		List<User> users = new ArrayList<User>();
		
		Statement stmt = null;
		ResultSet rset = null;
		
		if(connection())
		{
			try
			{
				// Execute query and retrieve results
				stmt = (Statement) conn.createStatement();
				rset = stmt.executeQuery("SELECT * FROM user");
				
				// Analyse results
				while(rset.next())
				{
					User newUser = new User();
					newUser.setId(rset.getLong("id"));
					newUser.setName(rset.getString("name"));
					newUser.setTwitterNickname(rset.getString("twitterNickname"));
					newUser.setJoinedDate(rset.getDate("joinedDate"));

					users.add(newUser);
					System.out.println("User found: " + newUser.getName());
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try { if(rset != null) rset.close(); } catch(Exception e) { e.printStackTrace(); }
				try { if(stmt != null) rset.close(); } catch(Exception e) { e.printStackTrace(); }
			}
			
			System.out.println("Found a total of " + users.size() + " user(s).");
			return users;
		}
		else
			return null;
	}

	public static List<Tweet> getTweets(long userId)
	{
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		Statement stmt = null;
		ResultSet rset = null;
		
		if(connection())
		{
			try
			{
				// Execute query and retrieve results
				stmt = (Statement) conn.createStatement();
				rset = stmt.executeQuery("SELECT * FROM tweet WHERE authorId = " + userId);
				
				// Analyse results
				while(rset.next())
				{
					Tweet newTweet = new Tweet();
					newTweet.setTweetId(rset.getLong("tweetId"));
					newTweet.setAuthorId(rset.getLong("authorId"));
					newTweet.setMessage(rset.getString("message"));
					newTweet.setDate(rset.getDate("date"));

					tweets.add(newTweet);
					System.out.println("Tweet found: " + newTweet.getTweetId());
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try { if(rset != null) rset.close(); } catch(Exception e) { e.printStackTrace(); }
				try { if(stmt != null) rset.close(); } catch(Exception e) { e.printStackTrace(); }
			}
			
			System.out.println("Found a total of " + tweets.size() + " tweet(s).");
			return tweets;
		}
		else
			return null;
	}

	public static List<Tweet> getTweets(String nickname)
	{
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		Statement stmt = null;
		ResultSet rset = null;
		
		if(connection())
		{
			try
			{
				// Execute query and retrieve results
				stmt = (Statement) conn.createStatement();
				rset = stmt.executeQuery("SELECT * FROM tweet, user "
									   + "WHERE tweet.authorId = user.Id "
									   + "AND user.twitterNickname = '" 
									   + nickname + "'");
				
				// Analyse results
				while(rset.next())
				{
					Tweet newTweet = new Tweet();
					newTweet.setTweetId(rset.getLong("tweetId"));
					newTweet.setAuthorId(rset.getLong("authorId"));
					newTweet.setMessage(rset.getString("message"));
					newTweet.setDate(rset.getDate("date"));

					tweets.add(newTweet);
					System.out.println("Tweet found: " + newTweet.getTweetId());
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try { if(rset != null) rset.close(); } catch(Exception e) { e.printStackTrace(); }
				try { if(stmt != null) rset.close(); } catch(Exception e) { e.printStackTrace(); }
			}
			
			System.out.println("Found a total of " + tweets.size() + " tweet(s).");
			return tweets;
		}
		else
			return null;
	}

}
