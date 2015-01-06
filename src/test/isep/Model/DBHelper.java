package test.isep.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DBHelper
{   
    static Logger log = Logger.getLogger(DBHelper.class);

    public static List<User> getUsers()
    {
        List<User> users = new ArrayList<User>();

        Statement stmt = null;
        ResultSet rset = null;

        try 
        {            
            DatabaseConnection.getInstance().setAutoCommit(false);
        	
            // Execute query and retrieve results
            stmt = (Statement) DatabaseConnection.getInstance().createStatement();
            rset = stmt.executeQuery( "SELECT * FROM user" );

            // Analyse results
            while ( rset.next() )
            {
            	User newUser = createUser(
            			rset.getLong( "id" ),
            			rset.getString( "name" ), 
            			rset.getString( "twitterNickname" ), 
            			rset.getDate( "joinedDate" ));
            	
                users.add( newUser );
                log.info("User found: " + newUser.getName());;
            }
        } 
        catch ( SQLException e ) 
        {
        	log.warn("Prepared request has failed, see why below : ");
            e.printStackTrace();
        } 
        /*finally 
        {
            try 
            {
                if ( DatabaseConnection.getInstance() != null ) 
                {
                    DatabaseConnection.getInstance().close();
                }
            } catch ( SQLException e ) 
            {
                e.printStackTrace();
            }
        }*/
        log.info( "Found a total of " + users.size() + " user(s)." );
        return users;    
    }

    public static List<Tweet> getTweets()
    {
        List<Tweet> tweets = new ArrayList<Tweet>();

        Statement stmt = null;
        ResultSet rset = null;

        try 
        {            
            DatabaseConnection.getInstance().setAutoCommit(false);
        	
            // Execute query and retrieve results
            stmt = (Statement) DatabaseConnection.getInstance().createStatement();
            rset = stmt.executeQuery( "SELECT * FROM tweet" );

            // Analyse results
            while ( rset.next() )
            {
                Tweet newTweet = createTweet(
                		rset.getLong( "tweetId" ),
                		rset.getLong( "authorId" ),
                		rset.getString( "message" ),
                		rset.getDate( "date" ));
            	
                tweets.add( newTweet );
                log.info("Tweet found: " + newTweet.getMessage());;
            }
        } 
        catch ( SQLException e ) 
        {
        	log.warn("Prepared request has failed, see why below : ");
            e.printStackTrace();
        } 
        /*finally 
        {
            try 
            {
                if ( DatabaseConnection.getInstance() != null ) 
                {
                    DatabaseConnection.getInstance().close();
                }
            } catch ( SQLException e ) 
            {
                e.printStackTrace();
            }
        }*/
        log.info( "Found a total of " + tweets.size() + " tweet(s)." );
        return tweets;    
    }

    
    public static List<Tweet> getTweets( long userId )
    {
        List<Tweet> tweets = new ArrayList<Tweet>();

        Statement stmt = null;
        ResultSet rset = null;

        try 
        {            
            DatabaseConnection.getInstance().setAutoCommit(false);
        	
            // Execute query and retrieve results
            stmt = (Statement) DatabaseConnection.getInstance().createStatement();
            rset = stmt.executeQuery( "SELECT * FROM tweet WHERE authorId = " + userId );

            // Analyse results
            while ( rset.next() )
            {
                Tweet newTweet = createTweet(
                		rset.getLong( "tweetId" ),
                		rset.getLong( "authorId" ),
                		rset.getString( "message" ),
                		rset.getDate( "date" ));

                tweets.add( newTweet );
                log.info( "Tweet found: " + newTweet.getTweetId() );
            }
        } 
        catch ( SQLException e ) 
        {
        	log.warn( "Prepared request has failed, see why below : " );
            e.printStackTrace();
        } 
        /*finally 
        {
            try 
            {
                if ( DatabaseConnection.getInstance() != null ) 
                {
                    DatabaseConnection.getInstance().close();
                }
            } catch ( SQLException e ) 
            {
                e.printStackTrace();
            }
        }*/
        log.info( "Found a total of " + tweets.size() + " tweet(s)." );
        return tweets; 
    }

    public static List<Tweet> getTweets( String nickname )
    {
        List<Tweet> tweets = new ArrayList<Tweet>();

        Statement stmt = null;
        ResultSet rset = null;
        
        try 
        {            
            DatabaseConnection.getInstance().setAutoCommit(false);
        	
            // Execute query and retrieve results
            stmt = (Statement) DatabaseConnection.getInstance().createStatement();
            rset = stmt.executeQuery( "SELECT * FROM tweet, user "
                    + "WHERE tweet.authorId = user.Id "
                    + "AND user.twitterNickname = '"
                    + nickname + "'" );

            // Analyse results
            while ( rset.next() )
            {
                Tweet newTweet = createTweet(
                		rset.getLong( "tweetId" ),
                		rset.getLong( "authorId" ),
                		rset.getString( "message" ),
                		rset.getDate( "date" ));

                tweets.add( newTweet );
                log.info( "Tweet found: " + newTweet.getTweetId() );
            }
        } 
        catch ( SQLException e ) 
        {
        	log.warn( "Prepared request has failed, see why below : " );
            e.printStackTrace();
        } 
        /*finally 
        {
            try 
            {
                if ( DatabaseConnection.getInstance() != null ) 
                {
                    DatabaseConnection.getInstance().close();
                }
            } catch ( SQLException e ) 
            {
                e.printStackTrace();
            }
        }*/
        log.info( "Found a total of " + tweets.size() + " tweet(s)." );
        return tweets;
    }

    public static User createUser(long id, String name, String twitterNickname, Date joinedDate)
    {
    	User newUser = new User();
        newUser.setId(id);
        newUser.setName(name);
        newUser.setTwitterNickname(twitterNickname);
        newUser.setJoinedDate(joinedDate);
        return newUser;
    }
    
    public static Tweet createTweet(long tweetId, long authorId, String message, Date date)
    {
        Tweet newTweet = new Tweet();
        newTweet.setTweetId(tweetId);
        newTweet.setAuthorId(authorId);
        newTweet.setMessage(message);
        newTweet.setDate(date);
        return newTweet;
    }
    
    public static void updateData()
    {
    	List<User> users = new ArrayList<User>();
    	List<Tweet> tweets = new ArrayList<Tweet>();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	// Creation des users
    	try 
    	{
			users.add(createUser(15, "strupel", "@trups",  formatter.parse("2014-11-24 16:30:24")));
	    	users.add(createUser(16, "tmoureaux", "@moujito", formatter.parse("2014-11-24 16:31:24")));
	    	users.add(createUser(17, "apautrat", "@paut",  formatter.parse("2014-11-24 16:32:24")));
	        
	    	// Creation des tweets
	    	tweets.add(createTweet(30, 17, "Je suis chef de guerre, moi. Je suis pas là pour secouer des drapeaux et jouer de la trompette !", formatter.parse("2014-11-25 15:06:25")));
	    	tweets.add(createTweet(31, 16, "Elle est où la poulette?", formatter.parse("2014-11-25 15:06:25")));
	    	tweets.add(createTweet(32, 15, "Le Graal, je sais pas où il est mais il va y rester un moment, c'est moi qui vous l'dis !", formatter.parse("2014-11-25 15:06:25"))); 
		} 
    	catch (ParseException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        try 
        {
            DatabaseConnection.getInstance().setAutoCommit(false);
            
            // Creation users DB
            for ( int i = 0; i < users.size(); i++ ) 
            {
            	User currentUser = users.get(i);
            	
                PreparedStatement preparedStatement = (PreparedStatement) DatabaseConnection.getInstance().prepareStatement(
                		"INSERT INTO user(id, name, twitterNickname, joinedDate) VALUES(?, ?, ?, NOW());" );
                
                preparedStatement.setLong(1, currentUser.getId());
                preparedStatement.setString(2, currentUser.getName());
                preparedStatement.setString(3, currentUser.getTwitterNickname());
                
                log.info("Ajout du user: " + currentUser.getName() + ".");

                preparedStatement.executeUpdate();
                DatabaseConnection.getInstance().commit();
            }
            
            // Creation tweets DB
            for ( int i = 0; i < tweets.size(); i++ ) 
            {
            	Tweet currentTweet = tweets.get(i);
            	
                PreparedStatement preparedStatement = (PreparedStatement) DatabaseConnection.getInstance().prepareStatement(
                		"INSERT INTO tweet (tweetId, authorId, message, date) VALUES(?, ?, ?, NOW());" );
                
                preparedStatement.setLong(1, currentTweet.getTweetId());
                preparedStatement.setLong(2, currentTweet.getAuthorId());
                preparedStatement.setString(3, currentTweet.getMessage());
                
                log.info("Ajout du tweet: " + currentTweet.getMessage() + ".");

                preparedStatement.executeUpdate();
                DatabaseConnection.getInstance().commit();
            }

        } 
        catch ( SQLException e ) 
        {
        	log.info( "Prepared request has failed, see why below : " );
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if ( DatabaseConnection.getInstance() != null ) 
                {
                    DatabaseConnection.getInstance().close();
                }
            } catch ( SQLException e ) 
            {
                e.printStackTrace();
            }
        }

    }
}
