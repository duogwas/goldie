package fithou.duogwas.goldie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import fithou.duogwas.goldie.Entity.User;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Utils.DeviceTokenManager;
import fithou.duogwas.goldie.Utils.UserManager;

public class IntroActivity extends AppCompatActivity {
    TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        AnhXa();
        getDeviceToken();
        tvStartClick();
    }

    private void AnhXa() {
        tvStart = findViewById(R.id.tvStart);
    }

    private void tvStartClick() {
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User isLoged = UserManager.getSavedUser(IntroActivity.this, "User", "MODE_PRIVATE", User.class);
                if (isLoged != null) {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(IntroActivity.this, SignInActivity.class));
                }
            }
        });
    }

    private void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("TAG", token);

                        //save token
                        DeviceTokenManager.saveDeviceToken(IntroActivity.this, token);
                    }
                });
    }
}