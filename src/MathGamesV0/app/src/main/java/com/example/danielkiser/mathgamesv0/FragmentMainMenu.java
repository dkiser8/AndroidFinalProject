package com.example.danielkiser.mathgamesv0;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMainMenu extends Fragment {

    private Button btn1a, btn1b, btn2, btn3;
    private View root;

    private GamePicker mCallback;


    //0,1,2,3
    public interface GamePicker
    {
        void selectedGame(int gameSelected);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallback = (GamePicker) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + getString(R.string.warnInterface1));
        }
    }


    public FragmentMainMenu()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        btn1a = (Button) root.findViewById(R.id.btnFor1a);
        btn1b = (Button) root.findViewById(R.id.btnFor1b);
        btn2 = (Button) root.findViewById(R.id.btnFor2);
        btn3 = (Button) root.findViewById(R.id.btnFor3);


        View.OnClickListener clickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCallback.selectedGame(getGameSelected(v));
            }
        };

        btn1a.setOnClickListener(clickListener);
        btn1b.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);
    }

    private int getGameSelected(View v)
    {
        if (v == null)
        {
            return -1;
        }

        switch(v.getId())
        {
            case R.id.btnFor1a:
                return 0;
            case R.id.btnFor1b:
                return 1;
            case R.id.btnFor2:
                return 2;
            case R.id.btnFor3:
                return 3;
            default:
                Log.e(getString(R.string.btnerror),getString(R.string.btnerror2));
                return -1;
        }
    }

}
