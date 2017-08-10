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
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

import cnpm.com.xe.R;
import cnpm.com.xe.databases.DatabaseHandler;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.CHUYEN;
import static cnpm.com.xe.SettingsXe.DANH_SACH;
import static cnpm.com.xe.SettingsXe.DAT_XE;
import static cnpm.com.xe.SettingsXe.DAT_XE_THANH_CONG;
import static cnpm.com.xe.SettingsXe.DIEM_DEN_PLACE_PICKER;
import static cnpm.com.xe.SettingsXe.DIEM_DON_PLACE_PICKER;
import static cnpm.com.xe.SettingsXe.FORMAT_DATE;
import static cnpm.com.xe.SettingsXe.VND;

public class DatXe1Activity extends AppCompatActivity {
    Chuyen _chuyen;
    Intent _intent;

    TextView _diemdon;
    TextView _diemden;
    TextView _gia;
    TextView _khoangcach;
    TextView _ngaydi;
    TextView _giodi;
    TextView _ngayve;
    TextView _giove;
    EditText _soluong;
    LinearLayout _giove_ll;
    TextView _loaixe;
    EditText _ghichu;
    EditText _makhuyenmai;
    RadioButton _motchieu;
    RadioButton _khuhoi;
    Button _datxe;

    double _khoangcach_km;
    DatabaseHandler _databaseHandler = new DatabaseHandler(this);


    PlacePicker.IntentBuilder _builder;

    Calendar _calendar;
    DatePickerDialog.OnDateSetListener _date;
    DatePickerDialog.OnDateSetListener _date1;

    SupportMapFragment mapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_xe1);

        init();

    }

    private void init() {
        _chuyen = new Chuyen();

        _diemdon = (TextView) findViewById(R.id.diemdon);
        _diemdon.setOnClickListener(On_Click);
        _diemden = (TextView) findViewById(R.id.diemden);
        _diemden.setOnClickListener(On_Click);
        _soluong = (EditText) findViewById(R.id.soluong);
        _gia = (TextView) findViewById(R.id.gia);
        _khoangcach = (TextView) findViewById(R.id.khoangcach);
        _ngaydi = (TextView) findViewById(R.id.ngaydi);
        _giodi = (TextView) findViewById(R.id.giodi);
        _giove_ll = (LinearLayout) findViewById(R.id.giove_ll);
        _ngayve = (TextView) findViewById(R.id.ngayve);
        _giove = (TextView) findViewById(R.id.giove);
        _loaixe = (TextView) findViewById(R.id.loaixe);
        _loaixe.setOnClickListener(On_Click);
        _ghichu = (EditText) findViewById(R.id.ghichu);
        _makhuyenmai = (EditText) findViewById(R.id.makhuyenmai);
        _motchieu = (RadioButton) findViewById(R.id.motchieu);
        _khuhoi = (RadioButton) findViewById(R.id.khuhoi);
        _datxe = (Button) findViewById(R.id.datxe);
//
//        _khuhoi.setOnCheckedChangeListener(OnCheckedChange);
//        _motchieu.setOnCheckedChangeListener(OnCheckedChange);
        _datxe.setOnClickListener(On_Click);
        _calendar = Calendar.getInstance();

        _date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                _calendar.set(Calendar.YEAR, year);
                _calendar.set(Calendar.MONTH, monthOfYear);
                _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth-1);
                _ngaydi.setText(FORMAT_DATE.format(_calendar.getTime()));
                _chuyen.setNgayKhoiHanh(0 + "");
            }
        };
        _date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                _calendar.set(Calendar.YEAR, year);
                _calendar.set(Calendar.MONTH, monthOfYear);
                _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                _ngayve.setText(FORMAT_DATE.format(_calendar.getTime()));
                _chuyen.setNgayQuayLai(0 + "");
            }
        };
        //https://github.com/code-troopers/android-betterpickers
        //https://github.com/wdullaer/MaterialDateTimePicker
        _giodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder tpb = new TimePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment);
                tpb.show();

            }
        });

        _ngaydi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DatXe1Activity.this, _date, _calendar
                        .get(Calendar.YEAR), _calendar.get(Calendar.MONTH),
                        _calendar.get(Calendar.DAY_OF_MONTH)
                ).show();

            }
        });
        _ngayve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DatXe1Activity.this, _date1, _calendar
                        .get(Calendar.YEAR), _calendar.get(Calendar.MONTH),
                        _calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        _giove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder tpb = new TimePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment);
                tpb.show();
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
    TimePickerDialogFragment.TimePickerDialogHandler t  = new TimePickerDialogFragment.TimePickerDialogHandler() {
        @Override
        public void onDialogTimeSet(int reference, int hourOfDay, int minute) {

        }
    };


    Dialog dialog; // = null;
    AlertDialog.Builder builder;
    View layout;

    View.OnClickListener On_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.diemdon: {
                    _builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(_builder.build(DatXe1Activity.this), DIEM_DON_PLACE_PICKER);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case R.id.diemden: {

                    if (_diemdon_place == null) {
                        Toast.makeText(DatXe1Activity.this, "Nhập điểm đón trước", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    _builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(_builder.build(DatXe1Activity.this), DIEM_DEN_PLACE_PICKER);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case R.id.datxe:
                    if (isChecked()) {
                        if (_diemden_place == null || _diemdon_place == null) return;
                        //check khoảng cách
                        double khoangcach = CalculationByDistance(_diemdon_place.getLatLng(), _diemden_place.getLatLng());
                        if (khoangcach < 10 && khoangcach > 1500) {
                            Toast.makeText(DatXe1Activity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.w("Khoảng cách", "khong canh " + khoangcach);
                        _intent = new Intent(DatXe1Activity.this, ChiTietDatChuyenActivity.class);
                        _chuyen = new Chuyen(_diemdon.getText().toString(), _diemden.getText().toString(), khoangcach);
                        if (_khuhoi != null)
                            if (_khuhoi.isChecked())
                                _chuyen.setKhuHoi(true);
                            else _chuyen.setKhuHoi(false);


                        if (isChecked()) {
                            _chuyen = getChuyen();
                            _databaseHandler.add_Chuyen(_chuyen);
                            _intent = new Intent(DatXe1Activity.this, DanhSachChuyenActivity.class);
                            Toast.makeText(DatXe1Activity.this, "Đặt xe thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(_intent);
                        }

                        _intent.putExtra(CHUYEN, _chuyen);
                        startActivityForResult(_intent, DAT_XE);
                    }
                    break;
                case R.id.loaixe: {
                    builder = new AlertDialog.Builder(DatXe1Activity.this, R.style.AppTheme_AlertDialog_ChiTietChuyen);
                    layout = LayoutInflater.from(DatXe1Activity.this).inflate(R.layout.popup_kieudi, null);
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
            }
        }

    };

    private Chuyen getChuyen() {
        _chuyen.setDiemden(_diemden.getText().toString());
        _chuyen.setDiemdon(_diemdon.getText().toString());
        _chuyen.setNgayKhoiHanh(_ngaydi.getText().toString());
        _chuyen.setGioKhoiHanh1(_giodi.getText().toString());
        _chuyen.setSoluong((_soluong.getText().toString().equals("")
                || Integer.parseInt(_soluong.getText().toString()) <= 0)
                ? 1 : Integer.parseInt(_soluong.getText().toString()));
        _chuyen.setGiaDuKien(Double.parseDouble(_gia.getText().toString().replace(VND, "")));
        _chuyen.setSoluong(Integer.parseInt(_soluong.getText().toString()));

        _chuyen.setGhichu(_ghichu.getText().toString().equals("") ? " " : _ghichu.getText().toString());
        _chuyen.setMakhuyenmai(_makhuyenmai.getText().toString().equals("") ? " " : _makhuyenmai.getText().toString());

        Log.w("chuyenadapter___", _chuyen.getNgayKhoiHanh() + "  " + _chuyen.getGioKhoiHanh() + "  " + _chuyen.getSoluong());
        return _chuyen;
    }

    Place _diemdon_place;
    Place _diemden_place;

    private boolean isChecked() {
        //check điểm đón và điểm đến
        String diemdon = _diemdon.getText().toString();
        String diemden = _diemden.getText().toString();
        Log.w("điemon ", diemdon + "  " + diemden);
        if (diemdon.equals("") || diemden.equals("")) {
            Toast.makeText(DatXe1Activity.this, "Bạn phải nhập điểm đến và đi!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_diemden_place == null || _diemdon_place == null) return false;
        //check khoảng cách
        _khoangcach_km = CalculationByDistance(_diemdon_place.getLatLng(), _diemden_place.getLatLng());
        if (_khoangcach_km < 10 || _khoangcach_km > 1500) {
            Toast.makeText(DatXe1Activity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
            return false;
        }
        _chuyen.setKhoangCach(_khoangcach_km);
        return true;
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

//    CompoundButton.OnCheckedChangeListener OnCheckedChange = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (buttonView.getId() == R.id.motchieu)
//                _chuyen.setKhuHoi(!isChecked);
//            else _chuyen.setKhuHoi(isChecked);
//            if (_chuyen.isKhuHoi()) {
//                _giove_ll.setVisibility(View.VISIBLE);
//            } else {
//                _giove_ll.setVisibility(View.GONE);
//            }
//        }
//    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DAT_XE:
                if (resultCode == DAT_XE_THANH_CONG) {
                    _chuyen = (Chuyen) getIntent().getSerializableExtra(CHUYEN);
//                    _listChuyen.add(_chuyen);
                    _intent = new Intent(DatXe1Activity.this, DanhSachChuyenActivity.class);
                    _intent.putExtra(CHUYEN, _chuyen);
                    startActivity(_intent);
                    return;
                } else {

                }
                break;
            case DANH_SACH:
                break;
            case DIEM_DON_PLACE_PICKER: {
                if (data != null) {
                    _diemdon_place = PlacePicker.getPlace(data, this);
                    if (_diemden_place != null && _diemdon_place != null) {
                        _khoangcach_km = CalculationByDistance(_diemdon_place.getLatLng(), _diemden_place.getLatLng());
                        if (_khoangcach_km < 10 || _khoangcach_km > 1500) {
                            _diemdon_place = null;
                            _khoangcach_km = 0;
                            Toast.makeText(DatXe1Activity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
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
            case DIEM_DEN_PLACE_PICKER: {
                if (data != null) {
                    _diemden_place = PlacePicker.getPlace(data, this);
                    if (_diemden_place != null && _diemden_place != null) {
                        _khoangcach_km = CalculationByDistance(_diemdon_place.getLatLng(), _diemden_place.getLatLng());
                        if (_khoangcach_km < 10 || _khoangcach_km > 1500) {
                            _diemden_place = null;
                            _khoangcach_km = 0;
                            Toast.makeText(DatXe1Activity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
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

}
