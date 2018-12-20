package whyko.labproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import whyko.labproject.dal.Profile;
import whyko.labproject.view_models.ProfileViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.whyko.labproject.R;
import com.github.abdularis.civ.CircleImageView;


public class EditProfile extends Fragment {
    private NavController navController;
    private ProfileViewModel profile;
    private EditText first_name, last_name, phone, email;
    private Button save_edit;
    private Profile profileData;
    private CircleImageView avatar;

    public EditProfile() {
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

    private void performSave(){
        profileData.firstName = first_name.getText().toString();
        profileData.lastName = last_name.getText().toString();
        profileData.phoneNumber = phone.getText().toString();
        profileData.userEmail = email.getText().toString();
        profile.update(profileData).subscribe(() -> {
            navController.navigate(R.id.profile);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.main_content);
        first_name = view.findViewById(R.id.first_name_input);
        last_name = view.findViewById(R.id.last_name_input);
        phone = view.findViewById(R.id.phone_input);
        email = view.findViewById(R.id.email_input);
        save_edit = view.findViewById(R.id.save_edit_btn);
        avatar = view.findViewById(R.id.edit_image_view);
        save_edit.setOnClickListener(v -> performSave());
        final ImagePicker imagePicker = ImagePicker.create(this).single();
        avatar.setOnClickListener(v -> imagePicker.start());
        profile = ViewModelProviders.of(this).get(ProfileViewModel.class);
        if(savedInstanceState == null){
            profile.getLastLoggedIn().subscribe(profile -> {
                profileData = profile;
                setProfileData(profileData);
            });
        }
        else if(savedInstanceState.getSerializable("user_data") != null){
            profileData = (Profile) savedInstanceState.getSerializable("user_data");
            Glide.with(this).load(BitmapFactory.decodeFile(profileData.imageUrl, null)).into(avatar);
        }
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            profileData.imageUrl = image.getPath();
            Bitmap btmp = BitmapFactory.decodeFile(profileData.imageUrl, null);
            Glide.with(this).load(btmp).into(avatar);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("user_data", profileData);
    }

}
