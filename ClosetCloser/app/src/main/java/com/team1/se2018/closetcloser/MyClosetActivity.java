package com.team1.se2018.closetcloser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyClosetActivity extends Fragment
        implements MCChildFragmentOuter.OnFragmentInteractionListener, MCChildFragment2.OnFragmentInteractionListener{


    private OnFragmentInteractionListener mListener;

    final Fragment childFragment = new MCChildFragmentOuter();
    final Fragment childFragment2 = new MCChildFragment2();
    final Fragment childFragment3 = new MCChildFragment3();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_my_closet, container, false);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.mcchild_fragment_container, childFragment).commit();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Button btn_rec1 = (Button) view.findViewById(R.id.btn_otrmc);
        btn_rec1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.mcchild_fragment_container, childFragment).commit();

            }
        });

        Button btn_rec2 = (Button) view.findViewById(R.id.btn_topmc);
        btn_rec2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.mcchild_fragment_container, childFragment2).commit();
            }
        });

        Button btn_rec3 = (Button) view.findViewById(R.id.btn_btmmc);
        btn_rec3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.mcchild_fragment_container, childFragment3).commit();
            }
        });

        FloatingActionButton btn_upload = (FloatingActionButton) view.findViewById(R.id.uploadButton);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpload();

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

    public void messageFromChildFragment(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }

    public void goToUpload(){
        Intent intent = new Intent(getActivity(), MyclothesUploadActivity.class);
        startActivity(intent);
    }
}