package com.flowerfat.makepoint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.entity.db.OneDayPoints;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2015/8/22.
 */
public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.CardViewHolder> {


    private List<OneDayPoints> mDatas = new ArrayList<>();
    private Context context ;

    public PointsAdapter(Context context){
        this.context = context ;
    }

    @Override
    public PointsAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.item_pointslist, parent, false);
        final CardViewHolder _cardViewHolder = new CardViewHolder(view);


        return _cardViewHolder;
    }

    @Override
    public void onBindViewHolder(PointsAdapter.CardViewHolder holder, int position) {
        holder.text1.setText(mDatas.get(position).getPoint1().getTitle());
        holder.text2.setText(mDatas.get(position).getPoint2().getTitle());
        holder.text3.setText(mDatas.get(position).getPoint3().getTitle());
        holder.text4.setText(mDatas.get(position).getPoint4().getTitle());

        if(mDatas.get(position).getDate() != null){
            String today = mDatas.get(position).getDate().toString();
            holder.textTitle.setText(today);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addItems(List<OneDayPoints> mDatas){
        this.mDatas = mDatas ;
        notifyDataSetChanged();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public TextView text1, text2, text3, text4, textTitle ;

        public CardViewHolder(View view) {
            super(view);
            textTitle = (TextView)view.findViewById(R.id.item_title);
            text1 = (TextView)view.findViewById(R.id.item_block1);
            text2 = (TextView)view.findViewById(R.id.item_block2);
            text3 = (TextView)view.findViewById(R.id.item_block3);
            text4 = (TextView)view.findViewById(R.id.item_block4);
        }

    }
}
