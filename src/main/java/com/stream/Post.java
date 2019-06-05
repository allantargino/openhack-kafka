package com.stream;

public class Post {
    private String Id;
    private String Body;
    private String OwnerUserId;
    private String Type;
    private String CreatedAt;
  
  
   // Getter Methods 
  
    public String getId() {
      return Id;
    }
  
    public String getBody() {
      return Body;
    }
  
    public String getOwnerUserId() {
      return OwnerUserId;
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
  
    public void setBody( String Body ) {
      this.Body = Body;
    }
  
    public void setOwnerUserId( String OwnerUserId ) {
      this.OwnerUserId = OwnerUserId;
    }
  
    public void setType( String Type ) {
      this.Type = Type;
    }
  
    public void setCreatedAt( String CreatedAt ) {
      this.CreatedAt = CreatedAt;
    }
  }