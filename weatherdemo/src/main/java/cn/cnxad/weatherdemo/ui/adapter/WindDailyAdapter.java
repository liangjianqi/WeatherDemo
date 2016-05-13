package cn.cnxad.weatherdemo.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.cnxad.weatherdemo.R;
import cn.cnxad.weatherdemo.databinding.ItemWindDailyBinding;
import cn.cnxad.weatherdemo.entity.WeatherBean;

/**
 * 作者: Louis on 2016/5/13 11:03
 */
public class WindDailyAdapter extends RecyclerView.Adapter<WindDailyAdapter.ViewHolder> {

    private Context mContext;
    private List<WeatherBean.HeWeatherdataserviceBean.DailyForecastBean> list;
    private LayoutInflater mInflater;

    public WindDailyAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wind_daily, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherBean.HeWeatherdataserviceBean.DailyForecastBean item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void insertItem(WeatherBean.HeWeatherdataserviceBean.DailyForecastBean item, int position) {
        list.add(position, item);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemWindDailyBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(WeatherBean.HeWeatherdataserviceBean.DailyForecastBean item) {
            mBinding.setItem(item);
        }
    }
}
