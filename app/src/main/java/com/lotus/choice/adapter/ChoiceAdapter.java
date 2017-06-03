package com.lotus.choice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lotus.choice.R;

import java.util.List;

/**
 * Created by Thl on 2017/6/2.
 */

public class ChoiceAdapter extends BaseAdapter {

    private int selectPosition=0;
    private List<String> datas;
    private Context context;

    public ChoiceAdapter(Context context, List<String> datas, int selectPosition){
        this.context=context;
        this.datas=datas;
        this.selectPosition=selectPosition;
    }

    public void setSelectPosition(int selectPostion){
        this.selectPosition=selectPostion;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_choice_info,parent,false);
            holder=new ViewHolder();
            holder.cbChooice=(CheckBox) convertView.findViewById(R.id.cb_choice);
            holder.tvName=(TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        String name=datas.get(position);
        holder.tvName.setText(name);
        holder.cbChooice.setChecked(position==selectPosition?true:false);

        return convertView;
    }

    static class ViewHolder{
        CheckBox cbChooice;
        TextView tvName;
    }
}
