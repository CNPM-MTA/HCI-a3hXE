package cnpm.com.xe.activities;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cnpm.com.xe.R;
import cnpm.com.xe.databases.DatabaseHandler;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.TT_DADI;
import static cnpm.com.xe.SettingsXe.TT_DAHUY;
import static cnpm.com.xe.SettingsXe.TT_DANGDOI;
import static cnpm.com.xe.SettingsXe.USER_NAME;
import static cnpm.com.xe.SettingsXe.USER_PASS;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(OnClick);
        DatabaseHandler db = new DatabaseHandler(this);
        db.deleteAll_Chuyen();
        db.add_Chuyen(new Chuyen("BigC Thăng Long 212 Trần Duy Hưng Cầu Giấy Hà Nội", "Số 10 Đường A Tp Việt Trì Phú Thọ",
                35, 250000,10,    "4/5/2017", "3/5/2017", false, 1, TT_DADI));
        db.add_Chuyen(new Chuyen("Bưu điện tỉnh Ninh Bình Tp Ninh Bình", "Ngõ 199 Hồ Tùng Mậu Hà Nội",
                105, 250000, 10,  "10/10/2016", "10/10/2016",  false, 1, TT_DAHUY));
        db.add_Chuyen(new Chuyen("Làng văn hóa các dân tộc Việt Nam", "Ngõ 199 Hồ Tùng Mậu Hà Nội",
                43, 367000,15,"10/10/2016", "15/6/2017", false, 1, TT_DANGDOI));
        db.add_Chuyen(new Chuyen("Bến xe khách thành phố Ninh Bình", "Ngõ 199 Hồ Tùng Mậu Hà Nội",
                43, 367000,15,"5/23/2017", "8/6/2017", false, 1, TT_DANGDOI));
    }

    View.OnClickListener OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isChecked()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(USER_NAME, edtUsername.getText().toString());
                intent.putExtra(USER_PASS, edtPassword.getText().toString());
                startActivity(intent);
            } else
                Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
        }
    };

    private void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtPassword = (EditText) findViewById(R.id.txtPassword);
        edtUsername = (EditText) findViewById(R.id.txtPhone);
    }

    private boolean isChecked() {
        if (edtUsername.getText().toString().equals("123") && edtPassword.getText().toString().equals("123")) {
            return true;
        }
        return false;
    }
}
