package cnpm.com.xe.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cnpm.com.xe.R;
import cnpm.com.xe.json.DirectionsJSONParser;
import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.CHUYEN;
import static cnpm.com.xe.SettingsXe.COLOR_CHOOSE;
import static cnpm.com.xe.SettingsXe.COLOR_NONE;
import static cnpm.com.xe.SettingsXe.DANH_SACH;
import static cnpm.com.xe.SettingsXe.DAT_XE;
import static cnpm.com.xe.SettingsXe.DAT_XE_THANH_CONG;
import static cnpm.com.xe.SettingsXe.DIEM_DEN;
import static cnpm.com.xe.SettingsXe.DIEM_DON;
import static cnpm.com.xe.SettingsXe.DIEM_DON_PLACE_PICKER;
import static cnpm.com.xe.SettingsXe.DIEM_DEN_PLACE_PICKER;
import static cnpm.com.xe.SettingsXe.LOI_KHOANG_CACH;

public class DatXeActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private static final float MAX_ZOOM = 16;
    private static final float MIN_ZOOM = 4;

    private static final String API = "23";
    Place _diemdon_place;
    Place _diemden_place;
    BitmapDescriptor _bitmapMarker;

    protected Location _location_from;
    protected Location _location_to;

    Chuyen _chuyen;
    TextView _username;
    TextView _diemdon;
    TextView _diemden;
    ImageView _hoandoi;
    ImageView _vitrihientai;
    TextView _loaixe;
    ImageView _loaixe_img;
    LinearLayout _dichung;
    LinearLayout _tietkiem;
    LinearLayout _caocap;
    Switch _khuhoi;
    ImageView _datxe;

    GoogleApiClient mGoogleApiClient;

    private Intent _intent;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datxe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Đặt xe");
        init();
        _username = (TextView) findViewById(R.id.username);
//        if( getIntent().getSerializableExtra(USER_NAME) != null){
//            _username.setText((String) getIntent().getSerializableExtra(USER_NAME));
//        }
        if (getIntent().getSerializableExtra(DIEM_DON) != null && getIntent().getSerializableExtra(DIEM_DEN) != null) {
            _diemden.setText((String) getIntent().getSerializableExtra(DIEM_DEN));
            _diemdon.setText((String) getIntent().getSerializableExtra(DIEM_DON));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

            return;
        }
        mapFrag =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        mapFrag.getMapAsync(this);
        ((SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mapFrag)).getMapAsync(this);
        mGoogleMap.setMyLocationEnabled(true);

    }

    private void init() {
        _chuyen = new Chuyen();
        mapFrag = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        _username = (TextView) findViewById(R.id.username);
        //mapFrag.getMapAsync((OnMapReadyCallback) this);
        _diemdon = (TextView) findViewById(R.id.diemdon);
        _diemdon.setOnClickListener(OnClick);
        _diemden = (TextView) findViewById(R.id.diemden);
        _diemden.setOnClickListener(OnClick);

        _hoandoi = (ImageView) findViewById(R.id.swap);
        _hoandoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_diemdon.getText() != null && _diemden.getText() != null) {
                    String temp = _diemdon.getText().toString();
                    _diemdon.setText(_diemden.getText().toString());
                    _diemden.setText(temp);
                }
            }
        });
        _loaixe_img = (ImageView) findViewById(R.id.loaixe_img);
        _loaixe = (TextView) findViewById(R.id.loaixe);
        _khuhoi = (Switch) findViewById(R.id.khuhoi);
        if (_khuhoi != null) _khuhoi.setOnCheckedChangeListener(OnCheckedChange);
        _dichung = (LinearLayout) findViewById(R.id.dichung);
        _tietkiem = (LinearLayout) findViewById(R.id.tietkiem);
        _caocap = (LinearLayout) findViewById(R.id.caocap);
        _datxe = (ImageView) findViewById(R.id.datxe);

        //
        _dichung.setOnClickListener(OnClick);
        _tietkiem.setOnClickListener(OnClick);
        _caocap.setOnClickListener(OnClick);
        _datxe.setOnClickListener(OnClick);
        //

        _vitrihientai = (ImageView) findViewById(R.id.curPosition);
        //hiển thị vị trí hiện tại
        _vitrihientai.setOnClickListener(OnClick);

        mapFrag = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        //mapFrag.getMapAsync((OnMapReadyCallback) this);
    }

    CompoundButton.OnCheckedChangeListener OnCheckedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                _chuyen.setKhuHoi(true);
            } else _chuyen.setKhuHoi(false);
        }
    };
    PlacePicker.IntentBuilder _builder;
    View.OnClickListener OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.curPosition: {
                    checkLocationPermission();
                    final double[] lati = {0};
                    final double[] longitude = {0};
                    mapFrag.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            _location_from = getLocation();
                            //không lấy được vị trí
                            if (_location_from != null) {
                                lati[0] = _location_from.getLatitude();
                                longitude[0] = _location_from.getLongitude();

                            } else {
                                //nếu ko lấy được thì xét ở MTA
                                lati[0] = 21.0470536;
                                longitude[0] = 105.7830134;
                            }
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(lati[0], longitude[0]))      // Sets the center of the map to Mountain View
                                    .zoom(17)                   // Sets the zoom
                                    .build();                   // Creates a CameraPosition from the builder

                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            googleMap.setMaxZoomPreference(MAX_ZOOM);
                            googleMap.setMinZoomPreference(MIN_ZOOM);
                        }
                    });
                }
                case R.id.diemdon: {
                    _builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(_builder.build(DatXeActivity.this), DIEM_DON_PLACE_PICKER);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case R.id.diemden: {
                    _builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(_builder.build(DatXeActivity.this), DIEM_DEN_PLACE_PICKER);
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
                            Toast.makeText(DatXeActivity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.w("Khoảng cách", "khong canh " + khoangcach);
                        _intent = new Intent(DatXeActivity.this, ChiTietDatChuyenActivity.class);
                        _chuyen = new Chuyen(_diemdon.getText().toString(), _diemden.getText().toString(), khoangcach);
                        if (_khuhoi != null)
                            if (_khuhoi.isChecked())
                                _chuyen.setKhuHoi(true);
                            else _chuyen.setKhuHoi(false);
                        _intent.putExtra(CHUYEN, _chuyen);
//                        startActivity(_intent);
                        startActivityForResult(_intent, DAT_XE);
                    }
                    break;
                case R.id.dichung: {

                    _dichung.setBackgroundColor(Color.parseColor(COLOR_CHOOSE));
                    _tietkiem.setBackgroundColor(Color.parseColor(COLOR_NONE));
                    _caocap.setBackgroundColor(Color.parseColor(COLOR_NONE));

                    break;
                }
                case R.id.tietkiem: {
                    _dichung.setBackgroundColor(Color.parseColor(COLOR_NONE));
                    _tietkiem.setBackgroundColor(Color.parseColor(COLOR_CHOOSE));
                    _caocap.setBackgroundColor(Color.parseColor(COLOR_NONE));

                    break;
                }
                case R.id.caocap: {
                    _dichung.setBackgroundColor(Color.parseColor(COLOR_NONE));
                    _tietkiem.setBackgroundColor(Color.parseColor(COLOR_NONE));
                    _caocap.setBackgroundColor(Color.parseColor(COLOR_CHOOSE));

                    break;
                }
            }
        }
    };

    //check nhap diem den - di
    private boolean isChecked() {
        //check điểm đón và điểm đến
        String diemdon = _diemdon.getText().toString();
        String diemden = _diemden.getText().toString();
        if (diemdon.equals("") || diemden.equals("")) {
            Toast.makeText(DatXeActivity.this, "Bạn phải nhập điểm đến và đi!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_diemden_place == null || _diemdon_place == null) return false;
        //check khoảng cách
        double khoangcach = CalculationByDistance(_diemdon_place.getLatLng(), _diemden_place.getLatLng());
        if (khoangcach < 10 || khoangcach > 1500) {
            Toast.makeText(DatXeActivity.this, "Bạn vui lòng chọn lại địa chỉ trong phạm vi từ 10km đến 1500km", Toast.LENGTH_SHORT).show();
            return false;
        }
        _chuyen.setKhoangCach(khoangcach);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DAT_XE:
                if (resultCode == DAT_XE_THANH_CONG) {
                    _chuyen = (Chuyen) getIntent().getSerializableExtra(CHUYEN);

//                    _listChuyen.add(_chuyen);
                    _intent = new Intent(DatXeActivity.this, DanhSachChuyenActivity.class);
                    _intent.putExtra(CHUYEN, _chuyen);

                    startActivityForResult(_intent, DAT_XE_THANH_CONG);
                    return;
                } else {

                }
                break;
            case DANH_SACH:
                break;
            case DIEM_DON_PLACE_PICKER: {
                if (data != null) {
                    _diemdon_place = PlacePicker.getPlace(data, this);
                    _diemdon.setText(_diemdon_place.getAddress());
                    mapFrag.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            googleMap.clear();
                            MarkerOptions markerOptions1 = new MarkerOptions().
                                    icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map))
                                    .position(_diemdon_place.getLatLng())
                                    .title("Điểm đón");//set marker custom icon if needed
                            MarkerOptions markerOptions2;
                            if (_diemden_place != null) {
                                markerOptions2 = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map1))
                                        .position(_diemden_place.getLatLng())
                                        .title("Điểm đến");
                                googleMap.addMarker(markerOptions2);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_diemden_place.getLatLng(), 18.0f));
                            }
                            googleMap.addMarker(markerOptions1);

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_diemdon_place.getLatLng(), 18.0f));//
                            googleMap.setMaxZoomPreference(MAX_ZOOM);
                            googleMap.setMinZoomPreference(MIN_ZOOM);
                        }
                    });

                }
                break;
            }
            case DIEM_DEN_PLACE_PICKER: {
                if (data != null) {
                    _diemden_place = PlacePicker.getPlace(data, this);
                    _diemden.setText(_diemden_place.getAddress());
                    LatLngBounds.Builder bld = new LatLngBounds.Builder();
                    bld.include(_diemden_place.getLatLng());
                    if (_diemdon_place != null) bld.include(_diemdon_place.getLatLng());
                    mapFrag.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            googleMap.clear();
                            MarkerOptions markerOptions1 = new MarkerOptions().
                                    icon(BitmapDescriptorFactory.fromResource(R.drawable.map1))
                                    .position(_diemden_place.getLatLng())
                                    .title("Điểm đến");//set marker custom icon if needed
                            MarkerOptions markerOptions2;
                            if (_diemdon_place != null) {
                                markerOptions2 = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map))
                                        .position(_diemdon_place.getLatLng())
                                        .title("Điểm đón");
                                googleMap.addMarker(markerOptions2);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_diemdon_place.getLatLng(), 18.0f));
                            }

                            googleMap.addMarker(markerOptions1);

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_diemden_place.getLatLng(), 18.0f));//
                            googleMap.setMaxZoomPreference(MAX_ZOOM);
                            googleMap.setMinZoomPreference(MIN_ZOOM);

                        }
                    });
                }
                break;
            }
            default:
                return;

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_datxe:
                _intent = new Intent(this, DatXeActivity.class);
                startActivity(_intent);
                break;
            case R.id.nav_danhsachchuyen:
                _intent = new Intent(this, DanhSachChuyenActivity.class);
                startActivity(_intent);
                break;
            case R.id.navTimXe:
                _intent = new Intent(this, TimXeActivity.class);
                startActivity(_intent);
                break;
            case R.id.nav_thongbao:
                Toast.makeText(this, "Thông báo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_lienhe:
                Toast.makeText(this, "Liên hệ", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Location getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            // isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            //  if (isGPSEnabled == false && isNetworkEnabled == false) {
            if (isNetworkEnabled == false) {
                return _location_from;
            } else {
                if (isNetworkEnabled) {
                    _location_from = null;
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling

                            return _location_from;
                        }
                        _location_from = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (_location_from != null) {
                            return _location_from;
                        }
                    }
                }

                if (isGPSEnabled) {
                    _location_from = null;
                    if (_location_from == null) {
                        if (locationManager != null) {
                            _location_from = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (_location_from != null) {
                                return _location_from;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _location_from;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Vui lòng kiểm tra lại kết nối Internet");
            dialog.setPositiveButton("Mở cài đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                new AlertDialog.Builder(this)
//                        .setTitle("Cho phép truy cập địa điểm của bạn")
//                        .setMessage("Ứng dụng cần được cho phép truy cập địa điểm của bạn, đồng ý?")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(DatXeActivity.this,
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                final LatLng LOCATION_1 = new LatLng(21.0472612,-105.7840705);
//                CameraPosition cameraPosition = new CameraPosition.Builder()
//                        .target(LOCATION_1)
//                        .zoom(17)
//                        .bearing(90)
//                        .tilt(30)
//                        .build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.setMaxZoomPreference(MAX_ZOOM);
        googleMap.setMinZoomPreference(MIN_ZOOM);
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
//            mapFrag.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(GoogleMap googleMap) {
//                    googleMap.addPolygon(lineOptions);
//                }
//            });
            mGoogleMap.addPolyline(lineOptions);
        }

    }
}
