package com.stream;

/**
 * Comment
 */
public class Comment {
    private String Id;
    private String UserId;
    private String PostId;
    private String Text;
    private String Type;
    private String CreationDate;
    private String EventId;
  
  
   // Getter Methods 
  
    public String getId() {
      return Id;
    }
  
    public String getUserId() {
      return UserId;
    }
  
    public String getPostId() {
      return PostId;
    }
  
    public String getText() {
      return Text;
    }
  
    public String getType() {
      return Type;
    }
  
    public String getCreationDate() {
      return CreationDate;
    }

    public String getEventId() {
      return EventId;
    }
  
   // Setter Methods 
  
    public void setId( String Id ) {
      this.Id = Id;
    }
  
    public void setUserId( String UserId ) {
      this.UserId = UserId;
    }
  
    public void setPostId( String PostId ) {
      this.PostId = PostId;
    }
  
    public void setText( String Text ) {
      this.Text = Text;
    }
  
    public void setType( String Type ) {
      this.Type = Type;
    }
  
    public void setCreationDate( String CreationDate ) {
      this.CreationDate = CreationDate;
    }

    public void setEventId( String EventId ) {
      this.EventId = EventId;
    }
  }