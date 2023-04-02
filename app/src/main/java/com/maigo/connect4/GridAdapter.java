package com.maigo.connect4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    static class ViewHolder {
        Button button;
    }

    private final List<Integer> buttonList;
    private final LayoutInflater inflater;
    private final int layoutId;

    GridAdapter(Context context, int layoutId, List<Integer> iList){
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
        buttonList = iList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            holder = new ViewHolder();

            holder.button = convertView.findViewById(R.id.button);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (buttonList.get(position) == 1) {
            holder.button.setBackgroundResource(R.drawable.red_circle);
        }else if (buttonList.get(position) == 2){
            holder.button.setBackgroundResource(R.drawable.bule_circle);
        }else{
            holder.button.setBackgroundResource(R.drawable.white_circle);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return buttonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}