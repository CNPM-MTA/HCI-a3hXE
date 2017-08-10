package cnpm.com.xe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cnpm.com.xe.R;
import cnpm.com.xe.adapters.ChuyenAdapter;
import cnpm.com.xe.databases.DatabaseHandler;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.CHUYEN;


public class DanhSachChuyenActivity extends AppCompatActivity {

    ListView lvChuyen;
    ChuyenAdapter _adapter;
    ArrayList<Chuyen> listChuyen;
    Intent _intent;

    DatabaseHandler _databaseHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_chuyen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách chuyến");

        //
        init();
        //
//        if (listChuyen.size() == 0) {
//            TextView txtview = (TextView) findViewById(R.id.khongcochuyen);
//            txtview.setVisibility(View.VISIBLE);
//        } else {
            _adapter = new ChuyenAdapter(listChuyen, this);
            lvChuyen.setAdapter(_adapter);
            lvChuyen.setOnItemClickListener(On_Item_Click);
//        }
    }

    AdapterView.OnItemClickListener On_Item_Click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            _intent = new Intent(DanhSachChuyenActivity.this, ChiTietChuyenActivity.class);

            Chuyen chuyen = _adapter.getItem(position);
            Log.w("diem di", chuyen.getDiemdon());
            Log.w("diem den", chuyen.getDiemden());
            _intent.putExtra(CHUYEN, chuyen);
            startActivity(_intent);
        }
    };

    private void init() {
        lvChuyen = (ListView) findViewById(R.id.lvChuyen);
        listChuyen = _databaseHandler.getAllChuyen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }
}
