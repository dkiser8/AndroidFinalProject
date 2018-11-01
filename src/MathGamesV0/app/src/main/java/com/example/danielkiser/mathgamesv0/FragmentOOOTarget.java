package com.example.danielkiser.mathgamesv0;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOOOTarget extends Fragment
{
    private Button btnOperator1,btnOperator2,btnOperator3,btnEqualsSign;
    private TextView tvNum1, tvNum2, tvNum3, tvNum4, tvResult, tvTarget;
    private View root;
    private Pojo4OOMath pjThisGame;

    public FragmentOOOTarget()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        pjThisGame = new Pojo4OOMath();

        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_oootarget, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        btnOperator1 = (Button) root.findViewById(R.id.btnOOOTarget_Operator1);
        btnOperator2 = (Button) root.findViewById(R.id.btnOOOTarget_Operator2);
        btnOperator3 = (Button) root.findViewById(R.id.btnOOOTarget_Operator3);
        btnEqualsSign = (Button) root.findViewById(R.id.btnOOOTarget_Equals);

        tvNum1 = (TextView) root.findViewById(R.id.tvOOOTarget_Number1);
        tvNum2 = (TextView) root.findViewById(R.id.tvOOOTarget_Number2);
        tvNum3 = (TextView) root.findViewById(R.id.tvOOOTarget_Number3);
        tvNum4 = (TextView) root.findViewById(R.id.tvOOOTarget_Number4);
        tvResult = (TextView) root.findViewById(R.id.tvOOOTarget_Result);
        tvTarget = (TextView) root.findViewById(R.id.tvOOOTarget_Target);


        View.OnClickListener clickListenerOpChange = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cycleOperator(v);
//                mCallback.updateRemainingChange(getChangeAmountSelected(v));
            }
        };

        View.OnClickListener clickListenerEqualsSign = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkTotal();
//                mCallback.updateRemainingChange(getChangeAmountSelected(v));
            }
        };

        btnOperator1.setOnClickListener(clickListenerOpChange);
        btnOperator2.setOnClickListener(clickListenerOpChange);
        btnOperator3.setOnClickListener(clickListenerOpChange);

        btnEqualsSign.setOnClickListener(clickListenerEqualsSign);

    }

    private void populateRandomValues()
    {


//        tvNum1.setText(Pojo4OOMath.RandomNumber(iLowerBound, iUpperBound));
    }

    private void checkTotal()
    {

    }

    private void cycleOperator(View v)
    {

    }


}
