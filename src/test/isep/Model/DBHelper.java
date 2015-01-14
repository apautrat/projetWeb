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
	// Log4J
    static Logger log = Logger.getLogger(DBHelper.class);

    /**
     * @brief Methode permettant de recuperer la liste de tous les Users de la base
     * @return liste de tous les Users
     */
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
        log.info( "Found a total of " + users.size() + " user(s)." );
        return users;    
    }

    /**
     * @brief Methode permettant de recuperer la liste de tous les Tweets de la base
     * @return liste de tous les Tweets
     */
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
        log.info( "Found a total of " + tweets.size() + " tweet(s)." );
        return tweets;    
    }

    /**
     * @brief Methode permettant de recuperer la liste de tous les Tweets d'un User via son id
     * @param userId: long correspondant a l'id du User
     * @return liste de tous les Tweets en fonction de userId
     */
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
        log.info( "Found a total of " + tweets.size() + " tweet(s)." );
        return tweets; 
    }

    
    /**
     * @brief Methode permettant de recuperer la liste de tous les Tweets d'un User via son nickname
     * @param nickname: String correspondant au nickname du user
     * @return liste de tous les Tweets en fonction du nickname
     */
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
        log.info( "Found a total of " + tweets.size() + " tweet(s)." );
        return tweets;
    }

    /**
     * @brief 'Constructeur' User avec tous ses params
     * @param id
     * @param name
     * @param twitterNickname
     * @param joinedDate
     * @return User cree
     */
    public static User createUser(long id, String name, String twitterNickname, Date joinedDate)
    {
    	User newUser = new User();
        newUser.setId(id);
        newUser.setName(name);
        newUser.setTwitterNickname(twitterNickname);
        newUser.setJoinedDate(joinedDate);
        log.info("New User created: " + newUser.getName());
        return newUser;
    }
    
    /**
     * @brief 'Constructeur' Tweet avec tous ses params
     * @param tweetId
     * @param authorId
     * @param message
     * @param date
     * @return Tweet cree
     */
    public static Tweet createTweet(long tweetId, long authorId, String message, Date date)
    {
        Tweet newTweet = new Tweet();
        newTweet.setTweetId(tweetId);
        newTweet.setAuthorId(authorId);
        newTweet.setMessage(message);
        newTweet.setDate(date);
        log.info("New tweet created: " + newTweet.getMessage());
        return newTweet;
    }
    
    /**
     * @brief Methode permettant de mettre a jour la base de donnees avec des donnees statiques
     */
    public static void updateData()
    {
    	List<User> users = new ArrayList<User>();
    	List<Tweet> tweets = new ArrayList<Tweet>();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	try 
    	{
    		// Creation des objets Users
			users.add(createUser(15, "strupel", "@trups",  formatter.parse("2014-11-24 16:30:24")));
	    	users.add(createUser(16, "tmoureaux", "@moujito", formatter.parse("2014-11-24 16:31:24")));
	    	users.add(createUser(17, "apautrat", "@paut",  formatter.parse("2014-11-24 16:32:24")));
	        
	    	// Creation des objets Tweets
	    	tweets.add(createTweet(30, 17, "Je suis chef de guerre, moi. Je suis pas la pour secouer des drapeaux et jouer de la trompette !", formatter.parse("2014-09-05 17:06:25")));
	    	tweets.add(createTweet(31, 16, "Elle est ou la poulette?", formatter.parse("2014-11-29 18:16:25")));
	    	tweets.add(createTweet(32, 16, "Ceci est un tweet, oui, oui!", formatter.parse("2014-11-29 18:16:25")));
	    	tweets.add(createTweet(33, 15, "Le Graal, je sais pas ou il est mais il va y rester un moment, je vous le dis !", formatter.parse("2013-12-13 15:26:25"))); 
		} 
    	catch (ParseException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        try 
        {
            DatabaseConnection.getInstance().setAutoCommit(false);
            
            // Creation Users dans la base de donnees
            // On parcourt notre liste d'objets User et pour chacun => requete SQL creation BD
            for ( int i = 0; i < users.size(); i++ ) 
            {
            	User currentUser = users.get(i);
            	
                PreparedStatement preparedStatement = (PreparedStatement) DatabaseConnection.getInstance().prepareStatement(
                		"INSERT INTO user(id, name, twitterNickname, joinedDate) VALUES(?, ?, ?, NOW());" );
                
                preparedStatement.setLong(1, currentUser.getId());
                preparedStatement.setString(2, currentUser.getName());
                preparedStatement.setString(3, currentUser.getTwitterNickname());
                
                log.info("Ajout du user dans la base: " + currentUser.getName() + ".");

                preparedStatement.executeUpdate();
                DatabaseConnection.getInstance().commit();
            }
            
            // Creation Tweets dans la base de donnees
            // On parcourt notre liste d'objets Tweet et pour chacun => requete SQL creation BD
            for ( int i = 0; i < tweets.size(); i++ ) 
            {
            	Tweet currentTweet = tweets.get(i);
            	
                PreparedStatement preparedStatement = (PreparedStatement) DatabaseConnection.getInstance().prepareStatement(
                		"INSERT INTO tweet (tweetId, authorId, message, date) VALUES(?, ?, ?, NOW());" );
                
                preparedStatement.setLong(1, currentTweet.getTweetId());
                preparedStatement.setLong(2, currentTweet.getAuthorId());
                preparedStatement.setString(3, currentTweet.getMessage());
                
                log.info("Ajout du tweet dans la base: " + currentTweet.getMessage() + ".");

                preparedStatement.executeUpdate();
                DatabaseConnection.getInstance().commit();
            }

        } 
        catch ( SQLException e ) 
        {
        	log.info( "Prepared request has failed, see why below : " );
            e.printStackTrace();
        } 
    }
}
