package whyko.labproject.dal;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile implements Serializable {
    @PrimaryKey
    public long id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String userEmail;
    public String imageUrl;

    public Profile(String firstName, String lastName, String phoneNumber, String userEmail, Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.id = id;
    }
    public static Profile getDefault(){
        return new Profile("","","","",(long)0);
    }
}