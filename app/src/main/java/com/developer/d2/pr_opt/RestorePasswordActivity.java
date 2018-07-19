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

    private EditText userEmailEditText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        firebaseAuth = FirebaseAuth.getInstance();

        userEmailEditText = findViewById(R.id.restore_user_email_edit_text);

        ImageView arrowBackImageView = findViewById(R.id.arrow_back_image_view);
        arrowBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestorePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button restoreButton = findViewById(R.id.restore_button);
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userEmailEditText.getText().toString())) {
                    Toast.makeText(RestorePasswordActivity.this, R.string.incorrect_email, Toast.LENGTH_LONG)
                            .show();
                } else {
                    showProgressDialog();

                    String userEmail = userEmailEditText.getText().toString();

                    firebaseAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RestorePasswordActivity.this, R.string.check_email, Toast.LENGTH_LONG)
                                                .show();

                                        Intent intent = new Intent(RestorePasswordActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RestorePasswordActivity.this, R.string.auth_failed, Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(RestorePasswordActivity.this);
        progressDialog.setTitle(R.string.restore_password_button_text);
        progressDialog.setMessage(getResources().getString(R.string.wait_text));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RestorePasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
