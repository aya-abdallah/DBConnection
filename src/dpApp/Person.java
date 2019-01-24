
package dpApp;

/**
 *
 * @author lENOVO
 */
public class Person {
   
     int id;
     String fName;
     String mName;
     String lName;
     String email;
     String phone;
    
    
    public Person(){
        
    }
    public Person (int id,String fName, String mName, String lName, String email, String phone){
        this.id=id;
        this.fName= fName;
        this.mName= mName;
        this.lName= lName;
        this.email= email;
        this.phone=phone;
    }
    
    public String getFName (){
        return fName;
    }
    
     public String getMName (){
        return mName;
    }
     
      public String getLName (){
        return lName;
    }
      public String getEmail (){
        return email;
    }
      public String getPhone(){
          return phone;
      }
    
    
}
