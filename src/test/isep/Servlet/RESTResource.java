package test.isep.Servlet;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import test.isep.Model.DBHelper;
import test.isep.Model.Tweet;
import test.isep.Model.User;

@Path( "/res" )
public class RESTResource
{
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/users" )
    public List<User> getUsers()
    {
        return DBHelper.getUsers();
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/tweets" )
    public List<Tweet> getTweets()
    {
        return DBHelper.getTweets();
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/tweetsid/{userId}" )
    public List<Tweet> getTweetsByUserId( @PathParam( "userId" ) long userId )
    {
        return DBHelper.getTweets( userId );
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/tweetsnickname/{nickname}" )
    public List<Tweet> getTweetsByUserNickname( @PathParam( "nickname" ) String nickname )
    {
        return DBHelper.getTweets( nickname );
    }

    @GET
    @Path( "/updatedata" )
    public void updateData()
    {
        DBHelper.updateData();
    }

}