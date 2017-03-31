package com.example.hitesh1bhutani.studentdiary_nhcps_forteachers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        Firebase.setAndroidContext(this);
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent newActivity = new Intent(MainActivity.this, ClassList.class);
            startActivity(newActivity);
        }

        mAuth = FirebaseAuth.getInstance();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        final String formattedDate = df.format(c.getTime());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getApplicationContext(), "User is signed in "+ formattedDate, Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "User is signed out", Toast.LENGTH_SHORT).show();
                }

            }
        };

        final EditText id = (EditText) findViewById(R.id.et_mainActivity_TeacherId);
        final EditText password = (EditText) findViewById(R.id.et_mainActivity_Password);
        final Button logIn = (Button) findViewById(R.id.b_mainActivity_Login);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try{
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (NullPointerException n){
                    Toast.makeText(getApplicationContext(), "null pointer exception", Toast.LENGTH_SHORT).show();
                }
                checkLogIn(id, password);
            }

            private void checkLogIn(EditText id, EditText password) {
                final String teacherId = id.getText().toString().concat(getResources().getString(R.string.mail));
                final String teacherPassword = password.getText().toString();
                if(teacherId.isEmpty() && teacherPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.bothFieldEmpty, Toast.LENGTH_SHORT).show();
                }else if(teacherId.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.idFieldEmpty, Toast.LENGTH_SHORT).show();
                } else if (teacherPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.passwordFieldEmpty, Toast.LENGTH_SHORT).show();
                } else{
                    mAuth.signInWithEmailAndPassword(teacherId, teacherPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    Intent newActivity = new Intent(MainActivity.this, ClassList.class);
                                    startActivity(newActivity);

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w("is Not Successful", "signInWithEmail:failed", task.getException());
//                                        Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
//                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
