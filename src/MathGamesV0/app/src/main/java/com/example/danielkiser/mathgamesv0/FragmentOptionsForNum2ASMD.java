package com.example.danielkiser.mathgamesv0;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOptionsForNum2ASMD extends Fragment {

    private View root;
    private Button btnSave;
    private CheckBox checkBoxAdd, checkBoxSubtract, checkBoxMultiply, checkBoxDivide;
    private TextInputEditText edtMin, edtMax;
    private SetMinMax mCallback;

    public interface SetMinMax
    {
        void setG2MinMax(boolean[] baAddSubMultDivOptions, double dMin, double dMax);
    }


    public FragmentOptionsForNum2ASMD() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallback = (SetMinMax) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + getString(R.string.warn4));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_options_for_num2_asmd, container, false);
    }



    @Override
    public void onResume()
    {
        super.onResume();

        edtMin = (TextInputEditText) root.findViewById(R.id.inputG2OptionsMin);
        edtMax = (TextInputEditText) root.findViewById(R.id.inputG2OptionsMax);

        checkBoxAdd = (CheckBox) root.findViewById(R.id.checkBoxG2OptionsAdd);
        checkBoxSubtract = (CheckBox) root.findViewById(R.id.checkBoxG2OptionsSubtract);
        checkBoxMultiply = (CheckBox) root.findViewById(R.id.checkBoxG2OptionsMultiply);
        checkBoxDivide = (CheckBox) root.findViewById(R.id.checkBoxG2OptionsDivide);


        btnSave = (Button) root.findViewById(R.id.buttonG2OptionsSave);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                readInTextField();
            }
        });
    }


    private void readInTextField()
    {
        //if any are null we've got a problem
        if(
                (edtMin == null) ||
                (edtMax == null) ||
                (checkBoxAdd == null) ||
                (checkBoxSubtract == null) ||
                (checkBoxMultiply == null) ||
                (checkBoxDivide == null))
        {
            //toast error
            return;
        }


        boolean[] baAddSubMultDivOptions = new boolean[4];

        baAddSubMultDivOptions[0] = checkBoxAdd.isChecked();
        baAddSubMultDivOptions[1] = checkBoxSubtract.isChecked();
        baAddSubMultDivOptions[2] = checkBoxMultiply.isChecked();
        baAddSubMultDivOptions[3] = checkBoxDivide.isChecked();


        //error check something selected
        if(!(baAddSubMultDivOptions[0] ||baAddSubMultDivOptions[1] ||baAddSubMultDivOptions[2] ||baAddSubMultDivOptions[3]))
        {
            //toast error
            return;
        }



        double dMin;
        double dMax;

        if((!"".equals(edtMin.getText().toString())) && (!".".equals(edtMin.getText().toString())))
        {
            dMin = Double.parseDouble(edtMin.getText().toString());
        }
        else
        {
            //toast error
            return;
        }

        if((!"".equals(edtMax.getText().toString())) && (!".".equals(edtMax.getText().toString())))
        {
            dMax = Double.parseDouble(edtMax.getText().toString());
        }
        else
        {
            //toast error
            return;
        }

        if(dMin >= dMax)
        {
            //toast error
            return;
        }


        mCallback.setG2MinMax(baAddSubMultDivOptions, dMin, dMax);
    }

    private void toastError()
    {

    }


}
