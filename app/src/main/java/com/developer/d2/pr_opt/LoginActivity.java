package com.developer.d2.pr_opt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmailEditText;
    private EditText userPasswordEditText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        userEmailEditText = findViewById(R.id.user_email_edit_text);
        userPasswordEditText = findViewById(R.id.user_password_edit_text);

        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyTextFields(userEmailEditText, userPasswordEditText)) {
                    showProgressDialog();

                    String userEmail = userEmailEditText.getText().toString();
                    String userPassword = userPasswordEditText.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
            }
        });

        TextView forgotPasswordTextView = findViewById(R.id.forgot_password_text_view);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RestorePasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle(R.string.sign_in_text);
        progressDialog.setMessage(getResources().getString(R.string.wait_text));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    private boolean isEmptyTextFields(EditText userEmailEditText, EditText userPasswordEditText) {
        if (TextUtils.isEmpty(userEmailEditText.getText().toString())
                || TextUtils.isEmpty(userPasswordEditText.getText().toString())) {

            Toast.makeText(LoginActivity.this, R.string.incorrect_user_information,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
