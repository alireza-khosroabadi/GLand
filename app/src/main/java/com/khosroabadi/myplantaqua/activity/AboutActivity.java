package com.khosroabadi.myplantaqua.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.adapters.ReferencesAdapter;

import java.util.Arrays;
import java.util.List;

public class AboutActivity extends BaseActivity {

    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();

    }

    private void initializeView() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = getLayoutInflater().inflate(R.layout.activity_about, null , false);
        drawerLayout.addView(contentView,0);
        mToolbar = (Toolbar) findViewById(R.id.plant_activity_toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.public_toolbar_title);
        mToolbarTitle.setText(getString(R.string.about));
//mToolbarTitle.setText();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


}
