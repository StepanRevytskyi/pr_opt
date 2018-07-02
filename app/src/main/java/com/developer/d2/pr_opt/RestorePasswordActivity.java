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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestorePasswordActivity extends AppCompatActivity {

    private ImageView mArrowBackImageView;
    private EditText mUserEmailEditText;
    private Button mRestoreButton;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mUserEmailEditText = findViewById(R.id.restore_user_email_edit_text);

        mArrowBackImageView = findViewById(R.id.arrow_back_image_view);
        mArrowBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestorePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mRestoreButton = findViewById(R.id.restore_button);
        mRestoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mUserEmailEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), R.string.incorrect_email, Toast.LENGTH_LONG)
                            .show();
                } else {
                    showProgressDialog();

                    String userEmail = mUserEmailEditText.getText().toString();

                    mFirebaseAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), R.string.check_email, Toast.LENGTH_LONG)
                                                .show();

                                        Intent intent = new Intent(RestorePasswordActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.incorrect_email, Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    mProgressDialog.dismiss();
                                }
                            });
                }
            }
        });
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(RestorePasswordActivity.this);
        mProgressDialog.setTitle(R.string.restore_password_button_text);
        mProgressDialog.setMessage(getResources().getString(R.string.wait_text));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RestorePasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
