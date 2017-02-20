package com.babyplan.salt.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Salt on 2017/2/19.
 */
class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private LayoutInflater mInflater;
    private Context mContest;
    private List<data>mDatas;
    public SimpleAdapter(Context context,List<data>datas)
    {
        this.mContest=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_single_textview,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data apple=mDatas.get(position);
        holder.tv.setText(apple.getName());
        holder.iv.setImageResource(apple.getImageId());
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void  addData(int pos,String text,int id)
    {
        data apple=new data(text,id);
        mDatas.add(pos,apple);
        notifyItemInserted(pos);
    }
    public void  deleteData(int pos)
    {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }
}





class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView tv;
    ImageView iv;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv= (TextView) itemView.findViewById(R.id.id_tv);
        iv= (ImageView) itemView.findViewById(R.id.id_iv);
    }
}