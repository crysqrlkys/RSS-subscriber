package whyko.labproject.dal;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Maybe;

@Dao
public interface HelpersDao {
    @Query("SELECT * FROM helpers WHERE id=0")
    Helpers getInfo();

    @Query("SELECT * FROM helpers")
    List<Helpers> checkInitialization();

    @Update
    void update(Helpers helpers);

    @Insert
    void insert(Helpers helpers);
}