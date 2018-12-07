package com.team1.se2018.closetcloser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


public class BadFeedbackBSD extends BottomSheetDialogFragment {

    public static BadFeedbackBSD getInstance() {
        return new BadFeedbackBSD();
    }

    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bad_feedback_bsd, container, false);

        Button okbutton = v.findViewById(R.id.okbutton);
        CheckBox badcoord = v.findViewById(R.id.checkbox);
        CheckBox badcolor = v.findViewById(R.id.checkbox2);
        CheckBox badseason = v.findViewById(R.id.checkbox3);


        badcoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("bad coordination checked");
            }
        });

        badcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("bad color coordination checked");
            }
        });

        badseason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("bad for season checked");
            }
        });

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 1 clicked");
                dismiss();
            }
        });

        return v;

    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }


}
