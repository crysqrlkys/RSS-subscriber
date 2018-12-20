package whyko.labproject.view_models;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import whyko.labproject.dal.Helpers;
import whyko.labproject.dal.Profile;
import whyko.labproject.dal.WhyDataBaseAccessor;

public class ProfileViewModel extends AndroidViewModel {

    private WhyDataBaseAccessor dbAccessor;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        dbAccessor = new WhyDataBaseAccessor(application);
    }

    public Maybe<Long> getLastUserId(){
        return Maybe.fromCallable(()-> {
            if (dbAccessor.getHelpers() == null) {
                return (long) -1;
            }
            return dbAccessor.getHelpers().lastUser;}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<Boolean> logIn(String email, String password){
        return Maybe.fromCallable(()->{
            Profile profile = dbAccessor.getByAuthNoRx(email, password);
            if(profile != null){
                _logInStep(profile);
                return true;
            }
            return false;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<Boolean> register(String email, String password) {
        return Maybe.fromCallable(()->{
            Profile profile1 = dbAccessor.getByAuthNoRx(email, password);
            if(profile1 != null)
                return false;
            Profile newProfile = Profile.createWithPassword(email, password);
            Long id = dbAccessor.insert(newProfile);
            Helpers helpers = dbAccessor.getHelpers();
            helpers.lastUser = id;
            dbAccessor.updateHelpers(helpers);
            return true;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<Profile> getLastLoggedIn(){
        return Maybe.fromCallable(()-> {
            long id = dbAccessor.getHelpers().lastUser;
            Profile profile = dbAccessor.getByIdNoRx(id);
            return profile;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Completable logOut(){
        return Completable.fromAction(() ->{
            _logOutStep();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void _logInStep(Profile profile){
        Helpers helpers = dbAccessor.getHelpers();
        helpers.lastUser = profile.id;
        dbAccessor.updateHelpers(helpers);
    }

    private void _logOutStep(){
        Helpers helpers = dbAccessor.getHelpers();
        helpers.lastUser = (long) -1;
        dbAccessor.updateHelpers(helpers);
    }

    public Maybe<List<Profile>> getAll(){
        return dbAccessor.getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable update(Profile profile){
        return Completable.fromAction(()->{
            dbAccessor.update(profile);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
