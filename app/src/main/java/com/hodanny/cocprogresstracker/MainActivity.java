package com.hodanny.cocprogresstracker;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public int mCurrentTownhall = 5;

    private BuildingDataSource mDatabase;
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mMainRecyclerAdapter;

    private List<Building> mUserProgress = new ArrayList<>();

    private void InitViews(Context context)
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.main_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mMainRecyclerAdapter = new MainRecyclerAdapter(mUserProgress);
        mRecyclerView.setAdapter(mMainRecyclerAdapter);

    }

    private void InitDatabase()
    {
        DbHandler myDbHelper=new DbHandler(this);
        try {

            myDbHelper.initializeDataBase();
            mDatabase = new BuildingDataSource(this);
            mDatabase.open();
            mDatabase.populateUserProgress(mCurrentTownhall);
            mUserProgress = mDatabase.selectAllUserProgress();
            mDatabase.close();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitDatabase();
        InitViews(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
