package cn.cnxad.weatherdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import cn.cnxad.weatherdemo.R;
import cn.cnxad.weatherdemo.databinding.ActivityMainBinding;
import cn.cnxad.weatherdemo.entity.WeatherBean;
import cn.cnxad.weatherdemo.net.CallbackListener;
import cn.cnxad.weatherdemo.net.VolleyManager;
import cn.cnxad.weatherdemo.persenter.WeatherPersenter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindColor(android.R.color.transparent)
    int toobarColor;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_main)
    LinearLayout contentMain;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private VolleyManager mVolleyManager;
    //背景透明度
    private int mAlpha = 1;
    private ActivityMainBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        requestDta();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        setTranslate();
    }

    private void setTranslate() {
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }

        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this, drawerLayout, mAlpha);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        contentMain.setBackgroundResource(R.mipmap.rain_bg);
    }

    /**
     * 请求数据
     */
    private void requestDta() {
        mVolleyManager = VolleyManager.getInstance(getApplicationContext());

        new WeatherPersenter("CN101110101", mVolleyManager, new CallbackListener<WeatherBean>() {
            @Override
            public void onSuccessResponse(WeatherBean response) {
                WeatherBean.HeWeatherdataserviceBean resp = response.getHeWeatherdataservice().get(0);
                if (resp.getStatus().equals("ok")) {
                    mDataBinding.setWeather(resp);
                } else {
                    LogUtils.e(response);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.e(error);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            requestDta();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
