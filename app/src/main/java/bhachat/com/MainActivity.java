package bhachat.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.*;


public class MainActivity extends AppCompatActivity {
    RelativeLayout tv,tv2,activity_main;
    EditText emailId, password, RegEmailId, RegPassword, username, Regusername;
    TextView btnSingUp, btnSingIn, registr;
    FirebaseAuth auth;
    DatabaseReference reference;
    Animation anim = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        activity_main = findViewById(R.id.activity_main);
        anim = AnimationUtils.loadAnimation(this, R.anim.login_wind);
        registr = findViewById(R.id.registr);
        ImageView reg_close = (ImageView)findViewById(R.id.reg_close2);
        Animation finalAnim = anim;



        emailId = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        username = findViewById(R.id.username);
        btnSingIn = findViewById(R.id.login_bttn);

        RegEmailId = findViewById(R.id.reg_email2);
        Regusername = findViewById(R.id.reg_username);
        RegPassword = findViewById(R.id.reg_pass2);
        btnSingUp = findViewById(R.id.reg_butt2);

        auth = FirebaseAuth.getInstance();


        registr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //  tv.startAnimation(finalAnim);
                //   tv2.setVisibility(View.VISIBLE);
                username.setEnabled(false);
                password.setEnabled(false);
                emailId.setEnabled(false);

                Intent intent4 = new Intent(MainActivity.this, LoginActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
                finish();



            }

        });


        reg_close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                tv2.setVisibility(View.INVISIBLE);
                username.setEnabled(true);
                password.setEnabled(true);
                emailId.setEnabled(true);

            }



        });


        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = emailId.getText().toString();
                String txt_password = password.getText().toString();


                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty((txt_email)) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(MainActivity.this, "Все поля должы быть заполнены!", Toast.LENGTH_SHORT).show();
                }else if(txt_password.length() < 6){
                    Toast.makeText(MainActivity.this, "Пароль должен быть больше 6 символов", Toast.LENGTH_SHORT).show();
                }else {
                    register(txt_username,txt_email,txt_password);
                }

            }
        });


    }

    private void  register(final String username, String emailId, String password){

        auth.createUserWithEmailAndPassword(emailId, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", username.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });

                        }else {
                            Toast.makeText(MainActivity.this, "Вы не можете зарегистрировать эту пару E-mail и пароля", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }




}
