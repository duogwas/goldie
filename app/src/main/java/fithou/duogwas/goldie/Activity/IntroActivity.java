package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import fithou.duogwas.goldie.Model.User;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Utils.ObjectSharedPreferences;
import vn.thanguit.toastperfect.ToastPerfect;

public class IntroActivity extends AppCompatActivity {
    TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        AnhXa();
        tvStartClick();
    }

    private void AnhXa() {
        tvStart = findViewById(R.id.tvStart);
    }

    private void tvStartClick() {
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User isLoged = ObjectSharedPreferences.getSavedObjectFromPreference(IntroActivity.this, "User", "MODE_PRIVATE", User.class);
                if (isLoged!=null){
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(IntroActivity.this, SignInActivity.class));
                }
            }
        });
    }
}