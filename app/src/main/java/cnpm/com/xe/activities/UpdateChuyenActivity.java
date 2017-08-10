package cnpm.com.xe.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

import cnpm.com.xe.R;
import cnpm.com.xe.databases.DatabaseHandler;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.CHUYEN;
import static cnpm.com.xe.SettingsXe.DIEM_DEN_PLACE_PICKER1;
import static cnpm.com.xe.SettingsXe.DIEM_DON_PLACE_PICKER1;
import static cnpm.com.xe.SettingsXe.FORMAT_DATE;
import static cnpm.com.xe.SettingsXe.VND;

public class UpdateChuyenActivity extends AppCompatActivity {
    Chuyen _chuyen;

    TextView _diemdon;
    TextView _diemden;
    TextView _gia;
    TextView _khoangcach;
    TextView _ngaydi;
    EditText _soluong;
    TextView _loaixe;
    EditText _ghichu;
    EditText _makhuyenmai;
    Button _chinhsua;
    Button _thoat;
    DatabaseHandler _databaseHandler = new DatabaseHandler(this);

    PlacePicker.IntentBuilder _builder;

    Calendar _calendar;
    DatePickerDialog.OnDateSetListener _date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chuyen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cập nhật thông tin chuyến");

        init();
        getSerializable();
        setView();
    }

    private void setView() {
        _diemden.setText(_chuyen.getDiemden());
        _diemdon.setText(_chuyen.getDiemdon());
        _khoangcach.setText(_chuyen.getKhoangCach() + "");
        _ngaydi.setText(_chuyen.getNgayKhoiHanh());
        _ghichu.setText(_chuyen.getGhichu());
        _makhuyenmai.setText(_chuyen.getMakhuyenmai());
        _soluong.setText(_chuyen.getSoluong() + "");
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
//        _diemdon.setOnClickListener(On_Click);
        _diemden = (TextView) findViewById(R.id.diemden);
//        _diemden.setOnClickListener(On_Click);
        _soluong = (EditText) findViewById(R.id.soluong);
        _gia = (TextView) findViewById(R.id.gia);
        _khoangcach = (TextView) findViewById(R.id.khoangcach);
        _ngaydi = (TextView) findViewById(R.id.ngaydi);
        _loaixe = (TextView) findViewById(R.id.loaixe);
        _loaixe.setOnClickListener(On_Click);
        _ghichu = (EditText) findViewById(R.id.ghichu);
        _makhuyenmai = (EditText) findViewById(R.id.makhuyenmai);
        _chinhsua = (Button) findViewById(R.id.chinhsua);
        _thoat = (Button) findViewById(R.id.thoat);
        _thoat.setOnClickListener(On_Click);
        _chinhsua.setOnClickListener(On_Click);

        _soluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _ngaydi.setText(_chuyen.getNgayKhoiHanh());

        _calendar = Calendar.getInstance();

        _date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                _calendar.set(Calendar.YEAR, year);
                _calendar.set(Calendar.MONTH, monthOfYear);
                _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth - 1);
                _ngaydi.setText(FORMAT_DATE.format(_calendar.getTime()));
                _chuyen.setNgayKhoiHanh(0 + "");
            }
        };
        _ngaydi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateChuyenActivity.this, _date, _calendar
                        .get(Calendar.YEAR), _calendar.get(Calendar.MONTH),
                        _calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        _soluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") && !_soluong.getText().toString().trim().equals("")){
                    _gia.setText(100000* Integer.parseInt(_soluong.getText().toString().trim()) + "VNĐ");
                }
                else _gia.setText("0");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    Dialog dialog;
    AlertDialog.Builder builder;
    View layout;
    TextView content;
    TextView action_goitongdai;
    TextView action_thoat;

    Place _diemdon_place;
    Place _diemden_place;


    View.OnClickListener On_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.diemdon: {
//                    _builder = new PlacePicker.IntentBuilder();
//                    try {
//                        startActivityForResult(_builder.build(UpdateChuyenActivity.this), DIEM_DON_PLACE_PICKER1);
//                    } catch (GooglePlayServicesRepairableException e) {
//                        e.printStackTrace();
//                    } catch (GooglePlayServicesNotAvailableException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//                case R.id.diemden: {
//
//                    if (_diemdon_place == null) {
//                        Toast.makeText(UpdateChuyenActivity.this, "Nhập điểm đón trước", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    _builder = new PlacePicker.IntentBuilder();
//                    try {
//                        startActivityForResult(_builder.build(UpdateChuyenActivity.this), DIEM_DEN_PLACE_PICKER1);
//                    } catch (GooglePlayServicesRepairableException e) {
//                        e.printStackTrace();
//                    } catch (GooglePlayServicesNotAvailableException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
                case R.id.loaixe: {
                    builder = new AlertDialog.Builder(UpdateChuyenActivity.this, R.style.AppTheme_AlertDialog_ChiTietChuyen);
                    layout = LayoutInflater.from(UpdateChuyenActivity.this).inflate(R.layout.popup_kieudi, null);
                    TextView xe4cho = (TextView) layout.findViewById(R.id.xe4cho);
                    TextView xe7cho = (TextView) layout.findViewById(R.id.xe7cho);
                    TextView xe29cho = (TextView) layout.findViewById(R.id.xe29cho);
                    TextView xe45cho = (TextView) layout.findViewById(R.id.xe45cho);
                    xe4cho.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog != null) {
                                dialog.dismiss();
                                _loaixe.setText("Xe 4 chỗ");
                                _loaixe.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.xe4cho), null, null, null);
                            }
                        }
                    });
                    xe7cho.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog != null) {
                                dialog.dismiss();
                                _loaixe.setText("Xe 7 chỗ");
                                _loaixe.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.xe7cho), null, null, null);
                            }
                        }
                    });
                    xe29cho.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog != null) {
                                dialog.dismiss();
                                _loaixe.setText("Xe 29 chỗ");
                                _loaixe.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.xe29cho), null, null, null);
                            }
                        }
                    });
                    xe45cho.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog != null) {
                                dialog.dismiss();
                                _loaixe.setText("Xe 45 chỗ");
                                _loaixe.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.xe45cho), null, null, null);
                            }
                        }
                    });
                    builder.setView(layout);
                    dialog = builder.create();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {

                        }
                    });
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    break;
                }
                case R.id.chinhsua:
                    if (isChecked()) {
                        _chuyen = getChuyen();
//                        Log.w("chinhsua", "dfsdoifsdfsd");
                        Toast.makeText(UpdateChuyenActivity.this, "Update", Toast.LENGTH_SHORT).show();
                        _databaseHandler.update_Chuyen(_chuyen);
//                        _intent = new Intent(UpdateChuyenActivity.this, ChiTietChuyenActivity.class);
//                        startActivity(_intent);
                        onBackPressed();
                        break;
                    }
                    break;
                case  R.id.thoat:{
                    builder = new AlertDialog.Builder(UpdateChuyenActivity.this, R.style.AppTheme_AlertDialog_ChiTietChuyen);
                    layout = LayoutInflater.from(UpdateChuyenActivity.this).inflate(R.layout.popup_goitongdai, null);
                    content = (TextView) layout.findViewById(R.id.noidung);
                    action_goitongdai = (TextView) layout.findViewById(R.id.action_goitongdai);
                    action_thoat = (TextView) layout.findViewById(R.id.action_thoat);

                    content.setText(Html.fromHtml("Lưu thay đổi?"));

                    action_thoat.setText("Không");
                    action_thoat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                                onBackPressed();
                            }
                        }
                    });
                    action_goitongdai.setText("Có");
                    action_goitongdai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                                _chuyen = getChuyen();
                                _databaseHandler.update_Chuyen(_chuyen);
                                onBackPressed();
                            }
                        }
                    });
                    builder.setView(layout);
                    dialog = builder.create();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {

                        }
                    });
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        }
    };

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
        _chuyen.setNgayKhoiHanh(_ngaydi.getText().toString());
        _chuyen.setGhichu(_ghichu.getText().toString().equals("") ? " " : _ghichu.getText().toString());
        _chuyen.setMakhuyenmai(_makhuyenmai.getText().toString().equals("") ? " " : _makhuyenmai.getText().toString());

        return _chuyen;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    double _khoangcach_km;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case DIEM_DON_PLACE_PICKER1: {
                Log.w("OnActivityResult", "diem don place");
                if (data != null) {
                    Log.w("OnactivityResult",PlacePicker.getPlace(data, this).getAddress() + "");
                    _diemdon_place = PlacePicker.getPlace(data, this);
                    if (_diemden_place != null && _diemdon_place != null) {
                        _khoangcach_km = CalculationByDistance(_diemdon_place.getLatLng(), _diemden_place.getLatLng());
                        if (_khoangcach_km < 10 || _khoangcach_km > 1500) {
                            _diemdon_place = null;
                            _khoangcach_km = 0;
                            Toast.makeText(UpdateChuyenActivity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
                        } else {
                            _diemdon.setText(_diemdon_place.getAddress());
                            _chuyen.setKhoangCach(_khoangcach_km);
                            _khoangcach.setText(_khoangcach_km + "");
                        }

                        if (_diemden_place != null && _diemdon_place != null)
                            _khoangcach.setText(CalculationByDistance(_diemden_place.getLatLng(), _diemdon_place.getLatLng()) + "");
                    } else _diemdon.setText(_diemdon_place.getAddress());
                }
                break;
            }
            case DIEM_DEN_PLACE_PICKER1: {
                if (data != null) {
                    _diemden_place = PlacePicker.getPlace(data, this);
                    if (_diemden_place != null && _diemden_place != null) {
                        _khoangcach_km = CalculationByDistance(_diemdon_place.getLatLng(), _diemden_place.getLatLng());
                        if (_khoangcach_km < 10 || _khoangcach_km > 1500) {
                            _diemden_place = null;
                            _khoangcach_km = 0;
                            Toast.makeText(UpdateChuyenActivity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
                        } else {
                            _diemden.setText(_diemden_place.getAddress());
                            _khoangcach.setText(_khoangcach_km + "");
                            _chuyen.setKhoangCach(_khoangcach_km);
                        }
                    } else _diemden.setText(_diemden_place.getAddress());

                    break;
                }
            }
            default:
                return;
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Double.parseDouble(String.format("%.2f", Radius * c));
    }
}
