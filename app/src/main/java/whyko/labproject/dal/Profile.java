package whyko.labproject.dal;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import de.rtner.security.auth.spi.SimplePBKDF2;
import io.reactivex.Scheduler;
import io.reactivex.Single;

@Entity
public class Profile implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String userEmail;
    public String imageUrl;
    public String password;

    public Profile(String firstName, String lastName, String phoneNumber, String userEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.id = 0;
    }

    public static Profile createWithPassword(String userEmail, String password){
        Profile newProfile = new Profile("","","", userEmail);
        newProfile.password = new SimplePBKDF2().deriveKeyFormatted(password);
        return newProfile;
    }

    public static Profile getDefault(){
        return new Profile("","","","");
    }

}