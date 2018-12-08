package com.team1.se2018.closetcloser;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SRChildFragment2 extends Fragment {

    private SRChildFragment.OnFragmentInteractionListener mListener;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_srchild_fragment2, container, false);
        mListView = (ListView)rootView.findViewById(R.id.list_view_outer);
        ListViewAdapter2 lvAdapter2 = new ListViewAdapter2(this.getActivity());

        // get information of items
        SRListItem2 item1 = new SRListItem2("무신사스토어", "레터링맨투맨", "57000원",
                BitmapFactory.decodeResource(getResources(), R.drawable.pizza),
                BitmapFactory.decodeResource(getResources(), R.drawable.chicken),
                BitmapFactory.decodeResource(getResources(), R.drawable.chicken));
        lvAdapter2.addItem(item1);
        lvAdapter2.addItem(item1);
        lvAdapter2.addItem(item1);

        mListView.setAdapter(lvAdapter2);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SRChildFragment.OnFragmentInteractionListener) {
            mListener = (SRChildFragment.OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
