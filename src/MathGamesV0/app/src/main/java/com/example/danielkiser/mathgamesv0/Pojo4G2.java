package com.example.danielkiser.mathgamesv0;

import java.util.ArrayList;
import java.util.Random;

public class Pojo4G2
{
    //inclusive bounds
    private int iLowerBound;
    private int iUpperBound;
    private boolean[] baOperatorOptions = new boolean[4];    //Add,sub,mult,div

    private int iN1, iN2, iOperator, iAnswer;

    private Random r = new Random();
    private boolean bDividedByZero;


    public int getN1() {
        return iN1;
    }

    public int getN2()
    {
        return iN2;
    }

    public int getAnswer()
    {
        return iAnswer;
    }

    public char getOperator()
    {
        return Pojo4OOMath.helperForPrinting(iOperator);
    }

    public boolean getDividedByZero()
    {
        return bDividedByZero;
    }

    public Pojo4G2()
    {
        setBounds(-12,12);
        baOperatorOptions = new boolean[]{true, true, true, true};
//        makeRandomProblem();
    }

    public void setBooleanArrayForOperators(boolean[] ba)
    {
        this.baOperatorOptions = ba.clone();
    }

    public void setBounds(int iLowerBound, int iUpperBound)
    {
        this.iLowerBound = iLowerBound;
        this.iUpperBound = iUpperBound;
    }

    public void makeRandomProblem()
    {
        bDividedByZero = false;
        iN1 = pickRandomInt();
        iN2 = pickRandomInt();
        iOperator = pickRandomOperator();

        switch (iOperator)
        {
            case 0:
                iAnswer = iN1 + iN2;
                break;
            case 1:
                iAnswer = iN1 - iN2;
                break;
            case 2:
                iAnswer = iN1 * iN2;
                break;
            case 3:
                if(iN2 == 0)
                {
                    bDividedByZero = true;
                    break;
                }
                iAnswer = iN1;
                iN1 = iN1 * iN2;
                break;
            default:
                //problem
                iAnswer = -10000000;
                break;
        }
    }


    private int pickRandomOperator()
    {
        ArrayList<Integer> ali = new ArrayList<>();

        for(int i = 0; i < 4; i++)
        {
            if(baOperatorOptions[i])
            {
                ali.add(i);
            }
        }

        if(ali.isEmpty())
        {
            //error checking
            //got a problem none were selected
            return -1;
        }

        return ali.get(r.nextInt(ali.size()));
    }

    private int pickRandomInt()
    {
        return iUpperBound - r.nextInt(iUpperBound - iLowerBound + 1);
    }
}
