package com.stream;

/**
 * Vote
 */
public class Vote {
    private String Id;
    private String EventId;
    private String PostId;
    private String VoteTypeId;
    private String Type;
    private String CreatedAt;
  
  
   // Getter Methods 
  
    public String getId() {
      return Id;
    }
  
    public String getEventId() {
      return EventId;
    }
  
    public String getPostId() {
      return PostId;
    }
  
    public String getVoteTypeId() {
      return VoteTypeId;
    }
  
    public String getType() {
      return Type;
    }
  
    public String getCreatedAt() {
      return CreatedAt;
    }
  
   // Setter Methods 
  
    public void setId( String Id ) {
      this.Id = Id;
    }
  
    public void setEventId( String EventId ) {
      this.EventId = EventId;
    }
  
    public void setPostId( String PostId ) {
      this.PostId = PostId;
    }
  
    public void setVoteTypeId( String VoteTypeId ) {
      this.VoteTypeId = VoteTypeId;
    }
  
    public void setType( String Type ) {
      this.Type = Type;
    }
  
    public void setCreatedAt( String CreatedAt ) {
      this.CreatedAt = CreatedAt;
    }
  }