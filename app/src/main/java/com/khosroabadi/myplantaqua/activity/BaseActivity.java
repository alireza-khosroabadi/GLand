package com.khosroabadi.myplantaqua.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.tools.ConstantManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    protected Toolbar mToolbar;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
      /*  mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
*/

        drawerLayout = (DrawerLayout) findViewById(R.id.home_activity_drawer_layout);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        getWindow().setBackgroundDrawable(null);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.plants_drawer: {
                Intent intent = new Intent(BaseActivity.this, ProductActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME , ConstantManager.CATEGORY.PLANT);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }

            case R.id.fishes_drawer: {
                Intent intent = new Intent(BaseActivity.this, ProductActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.FRESH_WHATER_FISH);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            case R.id.shrimp_drawer: {
                Intent intent = new Intent(BaseActivity.this, ProductActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.FRESH_WATER_SHRIMP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            case R.id.snail_drawer: {
                Intent intent = new Intent(BaseActivity.this, ProductActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME, ConstantManager.CATEGORY.SNAIL);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            case R.id.calculator_drawer: {
                Intent intent = new Intent(BaseActivity.this, CalculatorActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.about_app_drawer: {
                Intent intent = new Intent(BaseActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            }
/*            case R.id.documents_drawer: {
                Intent intent = new Intent(BaseActivity.this, NewProductListActivity.class);
                intent.putExtra(ConstantManager.CATEGORY_NAME , ConstantManager.CATEGORY.PLANT);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }*/
            default:
                return true;
        }

    }

    private void showDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            hideDrawer();
        else
            super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


}
