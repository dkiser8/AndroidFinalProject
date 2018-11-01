package com.example.danielkiser.mathgamesv0;

import android.util.Log;

import java.util.Random;

public class Pojo4OOMath
{
    private Random r = new Random();
    private int iLowerBound = 0;
    private int iUpperBound = 9;
    private int nUno;
    private int nDos;
    private int nTres;
    private int nQuat;
    private int oUno = 0;
    private int oDos = 0;
    private int oTres = 0;
    private int iTarget;
    private double dRunningTotal;

    private double[] sevens;
    private double[] fives;
    private double[] threes;
    private boolean bTriedToDivideByZero = false;
    private boolean bTriedToRaise0toTheZero = false;

    public double getdRunningTotal() {
        return dRunningTotal;
    }

    public int getnUno() {
        return nUno;
    }

    public int getnDos() {
        return nDos;
    }

    public int getnTres() {
        return nTres;
    }

    public int getnQuat() {
        return nQuat;
    }

    public int getiTarget() {
        return iTarget;
    }

    public char getFirstOperator()
    {
        return helperForPrinting(oUno);
    }

    public char getSecondOperator()
    {
        return helperForPrinting(oDos);
    }

    public char getThirdOperator() {
        return helperForPrinting(oTres);
    }

    public void rotateOperatorUno()
    {
        oUno = incrementOperator(oUno);
    }

    public void rotateOperatorDos()
    {
        oDos = incrementOperator(oDos);
    }

    public void rotateOperatorTres()
    {
        oTres = incrementOperator(oTres);
    }


    private int incrementOperator(int spot)
    {
        if(spot == 4)
        {
            return 0;
        }

        if(spot > 4)
        {
            return -1;
        }

        return (spot + 1);
    }


    private int RandomNumber()
    {
        return RandomNumber(iLowerBound, iUpperBound);
    }

    private int RandomNumber(int lowerBound, int UpperBound)
    {
        return r.nextInt(iUpperBound) + iLowerBound;
    }


    public Pojo4OOMath()
    {
        this(0,9);
    }

    public Pojo4OOMath(int lowerBound, int upperBound)
    {
        this.iLowerBound = lowerBound;
        this.iUpperBound = upperBound;

        nUno = RandomNumber();
        nDos = RandomNumber();
        nTres = RandomNumber();
        nQuat = RandomNumber();

        iTarget = RandomNumber(0,6) * RandomNumber(0,6);
    }

    public boolean verifyCorrect(int n1, int o1, int n2, int o2, int n3, int o3, int n4)
    {
        return verifyCorrect(new int[] {n1, o1, n2, o2, n3, o3, n4});
    }

    public boolean verifyCorrect(int[] ia)
    {
        bTriedToRaise0toTheZero = false;
        bTriedToDivideByZero = false;

        Log.d("Drunning total", dRunningTotal + "");
        reduce7sTo5s(ia);
        Log.d("Drunning total", dRunningTotal + "");
        reduce5sTo3s();
        Log.d("Drunning total", dRunningTotal + "");

        return compareRunningTotalAndGoal(operate(threes[0],threes[2], (int) threes[1]));
    }

    public boolean verifyCorrect(int[] iaNumbersAndOperators, int[] iaParentheses)
    {
        Log.d("Doing the verify", "doing it:");
        Log.d("Printpretty iaNumAndOp:", arrayToStringPretty(iaNumbersAndOperators));
        Log.d("Printpretty iaParen:", arrayToStringPretty(iaParentheses));
        Log.d("Drunning total", dRunningTotal + "");

        iaParentheses = cleanUselessPairedParentheses(iaParentheses);

        int countOfUsedParentheses = 0;

        for (int i:iaParentheses)
        {
            if(i != 0)
            {
                countOfUsedParentheses++;
            }
        }

        if(countOfUsedParentheses == 0)
        {
            Log.d("drntot(used paren)", dRunningTotal + "");
            return verifyCorrect(iaNumbersAndOperators);
        }

        Log.d("Printpretty2 iaParen:", arrayToStringPretty(iaParentheses));



        //begin different solving
        bTriedToRaise0toTheZero = false;
        bTriedToDivideByZero = false;

        //populate sevens
        sevens = new double[7];
        for(int i = 0; i < 7; i++)
        {
            sevens[i] = iaNumbersAndOperators[i];
        }

        //different parentheses combos
        if((iaParentheses[0] == 1) && (iaParentheses[3] == -1) && (iaParentheses[4] == 1) && (iaParentheses[7] == -1))
        {
            //two and two
            //(a * b) * (c * d)
            Log.d("Drunning totalZ#", dRunningTotal + "");
            sevensFirstOperator();
            Log.d("Drunning totalZ$", dRunningTotal + "");
            fivesSecondOperator();
            Log.d("Drunning totalZ%", dRunningTotal + "");
            return compareRunningTotalAndGoal(operate(threes[0],threes[2], (int) threes[1]));
        }

        Log.d("Skipped it :(", arrayToStringPretty(iaParentheses));

        if((iaParentheses[0] == 1) && (iaParentheses[3] == -1))
        {
            //two and normal
            //(a * b) * c * d
            sevensFirstOperator();
            reduce5sTo3s();
            return compareRunningTotalAndGoal(operate(threes[0],threes[2], (int) threes[1]));
        }

        if((iaParentheses[2] == 1) && (iaParentheses[5] == -1))
        {
            //two and normal
            //a * ( b * c ) * d
            sevensSecondOperator();
            reduce5sTo3s();
            return compareRunningTotalAndGoal(operate(threes[0],threes[2], (int) threes[1]));
        }

        if((iaParentheses[4] == 1) && (iaParentheses[7] == -1))
        {
            //two and normal
            //a * b * ( c * d )
            sevensThirdOperator();
            reduce5sTo3s();
            return compareRunningTotalAndGoal(operate(threes[0],threes[2], (int) threes[1]));
        }

        if((iaParentheses[2] == 1) && (iaParentheses[7] == -1))
        {
            //three and head
            //a * ( b * c * d )
            fives = new double[]{sevens[2], sevens[3], sevens[4], sevens[5], sevens[6]};
            reduce5sTo3s();

            return compareRunningTotalAndGoal(operate(
                    sevens[0],
                    operate(threes[0],threes[2], (int) threes[1]),
                    (int) sevens[1]
            ));
        }

        if((iaParentheses[0] == 1) && (iaParentheses[5] == -1))
        {
            //three and tail
            // ( a * b * c ) * d
            fives = new double[]{sevens[0], sevens[1], sevens[2], sevens[3], sevens[4]};
            reduce5sTo3s();

            return compareRunningTotalAndGoal(operate(
                    operate(threes[0],threes[2], (int) threes[1]),
                    sevens[6],
                    (int) sevens[5]
            ));
        }

//        throw new Exception("Error calculating this set of parentheses");
        return false;
    }

    private int[] cleanUselessPairedParentheses(int[] iaParentheses)
    {
        for(int i = 0; i < iaParentheses.length - 1; i += 2)
        {
            if((iaParentheses[i] == 1) && (iaParentheses[i+1] == -1))
            {
                iaParentheses[i] = 0;
                iaParentheses[i+1] = 0;
            }
        }

        if(
                (iaParentheses[0] == 1) &&
                    (iaParentheses[1] == 0) &&
                    (iaParentheses[2] == 0) &&
                    (iaParentheses[3] == 0) &&
                    (iaParentheses[4] == 0) &&
                    (iaParentheses[5] == 0) &&
                    (iaParentheses[6] == 0) &&
                (iaParentheses[iaParentheses.length-1] == -1))
        {
            iaParentheses[0] = 0;
            iaParentheses[iaParentheses.length-1] = 0;
        }

        return iaParentheses;
    }

    //helper method
    private void reduce7sTo5s(int[] ia)
    {
        sevens = new double[7];
        for(int i = 0; i < 7; i++)
        {
            sevens[i] = ia[i];
        }

        //exponents >= 4
        //mult/divide >= 2
        //add/subtract >= 0
        for(int iOperator = 4; iOperator >= 0; iOperator = iOperator - 2)
        {
            if(ia[1] >= iOperator)
            {
                sevensFirstOperator();
                return;
            }
            if(ia[3] >= iOperator)
            {
                sevensSecondOperator();
                return;
            }
            if(ia[5] >= iOperator)
            {
                sevensThirdOperator();
                return;
            }
        }
    }

    //helper method
    private void reduce5sTo3s()
    {
        //exponents >= 4
        //mult/divide >= 2
        //add/subtract >= 0
        for(int iOperator = 4; iOperator >= 0; iOperator = iOperator - 2)
        {
            if(fives[1] >= iOperator)
            {
                fivesFirstOperator();
                return;
            }
            if(fives[3] >= iOperator)
            {
                fivesSecondOperator();
                return;
            }
        }
    }




    private void sevensFirstOperator()
    {
        /*
        SevensToFives: first operator
        a + b + c + d
        to
        (a+b) + c + d
        */

        fives = new double[] {operate(sevens[0], sevens[2], (int) sevens[1]), sevens[3], sevens[4], sevens[5], sevens[6]};
        return;
    }

    private void sevensSecondOperator()
    {
        /*
        SevensToFives: second operator
        a + b + c + d
        to
        a + (b + c) + d
        */

        fives = new double[] {sevens[0], sevens[1], operate(sevens[2], sevens[4], (int) sevens[3]), sevens[5], sevens[6]};
        return;
    }

    private void sevensThirdOperator()
    {
        /*
        SevensToFives: third operator
        a + b + c + d
        to
        a + b + (c + d)
        */

        fives = new double[] {sevens[0], sevens[1], sevens[2], sevens[3], operate(sevens[4], sevens[6], (int) sevens[5])};
        return;
    }

    private void fivesFirstOperator()
    {
        /*
        FivesToThrees: first operator
        a + b + c
        to
        (a+b) + c
        */
        threes = new double[] {operate(fives[0], fives[2], (int) fives[1]), fives[3], fives[4]};
        return;
    }

    private void fivesSecondOperator()
    {
        /*
        FivesToThrees: second operator
        a + b + c
        to
        a + (b + c)
        */

        threes = new double[] {fives[0], fives[1], operate(fives[2], fives[4], (int) fives[3])};
        return;
    }



    private double operate(double first, double second, int operation)
    {
        switch (operation)
        {
            case 0: //add
                return first + second;
            case 1: //subtract
                return first - second;
            case 2: //multiply
                return first * second;
            case 3: //divide
                if(second == 0)
                {
                    bTriedToDivideByZero = true;
                    break;
                }
                return first / second;
            case 4: //exponent
                if (second == 0)      //special consideration for 0 ^ 0
                {
                    bTriedToRaise0toTheZero = true;
                    return 1.0;
                }
                return Math.pow(first, second);
            default:
//                System.err.println("Error in operate");
                break;
        }

        return -1000000000;
    }

    private boolean compareRunningTotalAndGoal(double runningTotal)
    {
        Log.d("compDrunning total", dRunningTotal + "");


        dRunningTotal = runningTotal;

        if(Math.abs(runningTotal - this.iTarget) < 0.01)
        {
            return true;
        }

        return false;
    }


    public static char helperForPrinting(int i)
    {
        switch (i)
        {
            case 0: //add
                return '+';
            case 1: //subtract
                return '-';
            case 2: //multiply
                return '*';
            case 3: //divide
                return '/';
            case 4: //exponent
                return '^';
            default:
                //System.err.println("Error in printchar");
                break;
        }

        //error
        return '?';
    }

    public static String arrayToStringPretty(int[] ia)
    {
        String sToReturn = "";

        for (int i = 0; i < ia.length; i++)
        {
            sToReturn += ia[i] + " ";
        }

        return sToReturn;
    }


    public String result()
    {
        if(bTriedToDivideByZero)
        {
            return "!Div0";
        }

        if(bTriedToRaise0toTheZero)
        {
            return "Err:0^0";
        }

        return "" + dRunningTotal;
    }

    public int[] getRandomParens()
    {
        int[] iaToReturn = new int[8];

        //two and two OR three head/tail
        switch (r.nextInt(2))
        {
            case 0:
                iaToReturn[0] = 1;
                iaToReturn[3] = -1;
                iaToReturn[4] = 1;
                iaToReturn[7] = -1;
                break;
            case 1:
                iaToReturn[0] = 1;
                iaToReturn[5] = -1;
                break;
            case 2:
                iaToReturn[2] = 1;
                iaToReturn[7] = -1;
            default:
                    return new int[] {-1};
        }

        return iaToReturn;
    }

    public String getiMaxTarget(int[] iaParen)
    {
        Pojo4OOMath tempToTest = new Pojo4OOMath();
        Double dMaxResultToReturn = -1000000.0;

        // + * /
        tempToTest.verifyCorrect(new int[]{
                nUno, 0, nDos, 2, nTres, 3, nQuat
        }, iaParen.clone());

        if(!tempToTest.bTriedToDivideByZero)
        {
            if(tempToTest.dRunningTotal > dMaxResultToReturn)
            {
                dMaxResultToReturn = tempToTest.dRunningTotal;
            }
        }


        // + / *
        tempToTest.verifyCorrect(new int[]{
                nUno, 0, nDos, 3, nTres, 2, nQuat
        }, iaParen.clone());

        if(!tempToTest.bTriedToDivideByZero)
        {
            if(tempToTest.dRunningTotal > dMaxResultToReturn)
            {
                dMaxResultToReturn = tempToTest.dRunningTotal;
            }
        }


        // * + /
        tempToTest.verifyCorrect(new int[]{
                nUno, 2, nDos, 0, nTres, 3, nQuat
        }, iaParen.clone());

        if(!tempToTest.bTriedToDivideByZero)
        {
            if(tempToTest.dRunningTotal > dMaxResultToReturn)
            {
                dMaxResultToReturn = tempToTest.dRunningTotal;
            }
        }


        // * / +
        tempToTest.verifyCorrect(new int[]{
                nUno, 2, nDos, 3, nTres, 0, nQuat
        }, iaParen.clone());

        if(!tempToTest.bTriedToDivideByZero)
        {
            if(tempToTest.dRunningTotal > dMaxResultToReturn)
            {
                dMaxResultToReturn = tempToTest.dRunningTotal;
            }
        }


        // / + *
        tempToTest.verifyCorrect(new int[]{
                nUno, 3, nDos, 0, nTres, 2, nQuat
        }, iaParen.clone());

        if(!tempToTest.bTriedToDivideByZero)
        {
            if(tempToTest.dRunningTotal > dMaxResultToReturn)
            {
                dMaxResultToReturn = tempToTest.dRunningTotal;
            }
        }


        // / * +
        tempToTest.verifyCorrect(new int[]{
                nUno, 3, nDos, 2, nTres, 0, nQuat
        }, iaParen.clone());

        if(!tempToTest.bTriedToDivideByZero)
        {
            if(tempToTest.dRunningTotal > dMaxResultToReturn)
            {
                dMaxResultToReturn = tempToTest.dRunningTotal;
            }
        }



        return dMaxResultToReturn + "";
    }

}
