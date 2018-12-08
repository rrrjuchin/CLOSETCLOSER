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

public class SRChildFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_srchild, container, false);
        mListView = (ListView)rootView.findViewById(R.id.list_view_outer);
        ListViewAdapter1 lvAdapter1 = new ListViewAdapter1(this.getActivity());

        // get information of items
        SRListItem1 item1 = new SRListItem1("무신사스토어", "레터링맨투맨", "57000원",
                BitmapFactory.decodeResource(getResources(), R.drawable.pizza),
                BitmapFactory.decodeResource(getResources(), R.drawable.chicken),
                BitmapFactory.decodeResource(getResources(), R.drawable.chicken),
                BitmapFactory.decodeResource(getResources(), R.drawable.chicken));
        lvAdapter1.addItem(item1);
        lvAdapter1.addItem(item1);
        lvAdapter1.addItem(item1);

        mListView.setAdapter(lvAdapter1);

        return rootView;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromChildFragment(Uri uri);
    }

}
