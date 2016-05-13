package cn.cnxad.weatherdemo.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import cn.cnxad.weatherdemo.Constant;
import cn.cnxad.weatherdemo.R;
import cn.cnxad.weatherdemo.databinding.ActivityMainBinding;
import cn.cnxad.weatherdemo.entity.WeatherBean;
import cn.cnxad.weatherdemo.net.CallbackListener;
import cn.cnxad.weatherdemo.net.VolleyManager;
import cn.cnxad.weatherdemo.persenter.DataboxSet;
import cn.cnxad.weatherdemo.persenter.WeatherPersenter;
import cn.cnxad.weatherdemo.ui.adapter.WindDailyAdapter;
import cn.cnxad.weatherdemo.ui.adapter.WindHourAdapter;
import cn.cnxad.weatherdemo.utils.FullyLinearLayoutManager;
import cn.cnxad.weatherdemo.view.DashLineItemDecoration;

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
    @Bind(R.id.recycler_day)
    RecyclerView recyclerDay;
    @Bind(R.id.recycler_hour)
    RecyclerView recyclerHour;

    private static int EXTRA_REQUEST_CODE_CITY = 100;
    private VolleyManager mVolleyManager;
    //背景透明度
    private int mAlpha = 1;
    private ActivityMainBinding mDataBinding;
    private WindHourAdapter hourAdapter;
    private WindDailyAdapter dailyAdapter;
    private Window mWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        //此处如果是第一次进入，则引导用户选择当前城市
        String cityId = "CN101110101";

        requestDta(cityId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EXTRA_REQUEST_CODE_CITY) {
                String cityId = data.getStringExtra(Constant.CITYID);
                requestDta(cityId);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        setTranslate();

        hourAdapter = new WindHourAdapter(this);
        FullyLinearLayoutManager hourLayoutManager = new FullyLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerHour.setLayoutManager(hourLayoutManager);
        recyclerHour.setHasFixedSize(true);
        recyclerHour.setAdapter(hourAdapter);


        dailyAdapter = new WindDailyAdapter(this);
        FullyLinearLayoutManager dailyLayoutManager = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerDay.setLayoutManager(dailyLayoutManager);
        recyclerDay.setHasFixedSize(true);
        recyclerDay.setAdapter(dailyAdapter);
        recyclerDay.addItemDecoration(new DashLineItemDecoration());

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
     *
     * @param cityId
     */
    private void requestDta(final String cityId) {
        mVolleyManager = VolleyManager.getInstance(getApplicationContext());

        new WeatherPersenter(cityId, mVolleyManager, new CallbackListener<WeatherBean>() {
            @Override
            public void onSuccessResponse(WeatherBean response) {
                WeatherBean.HeWeatherdataserviceBean resp = response.getHeWeatherdataservice().get(0);
                if (resp.getStatus().equals("ok")) {
                    mDataBinding.setWeather(resp);

                    //按小时显示天气情况
                    DataboxSet.insertHourList(resp.getHourly_forecast(), hourAdapter);
                    //按天显示天气情况
                    DataboxSet.insertDailyList(resp.getDaily_forecast(), dailyAdapter);
                } else {
                    LogUtils.e(response);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.e(error);
                SnackbarManager.show(Snackbar.with(getApplicationContext()).text("网络仿问出错,请重试").duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE).actionLabel("重试").actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        requestDta(cityId);
                    }
                }), MainActivity.this);
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

        if (id == R.id.action_select_city) {
            startActivityForResult(new Intent(MainActivity.this, CityActivity.class), EXTRA_REQUEST_CODE_CITY);
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
