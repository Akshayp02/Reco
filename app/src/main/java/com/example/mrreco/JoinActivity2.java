package com.example.mrreco;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

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

public class JoinActivity2 extends AppCompatActivity {
    private AppCompatEditText Useremail, Name, pass, confirmpass;
    private AppCompatButton btnjoin, btnjoinWithGoogle;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);
        // id find out
        Useremail = findViewById(R.id.registeremail);
        Name = findViewById(R.id.registername);
        pass = findViewById(R.id.registerpass);
        confirmpass = findViewById(R.id.registerconfirmpass);
        btnjoin = findViewById(R.id.btnjoin);
        btnjoinWithGoogle = findViewById(R.id.btnjoinwithgoogle);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Set up the AuthStateListener
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is already authenticated, redirect to the main activity
                    startActivity(new Intent(JoinActivity2.this, MainActivity.class));
                    finish();
                }
            }
        };

        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code for join
                String useremail = Useremail.getText().toString();
                String username = Name.getText().toString();
                String password = pass.getText().toString();
                String confirmPass = confirmpass.getText().toString();

                if (isEmpty(useremail) && isEmpty(username) && isEmpty(password) && isEmpty(confirmPass)) {
                    Toast.makeText(JoinActivity2.this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                } else if (matchPass(password, confirmPass)) {
                    // code for create user
                    auth.createUserWithEmailAndPassword(useremail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                // Save additional user data to the database
                                saveUserData(user.getUid(), useremail, username);
                                Toast.makeText(JoinActivity2.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(JoinActivity2.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(JoinActivity2.this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(JoinActivity2.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnjoinWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code for join with google

                // in case already have an account
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start listening for Firebase Authentication state changes
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop listening for Firebase Authentication state changes
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    // functions
    public boolean isEmpty(String txt) {
        return txt.trim().isEmpty();
    }

    public boolean matchPass(String pass, String confirmPass) {
        return pass.trim().equals(confirmPass.trim());
    }

// Function to save user data to the database
    private void saveUserData(String userId, String email, String username) {
        // Get a reference to the Firebase database and save the user data
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.child("email").setValue(email);
        userRef.child("username").setValue(username);
    }
}
