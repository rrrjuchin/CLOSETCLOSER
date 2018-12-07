package com.team1.se2018.closetcloser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DailyRecommendationActivity extends Fragment
        implements DRChildFragment.OnFragmentInteractionListener,
        DRChildFragment2.OnFragmentInteractionListener{

    private TextView mTextView;
    private OnFragmentInteractionListener mListener;

    final Fragment childFragment = new DRChildFragment();
    final Fragment childFragment2 = new DRChildFragment2();
    final Fragment childFragment3 = new DRChildFragment3();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_daily_recommendation, container, false);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.drchild_fragment_container, childFragment).commit();

        Button btn_rec3 = (Button) rootView.findViewById(R.id.btn_rec3);
        btn_rec3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"눌렷다!!!!!!!!",Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        Button btn_rec1 = (Button) view.findViewById(R.id.btn_rec1);
        btn_rec1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.drchild_fragment_container, childFragment).commit();

            }
        });

        Button btn_rec2 = (Button) view.findViewById(R.id.btn_rec2);
        btn_rec2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.drchild_fragment_container, childFragment2).commit();
            }
        });

        Button btn_rec3 = (Button) view.findViewById(R.id.btn_rec3);
        btn_rec3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.drchild_fragment_container, childFragment3).commit();
            }
        });

        // create bottom sheet
        Button deleteBtn = (Button) view.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadFeedbackBSD badFeedbackBSD = BadFeedbackBSD.getInstance();
                badFeedbackBSD.show(getActivity().getSupportFragmentManager(), "bottomSheet");
            }
        });

        FrameLayout feedbackBtn = (FrameLayout) view.findViewById(R.id.drchild_fragment_container);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodFeedbackBSD goodFeedbackBSD = GoodFeedbackBSD.getInstance();
                goodFeedbackBSD.show(getActivity().getSupportFragmentManager(), "bottomSheet");
            }
        });

        FloatingActionButton refreshBtn = (FloatingActionButton) view.findViewById(R.id.btn_refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get new recommendation
                
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void messageFromChildFragment(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }
}