package test.isep.Model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User
{

    private long   id;
    private String name;
    private String twitterNickname;
    private Date   joinedDate;

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getTwitterNickname()
    {
        return twitterNickname;
    }

    public Date getJoinedDate()
    {
        return joinedDate;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setTwitterNickname( String twitterNickname )
    {
        this.twitterNickname = twitterNickname;
    }

    public void setJoinedDate( Date joinedDate )
    {
        this.joinedDate = joinedDate;
    }
}
