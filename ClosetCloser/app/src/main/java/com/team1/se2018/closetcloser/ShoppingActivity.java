package com.team1.se2018.closetcloser;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShoppingActivity extends Fragment
        implements SRChildFragment.OnFragmentInteractionListener, SRChildFragment2.OnFragmentInteractionListener{


    private OnFragmentInteractionListener mListener;

    final Fragment childFragment = new SRChildFragment();
    final Fragment childFragment2 = new SRChildFragment2();
    final Fragment childFragment3 = new SRChildFragment3();
    String userID = null;
    String top_id_1 = null;
    String top_id_2 = null;
    String top_id_3 = null;
    private String check_sex = "*";
    String bottom_id_1 = null;
    String bottom_id_2 = null;
    String bottom_id_3 = null;

    String outer_id_1 = null;
    String outer_id_2 = null;
    String outer_id_3 = null;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_shopping, container, false);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.srchild_fragment_container, childFragment).commit();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Button btn_1 = (Button) view.findViewById(R.id.btn_wmc);
        btn_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.srchild_fragment_container, childFragment).commit();

            }
        });

        Button btn_2 = (Button) view.findViewById(R.id.btn_ans);
        btn_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.srchild_fragment_container, childFragment2).commit();
                Log.e("hahahahahahahahahaha",check_sex);
                randseltop_ini();
            }
        });


    }

    boolean found = false;
    private void randseltop_ini() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setTitle("Recommendation");
        //progressDialog.show();
        //progressDialog.setMessage("Loading..");

        userID = firebaseUser.getUid();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fhj = FirebaseAuth.getInstance();
        final FirebaseUser fu = fhj.getCurrentUser();
        final int[] i = {0};
        final int[] cnt = {0};
        final int[] randomindex = {0};
        final ArrayList randListRes = new ArrayList();
        final ArrayList randListTop = new ArrayList();
        final int[] random_tag = {0};
        found = false;
        DocumentReference docRef = db.collection("Normmem").document(fu.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.e("haha",check_sex);
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.e("haha2",check_sex);
                        if(document.get("sex") == "M"){
                            check_sex = "man";
                        }else{
                            check_sex = "woman";
                        }
                        db.collection("/Seller_cloth/"+ check_sex + "/winter__top")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                                                System.out.println(document.getId());
                                                randListTop.add(document.getId());
                                                cnt[0]++;
                                                System.out.println(randListTop);
                                                System.out.println(cnt[0]);
                                            }
                                            Log.e("haha3",check_sex);
                                            Log.d("here", Integer.toString(randListTop.size()));
                                            if(randListTop.size() < 3){
                                                found = false;
                                                //recommend(found, progressDialog);
                                                return;
                                            }
                                            for (i[0] = 0; i[0] < 3; i[0]++) {
                                                random_tag[0] = 0;
                                                randomindex[0] = randomRange(0, cnt[0] - 1);
                                                if(i[0] == 0){
                                                    randListRes.add(randListTop.get(randomindex[0]));
                                                    continue;
                                                }
                                                for(int j = 0 ; j < randListRes.size();j++){
                                                    if(randListTop.get(randomindex[0]) == randListRes.get(j).toString()){
                                                        i[0]-=1;
                                                        random_tag[0] = 1;
                                                        break;
                                                    }
                                                }
                                                if(random_tag[0] == 1){
                                                    continue;
                                                }
                                                randListRes.add(randListTop.get(randomindex[0]));
                                                System.out.println(randListRes);
                                            }
                                            Log.e("haha4",check_sex);
                                            top_id_1 = randListRes.get(0).toString();
                                            top_id_2 = randListRes.get(1).toString();
                                            top_id_3 = randListRes.get(2).toString();
                                            Log.e("hahahahahahahahahaha",top_id_1);
                                            Log.e("hahahahahahahahahaha",top_id_2);
                                            Log.e("hahahahahahahahahaha",top_id_3);
                                            found = true;
                                            //recommend(found, progressDialog);

                                            Log.d("here", String.valueOf(found));


                                        } else {
                                            System.out.println("Shit");

                                        }

                                    }

                                });

                    } else {
                    }
                } else {
                }
            }
        });

    }


    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
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
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }
}