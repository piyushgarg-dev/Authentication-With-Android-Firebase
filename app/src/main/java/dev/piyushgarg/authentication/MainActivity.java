package dev.piyushgarg.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth user = FirebaseAuth.getInstance();
    FirebaseUser loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");

    }
    public void signInUser(View v){
        final EditText emailTextField = findViewById(R.id.emailTextField);
        final EditText passwordTextField = findViewById(R.id.passwordTextField);
        try {
            user.signInWithEmailAndPassword(emailTextField.getText().toString(),passwordTextField.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()){
                             loggedInUser = user.getCurrentUser();
                             emailTextField.setText("");
                             passwordTextField.setText("");
                          }else{
                              System.out.println(task.getException());
                              Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void redirectUserToRegisterPage(View v){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

}
