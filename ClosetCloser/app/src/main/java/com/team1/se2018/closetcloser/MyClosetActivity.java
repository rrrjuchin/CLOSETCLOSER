package com.team1.se2018.closetcloser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MyClosetActivity extends Fragment implements DRChildFragment.OnFragmentInteractionListener,DRChildFragment2.OnFragmentInteractionListener{


    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_my_closet, container, false);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final Fragment childFragment = new DRChildFragment();
        final Fragment childFragment2 = new DRChildFragment2();
        final Fragment childFragment3 = new DRChildFragment3();

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