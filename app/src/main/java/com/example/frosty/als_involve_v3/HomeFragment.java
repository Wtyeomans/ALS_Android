package com.example.frosty.als_involve_v3;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by frosty on 10/8/17.
 */

public class HomeFragment extends Fragment {

    String armResultsNum, leftTapsNum, rightTapsNum, toeTapsNum;

    //inflate the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity Context = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        TextView armRotationResults = (TextView) view.findViewById(R.id.arm_rotation_results_text_view);
        TextView fingerTapTestResults = (TextView) view.findViewById(R.id.finger_tap_test_result_text_view);
        TextView toeTapTestResults = (TextView) view.findViewById(R.id.toe_tap_results_text_view);



        armResultsNum = Context.getRotationResultsStr();
        leftTapsNum = Context.getLeftTapsStr();
        rightTapsNum = Context.getRightTapsStr();
        toeTapsNum = Context.getToeTapsStr();

        if(armResultsNum != null){
            armRotationResults.setText(armResultsNum);
        }

        if (leftTapsNum != null && rightTapsNum != null) {
            fingerTapTestResults.setText("L-" + leftTapsNum + " R-" + rightTapsNum);
        }

        if(toeTapsNum != null){
            toeTapTestResults.setText(toeTapsNum);
        }




        return view;
        //return inflater.inflate(R.layout.home_fragment, container, false);
    }

    
}
