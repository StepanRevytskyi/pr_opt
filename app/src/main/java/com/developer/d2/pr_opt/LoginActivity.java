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

    private EditText mUserEmailEditText;
    private EditText mUserPasswordEditText;
    private Button mSignInButton;
    private TextView mForgotPasswordTextView;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mUserEmailEditText = findViewById(R.id.user_email_edit_text);
        mUserPasswordEditText = findViewById(R.id.user_password_edit_text);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyTextFields(mUserEmailEditText, mUserPasswordEditText)) {
                    showProgressDialog();

                    String userEmail = mUserEmailEditText.getText().toString();
                    String userPassword = mUserPasswordEditText.getText().toString();

                    mFirebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.auth_failed, Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    mProgressDialog.dismiss();
                                }
                            });
                }
            }
        });

        mForgotPasswordTextView = findViewById(R.id.forgot_password_text_view);
        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RestorePasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setTitle(R.string.sign_in_text);
        mProgressDialog.setMessage(getResources().getString(R.string.wait_text));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
    }

    private boolean isEmptyTextFields(EditText userEmailEditText, EditText userPasswordEditText) {
        if (TextUtils.isEmpty(userEmailEditText.getText().toString())
                || TextUtils.isEmpty(userPasswordEditText.getText().toString())) {

            Toast.makeText(getApplicationContext(), R.string.incorrect_user_information,
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
