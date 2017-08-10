package cnpm.com.xe.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cnpm.com.xe.R;
import cnpm.com.xe.databases.DatabaseHandler;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.CHUYEN;
import static cnpm.com.xe.SettingsXe.DIEM_DEN;
import static cnpm.com.xe.SettingsXe.DIEM_DEN_PLACE_PICKER;
import static cnpm.com.xe.SettingsXe.DIEM_DON;
import static cnpm.com.xe.SettingsXe.DIEM_DON_PLACE_PICKER;
import static cnpm.com.xe.SettingsXe.FORMAT_DATE;
import static cnpm.com.xe.SettingsXe.VND;

public class ChiTietDatChuyenActivity extends AppCompatActivity {
    Chuyen _chuyen;
    Intent _intent;

    TextView _diemdon;
    TextView _diemden;
    TextView _gia;
    TextView _khoangcach;
    TextView _ngaydi;
    EditText _soluong;
    TextView _loaixe;
    EditText _ghichu;
    EditText _makhuyenmai;
    RadioButton _motchieu;
    RadioButton _khuhoi;
    Button _datxe;
    DatabaseHandler _databaseHandler = new DatabaseHandler(this);

    Calendar _calendar;
    DatePickerDialog.OnDateSetListener _date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dat_chuyen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("THÔNG TIN ĐẶT CHUYẾN");

        init();
        getSerializable();
        setView();
    }

    private void setView() {
        _diemden.setText(_chuyen.getDiemden());
        _diemden.setOnClickListener(On_Click);
        _diemdon.setText(_chuyen.getDiemdon());
        _diemdon.setOnClickListener(On_Click);
        _soluong.setText(_chuyen.getSoluong() > 0 ? _chuyen.getSoluong() + "" : "1");
        _soluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (_chuyen.getGiaDuKien() == 0)
                    _chuyen.setGiaDuKien(Double.parseDouble(_soluong.getText().toString())
                            * Double.parseDouble(_khoangcach.getText().toString()) * 1000);
                _gia.setText(_chuyen.getGiaDuKien() + VND);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (_chuyen.getGiaDuKien() == 0)
                    _chuyen.setGiaDuKien(Double.parseDouble(_soluong.getText().toString())
                            * Double.parseDouble(_khoangcach.getText().toString()) * 1000);
                _gia.setText(_chuyen.getGiaDuKien() + VND);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (_chuyen.getGiaDuKien() == 0)
                    _chuyen.setGiaDuKien(Double.parseDouble(_soluong.getText().toString())
                            * Double.parseDouble(_khoangcach.getText().toString()) * 1000);
                _gia.setText(_chuyen.getGiaDuKien() + VND);
            }
        });

        _khoangcach.setText(_chuyen.getKhoangCach() + "");
        _ngaydi.setText(FORMAT_DATE.format(Calendar.getInstance().getTime()));

        _calendar = Calendar.getInstance();

        if (_calendar == null) {
            _calendar = new Calendar() {
                @Override
                protected int handleGetLimit(int i, int i1) {
                    return 0;
                }

                @Override
                protected int handleComputeMonthStart(int i, int i1, boolean b) {
                    return 0;
                }

                @Override
                protected int handleGetExtendedYear() {
                    return 0;
                }
            };
            _calendar.set(2017, 5, 24);
        }
        _date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                _calendar.set(Calendar.YEAR, year);
                _calendar.set(Calendar.MONTH, monthOfYear);
                _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                _ngaydi.setText(FORMAT_DATE.format(_calendar.getTime()));
                _chuyen.setNgayKhoiHanh(0 + "");
            }
        };

//        _ngaydi.setText(_calendar.getTime().getMonth() + "/" + _calendar.getTime().getDay() + "/" + _calendar.getTime().getYear());
        _ngaydi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ChiTietDatChuyenActivity.this, _date, _calendar
                        .get(Calendar.YEAR), _calendar.get(Calendar.MONTH),
                        _calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

//        _loaixe.setText(_chuyen.);
//        _khuhoi.setOnCheckedChangeListener(OnCheckedChange);
//        if (_chuyen.isKhuHoi()) {
//            Log.w("Khuhoi", "1");
//            _khuhoi.setChecked(true);
//            _motchieu.setChecked(false);
//        } else {
//            _khuhoi.setChecked(false);
//            _motchieu.setChecked(true);
//        }
        if (_chuyen.isKhuHoi()) _khuhoi.setChecked(true);
        _ghichu.setText(_chuyen.getGhichu());
        _makhuyenmai.setText(_chuyen.getMakhuyenmai());
    }

    private void getSerializable() {
        if (getIntent().getSerializableExtra(CHUYEN) instanceof Chuyen) {
            _chuyen = (Chuyen) getIntent().getSerializableExtra(CHUYEN);
            return;
        }
    }


    private void init() {
        _chuyen = new Chuyen();

        _diemdon = (TextView) findViewById(R.id.diemdon);
        _diemdon.setOnLongClickListener(On_LongClick);
        _diemden = (TextView) findViewById(R.id.diemden);
        _diemden.setOnLongClickListener(On_LongClick);
        _soluong = (EditText) findViewById(R.id.soluong);
        _gia = (TextView) findViewById(R.id.gia);
        _khoangcach = (TextView) findViewById(R.id.khoangcach);
        _ngaydi = (TextView) findViewById(R.id.ngaydi);
        _loaixe = (TextView) findViewById(R.id.loaixe);
        _ghichu = (EditText) findViewById(R.id.ghichu);
        _makhuyenmai = (EditText) findViewById(R.id.makhuyenmai);
        _motchieu = (RadioButton) findViewById(R.id.motchieu);
        _khuhoi = (RadioButton) findViewById(R.id.khuhoi);
        _datxe = (Button) findViewById(R.id.datxe);

        _khuhoi.setOnCheckedChangeListener(OnCheckedChange);
        _motchieu.setOnCheckedChangeListener(OnCheckedChange);
        _datxe.setOnClickListener(On_Click);
    }

    View.OnLongClickListener On_LongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            _intent = new Intent(ChiTietDatChuyenActivity.this, DatXeActivity.class);
            _intent.putExtra(DIEM_DEN, _diemden.getText());
            _intent.putExtra(DIEM_DON, _diemdon.getText());
            Log.w("diemden", _diemden.getText() + "  " + _diemdon.getText());
            startActivity(_intent);
            return false;
        }
    };
    CompoundButton.OnCheckedChangeListener OnCheckedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.motchieu) {
                if (isChecked) {
                    _khuhoi.setChecked(false);
                    _motchieu.setChecked(true);
                } else {
                    _khuhoi.setChecked(true);
                    _motchieu.setChecked(false);
                }
            }
            if (buttonView.getId() == R.id.khuhoi) {
                if (isChecked) {
                    _khuhoi.setChecked(true);
                    _motchieu.setChecked(false);
                } else {
                    _khuhoi.setChecked(false);
                    _motchieu.setChecked(true);
                }
            }
        }
    };

    View.OnClickListener On_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.datxe:
                    if (isChecked()) {
                        _chuyen = getChuyen();
                        Toast.makeText(ChiTietDatChuyenActivity.this, _chuyen.getNgayKhoiHanh(), Toast.LENGTH_SHORT).show();
                        _databaseHandler.add_Chuyen(_chuyen);
                        _intent = new Intent(ChiTietDatChuyenActivity.this, DanhSachChuyenActivity.class);
                        startActivity(_intent);
                    }
                    break;
                case R.id.diemden: {

                    break;
                }
                case R.id.diemdon: {

                    break;
                }

            }
        }
    };

    //Check có được phép hủy hay ko
    private boolean isChecked() {

        return true;
    }

    private Chuyen getChuyen() {
        _chuyen.setDiemden(_diemden.getText().toString());
        _chuyen.setDiemdon(_diemdon.getText().toString());
        _chuyen.setSoluong((_soluong.getText().toString().equals("")
                || Integer.parseInt(_soluong.getText().toString()) <= 0)
                ? 1 : Integer.parseInt(_soluong.getText().toString()));
        _chuyen.setGiaDuKien(Double.parseDouble(_gia.getText().toString().replace(VND, "")));
        _chuyen.setSoluong(Integer.parseInt(_soluong.getText().toString()));

//        DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
//        _chuyen.setNgayKhoiHanh(DATE_FORMAT.format(_ngaydi.getText().toString()));

        _chuyen.setGhichu(_ghichu.getText().toString().equals("") ? " " : _ghichu.getText().toString());
        _chuyen.setMakhuyenmai(_makhuyenmai.getText().toString().equals("") ? " " : _makhuyenmai.getText().toString());

        Log.w("chuyenadapter___", _chuyen.getNgayKhoiHanh() + "  " + _chuyen.getGioKhoiHanh() + "  " + _chuyen.getSoluong());
        return _chuyen;
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
//
//    Place _diemdon_place;
//    Place _diemden_place;
//    BitmapDescriptor _bitmapMarker;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DIEM_DON_PLACE_PICKER: {
                if (data != null) {
//                    _diemdon_place = PlacePicker.getPlace(data, this);
//                    _diemdon.setText(_diemdon_place.getAddress());
//                    _bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

                }
                break;
            }
            case DIEM_DEN_PLACE_PICKER: {
                if (data != null) {
//                    _diemden_place = PlacePicker.getPlace(data, this);
//                    _diemden.setText(_diemden_place.getAddress());
//                    _bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                }
                break;
            }
        }
    }
}
