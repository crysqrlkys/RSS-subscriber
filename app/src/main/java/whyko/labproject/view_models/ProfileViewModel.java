package whyko.labproject.view_models;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import whyko.labproject.dal.Profile;
import whyko.labproject.dal.WhyDataBaseAccessor;


public class ProfileViewModel extends AndroidViewModel {
    private WhyDataBaseAccessor mRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WhyDataBaseAccessor(application);
    }

    public Maybe<Profile> getProfile(long id){
        return mRepository.getById(id);
    }

    public Maybe<List<Profile>> getAll(){
        return mRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable update(Profile profile){
        return Completable.fromAction(()->{
            mRepository.update(profile);
        });
    }
}
