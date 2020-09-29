package bhachat.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;



public class LoginActivity extends AppCompatActivity {
    EditText emailId, password, RegEmailId, RegPassword, username, Regusername;
    TextView btnSingUp, btnSingIn, registr;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    TextView forgot_password;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser !=null){
            Intent intent2 = new Intent(LoginActivity.this, ChatActivity.class);
            startActivity(intent2);
            finish();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        registr = findViewById(R.id.registr);
        emailId = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        username = findViewById(R.id.username);
        btnSingIn = findViewById(R.id.login_bttn);
        forgot_password = findViewById(R.id.forgot_password);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = emailId.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();

                }else {

                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(LoginActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        registr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //  tv.startAnimation(finalAnim);
                //   tv2.setVisibility(View.VISIBLE);
                //   username.setEnabled(false);
                //   password.setEnabled(false);
                //   emailId.setEnabled(false);

                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }

        });

    }

}
