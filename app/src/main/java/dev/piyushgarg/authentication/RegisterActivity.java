package dev.piyushgarg.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
    }

    public void signUpUser(View view){
        final View context = view;
        final EditText fullNameTextField = findViewById(R.id.fullNameTextField);
        final EditText emailTetField = findViewById(R.id.emailTextField);
        final EditText passwordTextField = findViewById(R.id.passwordTextField);

        auth.createUserWithEmailAndPassword(emailTetField.getText().toString(),passwordTextField.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                            TODO: add Username to his profile
                            user = auth.getCurrentUser();
                            UserProfileChangeRequest updatedProfile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fullNameTextField.getText().toString())
                                    .build();
                            user.updateProfile(updatedProfile)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Snackbar.make(context,"Login Successful",Snackbar.LENGTH_SHORT).show();
                                                emailTetField.setText("");
                                                fullNameTextField.setText("");
                                                passwordTextField.setText("");
                                            }else{
                                                Snackbar.make(context,task.getException().toString(),Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else{
//                            Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                            Snackbar.make(context,task.getException().toString(),Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });


    }


}
