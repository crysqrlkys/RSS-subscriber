package whyko.labproject.dal;

import android.app.Application;

import java.util.List;

import io.reactivex.Maybe;

public class WhyDataBaseAccessor {
    private ProfileDao profileDao;

    public WhyDataBaseAccessor(Application application) {
        WhyDataBase db = WhyDataBase.getDatabase(application);
        profileDao = db.profileDao();
    }

    public Maybe<Profile> getById(long id){
        return profileDao.getById(id);
    }

    public Maybe<List<Profile>> getAll(){
        return profileDao.getAll();
    }

    public void update(Profile profile){
        profileDao.update(profile);
    }
}
