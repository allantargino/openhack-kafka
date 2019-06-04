package myapps;

              
public class Post
{
    private String CreationDate;

    private String OwnerUserId;

    private String Type;

    private String EventId;

    private String Id;

    private String Body;

    public String getCreationDate ()
    {
        return CreationDate;
    }

    public void setCreationDate (String CreationDate)
    {
        this.CreationDate = CreationDate;
    }

    public String getOwnerUserId ()
    {
        return OwnerUserId;
    }

    public void setOwnerUserId (String OwnerUserId)
    {
        this.OwnerUserId = OwnerUserId;
    }

    public String getType ()
    {
        return Type;
    }

    public void setType (String Type)
    {
        this.Type = Type;
    }

    public String getEventId ()
    {
        return EventId;
    }

    public void setEventId (String EventId)
    {
        this.EventId = EventId;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getBody ()
    {
        return Body;
    }

    public void setBody (String Body)
    {
        this.Body = Body;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CreationDate = "+CreationDate+", OwnerUserId = "+OwnerUserId+", Type = "+Type+", EventId = "+EventId+", Id = "+Id+", Body = "+Body+"]";
    }
}