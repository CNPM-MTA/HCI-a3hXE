package cnpm.com.xe.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import cnpm.com.xe.R;
import cnpm.com.xe.databases.DatabaseHandler;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.CHUYEN;
import static cnpm.com.xe.SettingsXe.FORMAT_DATE;
import static cnpm.com.xe.SettingsXe.HUY_CHUYEN;
import static cnpm.com.xe.SettingsXe.TT_DADI;
import static cnpm.com.xe.SettingsXe.TT_DAHUY;
import static cnpm.com.xe.SettingsXe.TT_DANGCHAY;
import static cnpm.com.xe.SettingsXe.TT_DANGDOI;
import static cnpm.com.xe.SettingsXe.VND;

public class ChiTietChuyenActivity extends AppCompatActivity {

    Chuyen _chuyen;

    ImageView _tinhtrang_img;
    TextView _tinhtrang;
    TextView _diemdon;
    TextView _diemden;
    TextView _gia;
    TextView _mave;
    TextView _loaixe;
    TextView _ngaydi;
    TextView _soluong;
    TextView _ghichu;
    TextView _kieudi;
    Button _chinhsua;
    Button _huychuyen;

    DatabaseHandler _databaseHandler = new DatabaseHandler(this);
    Intent _intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_chuyen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết chuyến");
        init();


        setView();
    }

    private void init() {
        _chuyen = new Chuyen();
        _diemdon = (TextView) findViewById(R.id.diemdon);
        _diemden = (TextView) findViewById(R.id.diemden);
        _kieudi = (TextView) findViewById(R.id.kieudi);
        _ngaydi = (TextView) findViewById(R.id.ngaydi);
        _gia = (TextView) findViewById(R.id.gia);
        _tinhtrang_img = (ImageView) findViewById(R.id.tinhtrang_img);
        _tinhtrang = (TextView) findViewById(R.id.tinhtrang);
        _soluong = (TextView) findViewById(R.id.loaixe);
        _mave = (TextView) findViewById(R.id.mave);
        _ghichu = (TextView) findViewById(R.id.ghichu);
        _loaixe = (TextView) findViewById(R.id.loaixe);
        _chinhsua = (Button) findViewById(R.id.chinhsua);
        _huychuyen = (Button) findViewById(R.id.huychuyen);

        _huychuyen.setOnClickListener(OnClick);
        _chinhsua.setOnClickListener(OnClick);
    }

    private void setView() {
        if (getIntent().getSerializableExtra(CHUYEN) instanceof Chuyen)
            _chuyen = (Chuyen) getIntent().getSerializableExtra(CHUYEN);
        else onBackPressed();
        _diemdon.setText(_chuyen.getDiemdon());
        _diemden.setText(_chuyen.getDiemden());
        _mave.setText(_chuyen.getId());
        _ngaydi.setText(_chuyen.getGioKhoiHanh() + "h " + _chuyen.getNgayKhoiHanh());
        _soluong.setText("X" + _chuyen.getSoluong());
        _gia.setText(_chuyen.getGiaDuKien() + VND);
        _loaixe.setText("x" + _chuyen.getSoluong());
        switch (_chuyen.getTinhTrang()) {
            case TT_DANGCHAY:
                _tinhtrang_img.setImageResource(R.drawable.run);
                _tinhtrang.setText("Đang chạy");
                _chinhsua.setVisibility(View.GONE);
                _huychuyen.setVisibility(View.GONE);
                break;
            case TT_DADI:
                _tinhtrang_img.setImageResource(R.drawable.done);
                _tinhtrang.setText("Đã đi");
                _chinhsua.setVisibility(View.GONE);
                _huychuyen.setVisibility(View.GONE);
                break;
            case TT_DAHUY:
                _tinhtrang_img.setImageResource(R.drawable.close);
                _tinhtrang.setText("Đã hủy");
                _chinhsua.setVisibility(View.GONE);
                _huychuyen.setVisibility(View.GONE);
                break;
            case TT_DANGDOI:
                _tinhtrang_img.setImageResource(R.drawable.clock);
                _tinhtrang.setText("Đang xếp chỗ");
                _chinhsua.setVisibility(View.VISIBLE);
                _huychuyen.setVisibility(View.VISIBLE);
                break;
//            default:
//                _tinhtrang_img.setImageResource(R.drawable.clock);
//                _tinhtrang.setText("Đang xếp chỗ");
//                _chinhsua.setVisibility(View.VISIBLE);
//                _huychuyen.setVisibility(View.VISIBLE);
//                break;
        }

    }

    Dialog dialog;
    AlertDialog.Builder builder;
    View layout;
    TextView content;
    TextView action_huy;
    TextView action_goitongdai;
    TextView action_thoat;
    View.OnClickListener OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.huychuyen) {
                Log.w("ngayngay" + _chuyen.getNgayKhoiHanh(), "ff");
                if (isCheck()) {
                    //alertdialog
                    builder = new AlertDialog.Builder(ChiTietChuyenActivity.this, R.style.AppTheme_AlertDialog_ChiTietChuyen);
                    layout = LayoutInflater.from(ChiTietChuyenActivity.this).inflate(R.layout.popup_xoa_chuyen_layout, null);
                    content = (TextView) layout.findViewById(R.id.noidung);
                    action_huy = (TextView) layout.findViewById(R.id.action_huy);
                    action_thoat = (TextView) layout.findViewById(R.id.action_thoat);

                    content.setText(Html.fromHtml("Bạn có thực sự muốn hủy chuyến?"));

                    action_thoat.setText("Thoát");
                    action_thoat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });
                    action_huy.setText("Hủy");
                    action_huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                                _databaseHandler.delete_Chuyen(_chuyen);
                                _intent = new Intent(ChiTietChuyenActivity.this, DanhSachChuyenActivity.class);
                                startActivity(_intent);

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
                } else {
                    //Toast.makeText(ChiTietChuyenActivity.this, "", Toast.LENGTH_SHORT).show();

                    builder = new AlertDialog.Builder(ChiTietChuyenActivity.this, R.style.AppTheme_AlertDialog_ChiTietChuyen);
                    layout = LayoutInflater.from(ChiTietChuyenActivity.this).inflate(R.layout.popup_goitongdai, null);
                    content = (TextView) layout.findViewById(R.id.noidung);
                    action_goitongdai = (TextView) layout.findViewById(R.id.action_goitongdai);
                    action_thoat = (TextView) layout.findViewById(R.id.action_thoat);

                    content.setText(Html.fromHtml("Chuyến đi đã quá thời gian được hủy. Gọi tổng đài để biết được trợ giúp"));

                    action_thoat.setText("Thoát");
                    action_thoat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });
                    action_goitongdai.setText("Gọi tổng đài");
                    action_goitongdai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog != null) {
                                dialog.dismiss();
                                _intent = new Intent(Intent.ACTION_DIAL);
                                _intent.setData(Uri.parse("tel:01685677236"));
                                startActivity(_intent);

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
            } else if (v.getId() == R.id.chinhsua) {
                _intent = new Intent(ChiTietChuyenActivity.this, UpdateChuyenActivity.class);
                _intent.putExtra(CHUYEN, _chuyen);
                startActivity(_intent);
            }
        }
    };

    private boolean isCheck() {
        Calendar calendar = Calendar.getInstance();
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Log.w("ngayngay" + _chuyen.getNgayKhoiHanh(), "");
        if (_chuyen.getNgayKhoiHanh().equals("8/6/2017") || _chuyen.getNgayKhoiHanh().equals("08/6/2017"))
            return false;
//        if (sdf.format(sdf.format(_chuyen.getNgayKhoiHanh())).equals(sdf.format(calendar.getTime())))
//            return false;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
