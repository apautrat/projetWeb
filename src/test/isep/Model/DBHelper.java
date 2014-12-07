package test.isep.Model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DBHelper
{
    private static String     DBURL      = "jdbc:mysql://localhost/projetweb";
    private static String     DBLOGIN    = "root";
    private static String     DBPASSWORD = "";

    private static Connection conn       = null;

    public static boolean connection()
    {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
            return false;
        }
        try
        {
            // Get a connection to the DB
            conn = (Connection) DriverManager.getConnection( DBURL, DBLOGIN, DBPASSWORD );
            System.out.println( "Connection to the DB is a success!" );
            return true;
        } catch ( Exception e )
        {
            System.err.println( "Connection to the DB has failed! See why below: " );
            e.printStackTrace();
            return false;
        }
    }

    public static List<User> getUsers()
    {
        List<User> users = new ArrayList<User>();

        Statement stmt = null;
        ResultSet rset = null;

        if ( connection() )
        {
            try
            {
                // Execute query and retrieve results
                stmt = (Statement) conn.createStatement();
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
                    System.out.println( "User found: " + newUser.getName() );
                }
            } catch ( SQLException e )
            {
                e.printStackTrace();
            } finally
            {
                try {
                    if ( rset != null )
                        rset.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                try {
                    if ( stmt != null )
                        rset.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }

            System.out.println( "Found a total of " + users.size() + " user(s)." );
            return users;
        }
        else
            return null;
    }

    public static List<Tweet> getTweets( long userId )
    {
        List<Tweet> tweets = new ArrayList<Tweet>();

        Statement stmt = null;
        ResultSet rset = null;

        if ( connection() )
        {
            try
            {
                // Execute query and retrieve results
                stmt = (Statement) conn.createStatement();
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
                    System.out.println( "Tweet found: " + newTweet.getTweetId() );
                }
            } catch ( SQLException e )
            {
                e.printStackTrace();
            } finally
            {
                try {
                    if ( rset != null )
                        rset.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                try {
                    if ( stmt != null )
                        rset.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }

            System.out.println( "Found a total of " + tweets.size() + " tweet(s)." );
            return tweets;
        }
        else
            return null;
    }

    public static List<Tweet> getTweets( String nickname )
    {
        List<Tweet> tweets = new ArrayList<Tweet>();

        Statement stmt = null;
        ResultSet rset = null;

        if ( connection() )
        {
            try
            {
                // Execute query and retrieve results
                stmt = (Statement) conn.createStatement();
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
                    System.out.println( "Tweet found: " + newTweet.getTweetId() );
                }
            } catch ( SQLException e )
            {
                e.printStackTrace();
            } finally
            {
                try {
                    if ( rset != null )
                        rset.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                try {
                    if ( stmt != null )
                        rset.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }

            System.out.println( "Found a total of " + tweets.size() + " tweet(s)." );
            return tweets;
        }
        else
            return null;
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
                
                System.out.println("Ajout du user: " + currentUser.getName() + ".");

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
                
                System.out.println("Ajout du tweet: " + currentTweet.getMessage() + ".");

                preparedStatement.executeUpdate();
                DatabaseConnection.getInstance().commit();
            }

        } 
        catch ( SQLException e ) 
        {
            System.out.println( "Prepared request has failed, see why below : " );
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

    /*public static void updateTableUser( String[][] tab ) {
        try {

            DatabaseConnection.getInstance().setAutoCommit( false );
            for ( int i = 0; i < 3; i++ ) {// rendre dynamique le 3
                PreparedStatement prepraredStatement = (PreparedStatement) DatabaseConnection.getInstance().prepareStatement( "INSERT INTO user (name,twitterNickname,joinedDate) VALUES(?,?,?);" );
                System.out.println( "requete : " + i );

                for ( int j = 0; j < 3; j++ ) {
                    System.out.println( "parametre : " + j );
                    prepraredStatement.setString( j + 1, tab[i][j] );
                }
                prepraredStatement.executeUpdate();
                DatabaseConnection.getInstance().commit();
            }

        } catch ( SQLException e ) {
            System.out.println( "La requete preparee a foire : " );
            e.printStackTrace();
        } finally {
            try {
                if ( DatabaseConnection.getInstance() != null ) {
                    DatabaseConnection.getInstance().close();
                }
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }
    }*/
}
