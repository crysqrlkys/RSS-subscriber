package whyko.labproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import whyko.labproject.view_models.ProfileViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.whyko.labproject.R;


public class AuthFragment extends Fragment {
    private EditText email, password;
    private ProfileViewModel profileViewModel;
    private AlertDialog dialog;
    private Button register_btn, login_btn;
    private TextView about;
    private NavController navController;


    public AuthFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_auth, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.auth_content);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        register_btn = view.findViewById(R.id.btn_register);
        login_btn = view.findViewById(R.id.btn_login);
        email = view.findViewById(R.id.auth_email);
        about = view.findViewById(R.id.auth_about);
        about.setOnClickListener(v -> {
            navController.navigate(R.id.authAbout);
        });
        password = view.findViewById(R.id.auth_password);
        register_btn.setOnClickListener(v -> register(email.getText().toString(),password.getText().toString()));
        login_btn.setOnClickListener(v -> logIn(email.getText().toString(),password.getText().toString()));
        dialog = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.loading_title)).setMessage(getString(R.string.loading_message)).setCancelable(false).create();
        tryToEnter();
        return view;
    }

    private void register(String email_val, String password_val){
        if(isInputCorrect(email_val,password_val)){
            dialog.show();
            profileViewModel.register(email_val, password_val).subscribe((ok)-> {
                if(ok)
                    moveToMain();
                dialog.dismiss();
            });
        }
    }

    private Boolean isInputCorrect(String email_val, String password_val){
        return (email_val != null || !email_val.isEmpty()) && (password_val != null || !password_val.isEmpty());
    }

    private void logIn(String email_val, String password_val){
        if(isInputCorrect(email_val,password_val)){
            dialog.show();
            profileViewModel.logIn(email_val, password_val).subscribe((isOk)->{
                if(isOk)
                    moveToMain();
                dialog.dismiss();
            });
        }
    }

    private void moveToMain(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void tryToEnter(){
        profileViewModel.getLastUserId().subscribe(
                id -> {
                    if(id != -1)
                        moveToMain();
                });
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }
}
