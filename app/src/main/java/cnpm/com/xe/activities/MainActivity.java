package cnpm.com.xe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cnpm.com.xe.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView datxe;
    TextView danhsachchuyen;
    TextView timxe;
    TextView thoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu chính");

        init();
    }

    private void init() {
        datxe = (TextView) findViewById(R.id.datxe);
        danhsachchuyen = (TextView) findViewById(R.id.danhsach);
        timxe = (TextView) findViewById(R.id.timxe);
        thoat = (TextView) findViewById(R.id.thoat);

        datxe.setOnClickListener(this);
        danhsachchuyen.setOnClickListener(this);
        timxe.setOnClickListener(this);
        thoat.setOnClickListener(this);
    }

    //come back home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.datxe:
                intent = new Intent(this, DatXe1Activity.class);
                startActivity(intent);
                break;
            case R.id.danhsach:
                intent = new Intent(this, DanhSachChuyenActivity.class);
                startActivity(intent);
                break;
            case R.id.timxe:
                intent = new Intent(this, TimXeActivity.class);
                startActivity(intent);
                break;
            case R.id.thoat:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
