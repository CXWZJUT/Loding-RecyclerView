package com.babyplan.salt.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<data> mDatas;
    private SimpleAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();

        initViews();

        mAdapter=new SimpleAdapter(this,mDatas);
        mRecyclerView.setAdapter(mAdapter);
    //设置RecycleView的布局管理
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    //设置RV的Item间的分隔线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initViews() {
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initDatas() {
        mDatas =new ArrayList<data>();
        for(int i='A';i<='z';i++)
        {
            data apple=new data (""+(char)i,R.drawable.ic_menu_add);
            mDatas.add(apple);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.action_add:
                mAdapter.addData(1,"Insert One",R.drawable.ic_menu_delete);
                break;
            case R.id.action_delete:
                mAdapter.deleteData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
