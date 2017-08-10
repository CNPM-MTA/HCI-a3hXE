package cnpm.com.xe.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cnpm.com.xe.R;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.TT_DADI;
import static cnpm.com.xe.SettingsXe.TT_DAHUY;
import static cnpm.com.xe.SettingsXe.TT_DANGCHAY;
import static cnpm.com.xe.SettingsXe.TT_DANGDOI;

/**
 * Created by dinht on 14/05/2017.
 */

public class ChuyenAdapter extends BaseAdapter {
    ArrayList<Chuyen> _lsData;
    LayoutInflater _inflater;

    public ChuyenAdapter() {
    }

    public ChuyenAdapter(ArrayList<Chuyen> _lsData, Context context) {
        this._lsData = _lsData;
        this._inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _lsData.size();
    }

    @Override
    public Chuyen getItem(int position) {
        return _lsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = _inflater.inflate(R.layout.item_chuyen, null);
        }

        TextView giodi = (TextView) convertView.findViewById(R.id.giodi);
        TextView ngaykhoihanh = (TextView) convertView.findViewById(R.id.ngaydi);
        TextView diemdon = (TextView) convertView.findViewById(R.id.diemdon);
        TextView diemden = (TextView) convertView.findViewById(R.id.diemden);
        TextView soluong = (TextView) convertView.findViewById(R.id.soluong);
        TextView gia = (TextView) convertView.findViewById(R.id.gia);
        ImageView tinhtrang = (ImageView) convertView.findViewById(R.id.tinhtrang_img);

        Chuyen item = _lsData.get(position);
        giodi.setText(item.getGioKhoiHanh() + "");
        ngaykhoihanh.setText(item.getNgayKhoiHanh());
        if (item.getTinhTrang() == TT_DANGCHAY) {
            tinhtrang.setImageResource(R.drawable.run);
        } else if (item.getTinhTrang() == TT_DADI)
            tinhtrang.setImageResource(R.drawable.done);
        if (item.getTinhTrang() == TT_DANGDOI)
            tinhtrang.setImageResource(R.drawable.clock);
        else if (item.getTinhTrang() == TT_DAHUY)
            tinhtrang.setImageResource(R.drawable.deletee);
        ngaykhoihanh.setText(item.getNgayKhoiHanh());

//        ngaydi.setText("1/1/2017");
        diemdon.setText(item.getDiemdon());
        diemden.setText(item.getDiemden());
        soluong.setText("x" + item.getSoluong());
        gia.setText(item.getGiaDuKien() + "VND");

        return convertView;
    }
}
