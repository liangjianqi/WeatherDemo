package cn.cnxad.weatherdemo.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.cnxad.weatherdemo.R;
import cn.cnxad.weatherdemo.databinding.ItemWindDailyBinding;
import cn.cnxad.weatherdemo.databinding.ItemWindHourBinding;
import cn.cnxad.weatherdemo.entity.WeatherBean;

/**
 * 作者: Louis on 2016/5/13 11:03
 */
public class WindHourAdapter extends RecyclerView.Adapter<WindHourAdapter.ViewHolder> {

    private List<WeatherBean.HeWeatherdataserviceBean.HourlyForecastBean> list;
    private LayoutInflater mInflater;

    public WindHourAdapter(Context mContext) {
        list = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wind_hour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherBean.HeWeatherdataserviceBean.HourlyForecastBean item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void insertItem(WeatherBean.HeWeatherdataserviceBean.HourlyForecastBean item, int position) {
        list.add(position, item);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemWindHourBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(WeatherBean.HeWeatherdataserviceBean.HourlyForecastBean item) {
            mBinding.setItem(item);
        }
    }
}
