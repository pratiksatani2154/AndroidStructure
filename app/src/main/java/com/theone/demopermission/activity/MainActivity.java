package com.theone.demopermission.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.theone.demopermission.R;
import com.theone.demopermission.databinding.ActivityMainBinding;
import com.theone.demopermission.utills.GlobalUtills;
import com.theone.demopermission.utills.Permission;
import com.theone.demopermission.viewmodel.MainViewModel;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel=new MainViewModel(this,activityMainBinding);
        activityMainBinding.setMain(mainViewModel);
        Permission.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_CALENDAR,Permission.CAMERA});


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Permission.HandleResult(MainActivity.this,requestCode, permissions, grantResults)) {
            GlobalUtills.showMessage(MainActivity.this,"Returned True",GlobalUtills.LENGTH_SHORT,Color.GREEN,Color.WHITE);
        }else {
            GlobalUtills.showMessage(MainActivity.this,"Returned false",GlobalUtills.LENGTH_SHORT,Color.RED,Color.WHITE);


        }
    }
}
