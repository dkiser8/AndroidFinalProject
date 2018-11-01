package com.example.danielkiser.mathgamesv0;


import android.os.Bundle;
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
public class FragmentGame3 extends Fragment
{
    private View root;
    private TextView tvNAddTo, tvNMultTo;
    private Button btnSubmit;
    private Pojo4G2 pj4g2;
    private TextInputEditText edtInput1, edtInput2;

    //states:
    //state 0: new question, submit button
    //state 1: correct, next question
    //state 2: incorrect
    private int iButtonState = 0;



    public FragmentGame3()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        pj4g2 = new Pojo4G2();
//        pj4g2.setBounds(1,12);
        pj4g2.setBooleanArrayForOperators(new boolean[]{false, false, true, false});

        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_fragment_game3, container, false);
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
        tvNAddTo = (TextView) root.findViewById(R.id.tvG3NAddTo);
        tvNMultTo = (TextView) root.findViewById(R.id.tvG3NMultTo);

        btnSubmit = (Button) root.findViewById(R.id.buttonG3Submit);

        edtInput1 = (TextInputEditText) root.findViewById(R.id.inputG3N1);
        edtInput2 = (TextInputEditText) root.findViewById(R.id.inputG3N2);
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
    }


    private void populateRandomValues()
    {
        pj4g2.makeRandomProblem();

        if((tvNAddTo == null) ||(tvNMultTo == null))
        {
            Log.d("Error in:", "populateRandomValues");
            return;
        }

        tvNAddTo.setText((pj4g2.getN1() + pj4g2.getN2()) + "");
        tvNMultTo.setText(pj4g2.getAnswer() + "");
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
                Log.d("CorrectAnswerShouldBe:", ""+ pj4g2.getAnswer());
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
        if(edtInput1 == null)
        {
            //toast error
            return;
        }

        //Get first user input
        int iUserInput1;
        if((!"".equals(edtInput1.getText().toString())) && (!".".equals(edtInput1.getText().toString())))
        {
            iUserInput1 = Integer.parseInt(edtInput1.getText().toString());
        }
        else
        {
            //toast error
            return;
        }

        //Get second user input
        int iUserInput2;
        if((!"".equals(edtInput2.getText().toString())) && (!".".equals(edtInput2.getText().toString())))
        {
            iUserInput2 = Integer.parseInt(edtInput2.getText().toString());
        }
        else
        {
            //toast error
            return;
        }

        if(
                ((iUserInput1 == pj4g2.getN1()) && (iUserInput2 == pj4g2.getN2())) ||
                ((iUserInput2 == pj4g2.getN1()) && (iUserInput1 == pj4g2.getN2()))
                )
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
}
