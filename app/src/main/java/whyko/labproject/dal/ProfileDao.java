package whyko.labproject.dal;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Maybe;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM profile WHERE id = :id")
    Maybe<Profile> getById(long id);

    @Query("SELECT * FROM profile")
    Maybe<List<Profile>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Profile profile);

    @Update
    void update(Profile profile);
}