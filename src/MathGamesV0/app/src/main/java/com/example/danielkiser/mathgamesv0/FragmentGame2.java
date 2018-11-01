package com.example.danielkiser.mathgamesv0;


import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGame2 extends Fragment
{
    private View root;
    private TextView tvN1, tvN2, tvOperator;
    private Button btnSubmit, btnDiv0;
    private Pojo4G2 pj4g2;
    private TextInputEditText edtInput;

    //states:
    //state 0: new question, submit button
    //state 1: correct, next question
    //state 2: incorrect
    private int iButtonState = 0;




    public FragmentGame2()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        pj4g2 = new Pojo4G2();

        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_fragment_game2, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        findViews();
        implementEvents();
        populateRandomValues();

    }

    private void findViews()
    {
        tvN1 = (TextView) root.findViewById(R.id.tvG2N1);
        tvN2 = (TextView) root.findViewById(R.id.tvG2N2);
        tvOperator = (TextView) root.findViewById(R.id.tvG2Operator);

        btnSubmit = (Button) root.findViewById(R.id.buttonG2Submit);
        btnDiv0 = (Button) root.findViewById(R.id.buttonG2Div0);

        edtInput = (TextInputEditText) root.findViewById(R.id.inputG2);
    }

    private void implementEvents()
    {
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswerOrNextQuestion();
            }
        });


        btnDiv0.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(pj4g2.getDividedByZero())
                {
                    checkAnswerOrNextQuestion(1);
                    return;
                }

                checkAnswerOrNextQuestion(2);
            }
        });


    }

    //states:
    //state 0: new question, submit button
    //state 1: correct, next question
    //state 2: incorrect
    private void checkAnswerOrNextQuestion(int iStateToMoveTo)
    {
        if(btnSubmit == null)
        {
            return;
        }

        switch (iStateToMoveTo)
        {
            case 0:
                iButtonState = 0;
                btnSubmit.setText(R.string.submit_answer);
                populateRandomValues();
                return;
            case 1:
                iButtonState = 1;
                btnSubmit.setText(R.string.correct_next_question);
                return;
            case 2:
                iButtonState = 2;
                Log.d("CorrectAnswerShouldBe:", ""+pj4g2.getAnswer());
                btnSubmit.setText(R.string.incorrect_try_again);
                return;
            default:
                iButtonState = -1;
                btnSubmit.setText(R.string.btnerror);
                return;
        }
    }

    private void checkAnswerOrNextQuestion()
    {
        if(btnSubmit == null)
        {
            return;
        }

        if(iButtonState == 1)
        {
            checkAnswerOrNextQuestion(0);
            return;
        }


        //if any are null we've got a problem
        if(edtInput == null)
        {
            //toast error
            return;
        }


        int iUserInput;

        if((!"".equals(edtInput.getText().toString())) && (!".".equals(edtInput.getText().toString())))
        {
            iUserInput = Integer.parseInt(edtInput.getText().toString());
        }
        else
        {
            //toast error
            return;
        }

        if(iUserInput == pj4g2.getAnswer())
        {
            checkAnswerOrNextQuestion(1);
            return;
        }
        else
        {
            checkAnswerOrNextQuestion(2);
            return;
        }

    }





    private void populateRandomValues()
    {
        pj4g2.makeRandomProblem();

        if((tvN1 == null) ||(tvN1 == null) ||(tvN1 == null))
        {
            Log.d("Error in:", "populateRandomValues");
            return;
        }

        tvN1.setText(pj4g2.getN1() + "");
        tvN2.setText(pj4g2.getN2() + "");
        tvOperator.setText(pj4g2.getOperator() + "");

    }




    //go back and make inputs integer only preferably
    public void SetOptionsInG2(boolean[] ba, double dMin, double dMax)
    {
        pj4g2.setBounds((int) dMin, (int) dMax);
        pj4g2.setBooleanArrayForOperators(ba.clone());
    }


}
