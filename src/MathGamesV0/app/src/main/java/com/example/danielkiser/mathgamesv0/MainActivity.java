package com.example.danielkiser.mathgamesv0;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements
        FragmentMainMenu.GamePicker,
        FragmentOptionsForNum2ASMD.SetMinMax
{
    private FragmentManager fm;
    private FragmentGame2 fg2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //hide fragments not in use
        fm = getSupportFragmentManager();

        getSupportFragmentManager().beginTransaction()
                .hide(fm.findFragmentById(R.id.fragment0))
                .hide(fm.findFragmentById(R.id.fragment1))
                .hide(fm.findFragmentById(R.id.fragment2))
                .hide(fm.findFragmentById(R.id.fragment3))
                .hide(fm.findFragmentById(R.id.fragment4))
                .hide(fm.findFragmentById(R.id.fragment5))
                .commit();

    }


    @Override
    public void selectedGame(int gameSelected)
    {
        Fragment fragToShow;

        switch (gameSelected)
        {
            case 0:
//                fragToShow = fm.findFragmentById(R.id.fragment0);
                fragToShow = fm.findFragmentById(R.id.fragment1);
                break;
            case 1:
                fragToShow = fm.findFragmentById(R.id.fragment2);
                break;
            case 2:
                fragToShow = fm.findFragmentById(R.id.fragment4);
                break;
            case 3:
                fragToShow = fm.findFragmentById(R.id.fragment5);
                break;
            default:
                Log.e(getString(R.string.btnerror),getString(R.string.btnerror2));
                return;
        }

        getSupportFragmentManager().beginTransaction()
                .hide(fm.findFragmentById(R.id.fragment))
                .show(fragToShow)
                .commit();
    }

    @Override
    public void setG2MinMax(boolean[] baAddSubMultDivOptions, double dMin, double dMax)
    {
        fg2 = (FragmentGame2) fm.findFragmentById(R.id.fragment4);

        if(fg2 != null)
        {
            fg2.SetOptionsInG2(baAddSubMultDivOptions.clone(), dMin, dMax);
        }


        getSupportFragmentManager().beginTransaction()
                .hide(fm.findFragmentById(R.id.fragment3))
                .show(fm.findFragmentById(R.id.fragment4))
                .commit();
    }
}
