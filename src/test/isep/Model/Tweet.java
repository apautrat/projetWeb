package test.isep.Model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tweet 
{
	private long tweetId;
	private long authorId;
	private String message;
	private Date date;
	
	public long getTweetId()
	{
		return tweetId;
	}
	
	public long getAuthorId()
	{
		return authorId;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setTweetId(long tweetId)
	{
		this.tweetId = tweetId;
	}
	
	public void setAuthorId(long authorId)
	{
		this.authorId = authorId;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
}
