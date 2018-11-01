package com.example.danielkiser.mathgamesv0;


import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOOMaxDrag extends Fragment
{
    private Button btnEqualsSign;
    private TextView tvNum1, tvNum2, tvNum3, tvNum4, tvResult, tvTarget, tvAdd, tvSub, tvMult, tvDiv, tvCaret, tvOp1, tvOp2, tvOp3;

    private int[] ia4ParenSwitcherHasViewIds = {
        R.id.tv4N1ParenA,R.id.tv4N1ParenB,
        R.id.tv4N2ParenA,R.id.tv4N2ParenB,
        R.id.tv4N3ParenA,R.id.tv4N3ParenB,
        R.id.tv4N4ParenA,R.id.tv4N4ParenB,
};
    private View.OnClickListener clickListenerParenSwitcher;
    private int[] iaParen = {1,-1,1,-1,1,-1,1,-1};

    private View root;
    private Pojo4OOMath pjThisGame;
    private View.OnLongClickListener longClickListener;
    private View.OnDragListener dragListener;
    private View.OnClickListener clickListenerEqualsSign;
    private int[] iaOperators = {-1, -1, -1};
    private int[] iaNumbers = {-1, -1, -1, -1};
    private int iCurrentlyDraggedViewId = -1;
    private int iCurrentlyEnteredViewId = -1;

    public FragmentOOMaxDrag()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        pjThisGame = new Pojo4OOMath();
        makeListeners();

        // Inflate the layout for this fragment
//        return root = inflater.inflate(R.layout.fragment_fragment_ootarget_drag, container, false);
        return root = inflater.inflate(R.layout.fragment_fragment_oomax_drag_paren, container, false);

    }

    private void makeListeners()
    {
        longClickListener = new View.OnLongClickListener()
        {

            @Override
            public boolean onLongClick(View v)
            {
                // Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.

                // Create a new ClipData.Item from the ImageView object's tag
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This will create a new ClipDescription object within the
                // ClipData, and set its MIME type entry to "text/plain"
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

                // Instantiates the drag shadow builder.
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);


                iCurrentlyDraggedViewId = v.getId();
//                if(v.getId() == button.getId())
//                {
//                    bCurrentlyDraggedItemIsButton = true;
//                }
//                else
//                {
//                    bCurrentlyDraggedItemIsButton = false;
//                }


                // Starts the drag
                v.startDrag(data//data to be dragged
                        , shadowBuilder //drag shadow
                        , v//local data about the drag and drop operation
                        , 0//no needed flags
                );

                //Set view visibility to INVISIBLE as we are going to drag the view
                v.setVisibility(View.INVISIBLE);
                return true;

//                return false;
            }
        };

        dragListener = new View.OnDragListener()
        {

            @Override
            public boolean onDrag(View v, DragEvent event)
            {
                // Defines a variable to store the action type for the incoming event
                int action = event.getAction();
                // Handles each of the expected events
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Determines if this View can accept the dragged data
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            // if you want to apply color when drag started to your view you can uncomment below lines
                            // to give any color tint to the View to indicate that it can accept
                            // data.


//                      view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                            // Invalidate the view to force a redraw in the new tint
//                      view.invalidate();

                            // returns true to indicate that the View can accept the dragged data.
                            return true;

                        }

                        // Returns false. During the current drag and drop operation, this View will
                        // not receive events again until ACTION_DRAG_ENDED is sent.
                        return false;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                        // Return true; the return value is ignored.

                        iCurrentlyEnteredViewId = v.getId();

                        //validate drop
                        if(!validDrop())
                        {
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            // Invalidate the view to force a redraw in the new tint
                            v.invalidate();

                            return true;
                        }


                        v.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate();

                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        // Ignore the event
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                        //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                        //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                        v.getBackground().clearColorFilter();
                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate();

                        return true;
                    case DragEvent.ACTION_DROP:
                        if(!validDrop())
                        {
                            return false;
                        }

                        if(isOperatorTV(iCurrentlyDraggedViewId) && isOperatorLL(iCurrentlyEnteredViewId))
                        {
                            int iOperatorChoice;

                            //set operator
                            switch (iCurrentlyDraggedViewId)
                            {
                                case R.id.tvOrderofOperationsDragTargetPlusSign:
                                    iOperatorChoice = 0;
                                    break;
                                case R.id.tvOrderofOperationsDragTargetMinusSign:
                                    iOperatorChoice = 1;
                                    break;
                                case R.id.tvOrderofOperationsDragTargetMultSign:
                                    iOperatorChoice = 2;
                                    break;
                                case R.id.tvOrderofOperationsDragTargetDivideSign:
                                    iOperatorChoice = 3;
                                    break;
                                case R.id.tvOrderofOperationsDragTargetCaretSign:
                                    iOperatorChoice = 4;
                                    break;
                                default:
                                    Log.d("Error picking operator", "In FragmentOOTargetDrag");
                                    iOperatorChoice = -1;
                                    break;
                            }


                            //set text & set logic
                            switch (iCurrentlyEnteredViewId)
                            {
                                case R.id.LinearLayoutOrderofOperationsTargetOperator1:
                                    iaOperators[0] = iOperatorChoice;
                                    tvOp1.setText("" + Pojo4OOMath.helperForPrinting(iOperatorChoice));
                                    break;
                                case R.id.LinearLayoutOrderofOperationsTargetOperator2:
                                    iaOperators[1] = iOperatorChoice;
                                    tvOp2.setText("" + Pojo4OOMath.helperForPrinting(iOperatorChoice));
                                    break;
                                case R.id.LinearLayoutOrderofOperationsTargetOperator3:
                                    iaOperators[2] = iOperatorChoice;
                                    tvOp3.setText("" + Pojo4OOMath.helperForPrinting(iOperatorChoice));
                                    break;
                                default:
                                    Log.d("Error picking operator", "In FragmentOOTargetDrag");
                                    break;
                            }

                            verifyAllElementsCompleteForGreenEqualsSign();

                            return false;
                        }


                        // Gets the item containing the dragged data
                        ClipData.Item item = event.getClipData().getItemAt(0);



                        // Gets the text data from the item.
                        String dragData = item.getText().toString();

                        // Displays a message containing the dragged data.
//                        Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_LONG).show();

                        // Turns off any color tints
                        v.getBackground().clearColorFilter();

                        // Invalidates the view to force a redraw
                        v.invalidate();

                        View v2 = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) v2.getParent();
                        owner.removeView(v2);//remove the dragged view
                        LinearLayout container = (LinearLayout) v;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                        container.addView(v2);//Add the dragged view
                        v2.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE


//                        if((TextView) container.findViewById(R.id.tvOOTargetDragN01) == null)
//                        {
//                            Log.d("Dropped into ", "View doesn't contain number 1");
//                        }
//                        else
//                        {
//                            Log.d("Dropped into ", "View does contain number 1");
//                        }

                        verifyAllElementsCompleteForGreenEqualsSign();


                        // Returns true. DragEvent.getResult() will return true.
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        ((View) event.getLocalState()).setVisibility(View.VISIBLE);
//                        bAllowedDrop = true;
//                        bCurrentlyDraggedItemIsButton = false;


                        // Turns off any color tinting
                        v.getBackground().clearColorFilter();

                        // Invalidates the view to force a redraw
                        v.invalidate();

                        // Does a getResult(), and displays what happened.
//                        if (event.getResult()) {
//                            Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
//                        } else
//                            Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();


                        // returns true; the value is ignored.
                        return true;

                    // An unknown action type was received.
                    default:
                        Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                        break;
                }
                return false;
            }
        };

        clickListenerEqualsSign = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkTotal();
            }
        };


//        clickListenerParenSwitcher = new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                toggleParen(v.getId());
//            }
//        };


    }


    private boolean validDrop()
    {
        //if dragged is number, entered is number space
        if(isNumberTV(iCurrentlyDraggedViewId) && isNumberLL(iCurrentlyEnteredViewId))
        {
            return true;
        }

        //if dragged is number, entered is number space
        //likely extraneous
//        if(isNumberTV(iCurrentlyDraggedViewId) && !isNumberLL(iCurrentlyEnteredViewId))
//        {
//            return false;
//        }

        //if dragged is operator and space is for operator
        if(isOperatorTV(iCurrentlyDraggedViewId) && isOperatorLL(iCurrentlyEnteredViewId))
        {
            return true;
        }


        return false;
    }


    private boolean isNumberTV(int id)
    {
        return (id == R.id.tvOOTargetDragN01) || (id == R.id.tvOOTargetDragN02) || (id == R.id.tvOOTargetDragN03) || (id == R.id.tvOOTargetDragN04);
    }

    private boolean isNumberLL(int id)
    {
        return (id == R.id.LinearLayoutOrderofOperationsTargetNumber1)
                || (id == R.id.LinearLayoutOrderofOperationsTargetNumber2)
                || (id == R.id.LinearLayoutOrderofOperationsTargetNumber3)
                || (id == R.id.LinearLayoutOrderofOperationsTargetNumber4);
    }

    private boolean isOperatorLL(int id)
    {
        return (id == R.id.LinearLayoutOrderofOperationsTargetOperator1)
                || (id == R.id.LinearLayoutOrderofOperationsTargetOperator2)
                || (id == R.id.LinearLayoutOrderofOperationsTargetOperator3);
    }

    private boolean isOperatorTV(int id)
    {
        return (id == R.id.tvOrderofOperationsDragTargetPlusSign)
                || (id == R.id.tvOrderofOperationsDragTargetMinusSign)
                || (id == R.id.tvOrderofOperationsDragTargetMultSign)
                || (id == R.id.tvOrderofOperationsDragTargetDivideSign)
                || (id == R.id.tvOrderofOperationsDragTargetCaretSign);
    }


    @Override
    public void onResume()
    {
        super.onResume();

        findViews();
        implementEvents();
        populateRandomValues();
    }


    //Find all views and set Tag to all draggable views
    private void findViews()
    {
        btnEqualsSign = (Button) root.findViewById(R.id.btnOOTargetDragEquals);

        tvNum1 = (TextView) root.findViewById(R.id.tvOOTargetDragN01);
        tvNum2 = (TextView) root.findViewById(R.id.tvOOTargetDragN02);
        tvNum3 = (TextView) root.findViewById(R.id.tvOOTargetDragN03);
        tvNum4 = (TextView) root.findViewById(R.id.tvOOTargetDragN04);
        tvResult = (TextView) root.findViewById(R.id.tvOOTargetDragResult);
        tvTarget = (TextView) root.findViewById(R.id.tvOOTargetDragTarget);

        tvAdd = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetPlusSign);
//        tvSub = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetMinusSign);
        tvMult = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetMultSign);
        tvDiv = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetDivideSign);
//        tvCaret = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetCaretSign);

        tvOp1 = (TextView) root.findViewById(R.id.tvOOTargetDragOp1);
        tvOp2 = (TextView) root.findViewById(R.id.tvOOTargetDragOp2);
        tvOp3 = (TextView) root.findViewById(R.id.tvOOTargetDragOp3);

        //set tags
//        tvNum1.setTag("tvNum1");
//        tvNum2.setTag("tvNum1");
//        tvNum3 .setTag("tvNum1");
//        tvNum4 .setTag("tvNum1");//= (TextView) root.findViewById(R.id.tvOOTargetDragN04);
//        tvResult .setTag("tvNum1");//= (TextView) root.findViewById(R.id.tvOOTargetDragResult);
//        tvTarget.setTag("tvNum1");// = (TextView) root.findViewById(R.id.tvOOTargetDragTarget);

        tvAdd.setTag("tvNum1");// = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetPlusSign);
//        tvSub.setTag("tvNum1");// = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetMinusSign);
        tvMult.setTag("tvNum1");// = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetMultSign);
        tvDiv.setTag("tvNum1");// = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetDivideSign);
//        tvCaret.setTag("tvNum1");// = (TextView) root.findViewById(R.id.tvOrderofOperationsDragTargetCaretSign);

    }


    //Implement long click and drag listener (and click for button)
    private void implementEvents()
    {
//        tvNum1.setOnLongClickListener(longClickListener);
//        tvNum2.setOnLongClickListener(longClickListener);
//        tvNum3.setOnLongClickListener(longClickListener);
//        tvNum4.setOnLongClickListener(longClickListener);

        tvAdd.setOnLongClickListener(longClickListener);
//        tvSub.setOnLongClickListener(longClickListener);
        tvMult.setOnLongClickListener(longClickListener);
        tvDiv.setOnLongClickListener(longClickListener);
//        tvCaret.setOnLongClickListener(longClickListener);

//        tvTarget.setOnClickListener(clickListenerEqualsSign);


        //add or remove any layout view that you don't want to accept dragged view
//        root.findViewById(R.id.LinearLayoutOrderofOperationsTargetNumber1).setOnDragListener(dragListener);
//        root.findViewById(R.id.LinearLayoutOrderofOperationsTargetNumber2).setOnDragListener(dragListener);
//        root.findViewById(R.id.LinearLayoutOrderofOperationsTargetNumber3).setOnDragListener(dragListener);
//        root.findViewById(R.id.LinearLayoutOrderofOperationsTargetNumber4).setOnDragListener(dragListener);

        root.findViewById(R.id.LinearLayoutOrderofOperationsTargetOperator1).setOnDragListener(dragListener);
        root.findViewById(R.id.LinearLayoutOrderofOperationsTargetOperator2).setOnDragListener(dragListener);
        root.findViewById(R.id.LinearLayoutOrderofOperationsTargetOperator3).setOnDragListener(dragListener);


        //click for button
        btnEqualsSign.setOnClickListener(clickListenerEqualsSign);


        //paren switcher
//        for (int iViewId:ia4ParenSwitcherHasViewIds)
//        {
//            root.findViewById(iViewId).setOnClickListener(clickListenerParenSwitcher);
//        }

    }





    private void populateRandomValues()
    {
        tvNum1.setText("     " + pjThisGame.getnUno() + "     ");
        tvNum2.setText("     " + pjThisGame.getnDos() + "     ");
        tvNum3.setText("     " + pjThisGame.getnTres() + "     ");
        tvNum4.setText("     " + pjThisGame.getnQuat() + "     ");

        iaParen = pjThisGame.getRandomParens().clone();

        for(int i = 0; i < 8; i++)
        {
            ((TextView) root.findViewById(ia4ParenSwitcherHasViewIds[i])).setText(parenTextHelper(iaParen[i]));
        }

        tvTarget.setText(pjThisGame.getiMaxTarget(iaParen.clone()));
        tvResult.setText("");

    }

    private void checkTotal()
    {
        if(!verifyAllElementsCompleteForGreenEqualsSign())
        {
            tvResult.setText("MissingData");
            return;
        }

        int[] iaToCheckSubmission = {
                iaNumbers[0],
                iaOperators[0],
                iaNumbers[1],
                iaOperators[1],
                iaNumbers[2],
                iaOperators[2],
                iaNumbers[3]
        };
//
////        Log.d("IA of Submission:", Pojo4OOMath.arrayToStringPretty(iaToCheckSubmission));
//
        for(int i = 0; i < iaToCheckSubmission.length; i++)
        {
            if(iaToCheckSubmission[i] == -1)
            {
                tvResult.setText("MissingData");
                return;
            }
        }

        //copy iaParen

        if(pjThisGame.verifyCorrect(iaToCheckSubmission, iaParen.clone()))
        {
            tvResult.setText("WINNER");
            return;
        }

        tvResult.setText("" + pjThisGame.result());
        Log.d("Result: " , pjThisGame.result());
    }

    private void fillNumbers()
    {
        iaNumbers = new int[] {
                pjThisGame.getnUno(),
                pjThisGame.getnDos(),
                pjThisGame.getnTres(),
                pjThisGame.getnQuat()
        };

    }



    private void toggleParen(int vId)
    {
        int iIndexOfId = -1;

        for(int i = 0; i < ia4ParenSwitcherHasViewIds.length; i++)
        {
            if(vId == ia4ParenSwitcherHasViewIds[i])
            {
                iIndexOfId = i;
                break;
            }
        }


        if(iIndexOfId < 0)
        {
            Log.d("Problem:","In toggleParen");
            return;
        }

        if(iIndexOfId > 7)
        {
            iIndexOfId -= 8;
        }

        int iIndexOfParenArrayToToggle = 0;

        if(iIndexOfId > 3)
        {
            iIndexOfParenArrayToToggle++;
        }

        iIndexOfParenArrayToToggle += (iIndexOfId % 4) * 2;
        //totoggle = totoggle + (iidnexofid % 4)
        // 0        = 0         + 0
        // 0        = 0         + 2
        // 0        =   0       + 4
        //

        toggleParenArray(iIndexOfParenArrayToToggle, ia4ParenSwitcherHasViewIds[iIndexOfId]);
    }

    private void toggleParenArray(int iIndexToToggle, int iViewId)
    {
        //+1 or -1
        //even goes to 1
        //odd goes to -1
        //tells what could be expected in that slot (0 or -1/1)
        int iCouldBe = (iIndexToToggle % 2) * -2 + 1;

//        Log.d("ParenArray From:","" + Pojo4OOMath.arrayToStringPretty(iaParen));

        if(iaParen[iIndexToToggle] == 0)
        {
            //turn on paren
            iaParen[iIndexToToggle] = iCouldBe;
        }
        else
        {
            //turn off paren
            iaParen[iIndexToToggle] = 0;
        }

//        Log.d("ParenArray To:","" + Pojo4OOMath.arrayToStringPretty(iaParen));


        //update text accordingly
//        Log.d("could not find viewId:", iViewId + "");
        ((TextView) root.findViewById(iViewId)).setText(parenTextHelper(iaParen[iIndexToToggle]));

        verifyAllElementsCompleteForGreenEqualsSign();
    }

    private int parenTextHelper(int iIntOfNeg1ZeroOrPos1)
    {
        switch (iIntOfNeg1ZeroOrPos1)
        {
            case -1:
                return R.string.closeParen;
            case 0:
                return R.string.space;
            case 1:
                return R.string.openParen;
            default:
                Log.d("error", "in parentTextHelper");
                return R.string.btnerror;
        }
    }


    private boolean verifyAllElementsCompleteForGreenEqualsSign()
    {
//        Log.d("NumberArray To:","" + Pojo4OOMath.arrayToStringPretty(iaNumbers));
//        Log.d("Operator To:","" + Pojo4OOMath.arrayToStringPretty(iaOperators));
//        Log.d("ParenArray To:","" + Pojo4OOMath.arrayToStringPretty(iaParen));


//        if(!checkParentheses())
//        {
////            ((LinearLayout)root.findViewById(R.id.LL4EqualsSignButton)).setBackgroundColor(R.color.Red);
//            ((LinearLayout)root.findViewById(R.id.LL4EqualsSignButton)).setBackgroundResource(R.color.Red);
//            return false;
//        }

        fillNumbers();

        int[] iaToCheckSubmission = {
                iaNumbers[0],
                iaOperators[0],
                iaNumbers[1],
                iaOperators[1],
                iaNumbers[2],
                iaOperators[2],
                iaNumbers[3]
        };

        Log.d("ParenArray To:","" + Pojo4OOMath.arrayToStringPretty(iaParen));
        Log.d("IA of Submission:", Pojo4OOMath.arrayToStringPretty(iaToCheckSubmission));

        for (int anIaToCheckSubmission : iaToCheckSubmission) {
            if (anIaToCheckSubmission == -1) {
                ((LinearLayout) root.findViewById(R.id.LL4EqualsSignButton)).setBackgroundResource(R.color.Red);
                return false;
            }
        }

        ((LinearLayout)root.findViewById(R.id.LL4EqualsSignButton)).setBackgroundResource(R.color.LightGreen);
        return true;
    }

//    private boolean checkParentheses()
//    {
//        int iSum = 0;
//
//        for (int anIaParen : iaParen) {
//            iSum += anIaParen;
//
//            if (iSum < 0) {
//                return false;
//            }
//        }
//
//        return iSum == 0;
//    }

}
