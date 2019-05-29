package com.example.scannf.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scannf.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SingUpActivity extends AppCompatActivity {

    private static final String TAG = "QuickNotesMainActivity";
    private Button btnSingUp;
    private EditText etEmail, etPass, etRepeatPass, etName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        btnSingUp = findViewById(R.id.singup_btn_sing_up);
        etEmail = findViewById(R.id.signup_email);
        etPass = findViewById(R.id.singup_password);
        etRepeatPass = findViewById(R.id.singup_password_repeat);
        etName = findViewById(R.id.signup_name);

        mAuth = FirebaseAuth.getInstance();


        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singup();
            }
        });

    }

    public void singup() {
        String pass, repeatPass, email, name;

        name = etName.getText().toString();
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();
        repeatPass = etRepeatPass.getText().toString();

        if (validationInfo(email, name, pass, repeatPass)) {
            insertFirebase(name, email, pass);


        }
    }

    public boolean validationInfo(String email, String name, String pass, String repeatPass) {
        boolean checked = false;
        if (!name.isEmpty()) {
            if (name.length() >= 4 && name.contains(" ")) {
                if (!email.isEmpty()) {
                    if (email.length() > 10 && email.contains("@") && email.contains(".")) {
                        if (!pass.isEmpty()) {
                            if (pass.length() >= 6) {
                                if (!repeatPass.isEmpty()) {
                                    if (pass.equals(repeatPass)) {
                                        checked = true;
                                    } else {
                                        showToast("Senhas não coincidem", 1);
                                    }
                                } else {
                                    showToast("Repita a senha", 1);
                                }

                            } else {
                                showToast("A senha deve conter mais de 6 caracteres", 1);
                            }
                        } else {
                            showToast("Digite a senha", 1);
                        }
                    } else {
                        showToast("Parece que seu e-mail não é válido\nTente novamente", 1);
                    }
                } else {
                    showToast("Parece que seu e-mail não foi digitado", 1);
                }
            } else {
                showToast("Parece que o nome digitado não é válido\nTente novamente", 1);
            }
        } else {
            showToast("Parece que o nome não foi digitado", 1);
        }
        return checked;
    }

    public void insertFirebase(final String name, String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest setName = new UserProfileChangeRequest.Builder().setDisplayName(name)
                                    .build();
                            user.updateProfile(setName)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                            Toast.makeText(SingUpActivity.this, "Usuario criado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SingUpActivity.this, "Algo deu errado\nTente novamente mais tarde",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void showToast(String message, int duration) {
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

}
