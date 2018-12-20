package whyko.labproject.dal;

import android.app.Application;

import java.util.List;

import de.rtner.security.auth.spi.SimplePBKDF2;
import io.reactivex.Maybe;

public class WhyDataBaseAccessor {
    private ProfileDao profileDao;
    private HelpersDao helpersDao;

    public WhyDataBaseAccessor(Application application) {
        WhyDataBase db = WhyDataBase.getDatabase(application);
        profileDao = db.profileDao();
        helpersDao = db.helpersDao();
    }

    public Maybe<Profile> getById(long id){
        return Maybe.fromCallable(()->profileDao.getById(id));
    }

    public Maybe<List<Profile>> getAll(){
        return Maybe.fromCallable(()->profileDao.getAll());
    }

    public Profile getByIdNoRx(long id){return profileDao.getById(id);}

    public boolean isHelpersEmpty(){return  helpersDao.checkInitialization().size() == 0;}

    public Profile getByAuthNoRx(String email, String password){
        String passwordHash = new SimplePBKDF2().deriveKeyFormatted(password);
        List<Profile> profiles = profileDao.getByEmail(email);
        for (Profile item:profiles) {
            Boolean exist = new SimplePBKDF2().verifyKeyFormatted(item.password, password);
            if(exist)
                return item;
        }
        return null;
    }

    public Helpers getHelpers(){return helpersDao.getInfo();}

    public void updateHelpers(Helpers stats){ helpersDao.update(stats);}

    public Long insert(Profile profile) {
        return profileDao.insert(profile);
    }

    public void delete(Profile profile) {
        profileDao.delete(profile);
    }

    public void update(Profile profile){
        profileDao.update(profile);
    }
}