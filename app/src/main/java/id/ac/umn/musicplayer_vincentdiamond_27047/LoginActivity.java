package id.ac.umn.musicplayer_vincentdiamond_27047;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private EditText usernameEt;
    private EditText passwordEt;
    private TextView warningTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().setTitle("Halaman Login");
        getSupportActionBar().hide();
        loginBtn = (Button) findViewById(R.id.activity_login_btn_login);
        usernameEt = (EditText) findViewById(R.id.activity_login_et_username);
        passwordEt = (EditText) findViewById(R.id.activity_login_et_password);
        warningTv = (TextView) findViewById(R.id.activity_login_tv_warning);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (username.equals("uasmobile") && password.equals("uasmobilegenap")) {
                    Intent intent = new Intent(LoginActivity.this, PlaylistActivity.class);
                    startActivity(intent);
                } else {
                    warningTv.setText("Username atau password yang kamu masukkan salah!");
                }
            }
        });
    }
}