package com.team1.se2018.closetcloser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class MCChildFragmentOuter extends Fragment
        implements MCChildFragmentItem.OnFragmentInteractionListener {

    private MyClosetActivity.OnFragmentInteractionListener mListener;

    final Fragment childFragment = new MCChildFragmentItem();

    // TODO: Rename and change types of parameters
    public static MCChildFragmentOuter newInstance(String param1, String param2) {
        MCChildFragmentOuter fragment = new MCChildFragmentOuter();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mcchild, container, false);

        final GridView gridView = (GridView)root.findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapterOuter(this.getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Clicked "+position);

                //
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.mcchild_fragment_container, childFragment).commit();
                gridView.setVisibility(gridView.GONE);
            }
        });

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}