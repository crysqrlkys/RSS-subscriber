package whyko.labproject.dal;

import android.content.Context;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Profile.class}, version =1 )
public abstract class WhyDataBase extends RoomDatabase {
    public abstract ProfileDao profileDao();
    private static volatile WhyDataBase INSTANCE;

    public static WhyDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WhyDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WhyDataBase.class, "bbdatabase")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            getDatabase(context).profileDao().insert(Profile.getDefault());
                                        }
                                    });
                                }
                            }).build();
                }
            }
        }
        return INSTANCE;
    }
}