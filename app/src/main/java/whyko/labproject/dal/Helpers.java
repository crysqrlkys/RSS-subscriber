package whyko.labproject.dal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Helpers {
    @PrimaryKey
    public int id;
    public Long lastUser;

    public Helpers(){
        this.id = 0;
    }

    public static Helpers getDefault(){return new Helpers();}
}