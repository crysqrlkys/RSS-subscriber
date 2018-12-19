package whyko.labproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import whyko.labproject.dal.Profile;
import whyko.labproject.view_models.ProfileViewModel;

import com.example.whyko.labproject.R;
import com.github.abdularis.civ.CircleImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ProfileFragment extends Fragment {
    private NavController navController;
    private ProfileViewModel profile;
    private TextView first_name, last_name, phone, email;
    private CircleImageView avatar;
    private Profile profileData;


    public ProfileFragment() {
    }

    private void setProfileData(Profile profileData){
        first_name.setText(profileData.firstName);
        last_name.setText(profileData.lastName);
        phone.setText(profileData.phoneNumber);
        email.setText(profileData.userEmail);

        if (profileData.imageUrl != null && profileData.imageUrl.length() != 0) {
            avatar.setImageBitmap(BitmapLoader.decodeSampledBitmapFromFile(profileData.imageUrl,500,500));
        } else {
            avatar.setImageResource(R.drawable.default_avatar);;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FloatingActionButton editButton = view.findViewById(R.id.edit_profile_btn);
        navController = Navigation.findNavController(getActivity(), R.id.main_content);
        editButton.setOnClickListener(v -> navController.navigate(R.id.editProfile));

        first_name = view.findViewById(R.id.first_name);
        last_name = view.findViewById(R.id.last_name);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        avatar = view.findViewById(R.id.profile_avatar);

        profile = ViewModelProviders.of(this).get(ProfileViewModel.class);
        if(savedInstanceState == null) {
            profile.getAll()
                    .subscribe(profiles -> {
                        if (profiles.size() > 0) {
                            profileData = profiles.get(0);
                            setProfileData(profileData);

                        }
                    });
        }
        else if (savedInstanceState.getSerializable("user_data") != null){
            profileData = (Profile) savedInstanceState.getSerializable("user_data");
            setProfileData(profileData);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("user_data", profileData);
    }


}
