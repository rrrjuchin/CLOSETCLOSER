package com.team1.se2018.closetcloser;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class GoodFeedbackBSD extends BottomSheetDialogFragment {

    public static GoodFeedbackBSD getInstance() {
        return new GoodFeedbackBSD();
    }

    private GoodFeedbackBSD.BottomSheetListener mListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    Map<String,Object> dataDB = new HashMap<>();

    private String top_info;
    private String bottom_info;
    private String outer_info;
    private String season_info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_good_feedback_bsd, container, false);

        Button button1 = v.findViewById(R.id.button1);
        Button button2 = v.findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 1 clicked");
                db.collection("Transaction").document(firebaseUser.getUid()).collection("bottom")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("top").equals(top_info) && document.getString("clothes").equals(bottom_info)) {
                                            long num = (long) document.get("num");
                                            num = num + 1;
                                            dataDB.put("num", num);
                                            document.getReference().set(dataDB, SetOptions.merge());
                                            break;
                                        }

                                    }
                                }
                            }
                        });

                db.collection("Transaction").document(firebaseUser.getUid()).collection("outer")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("top").equals(top_info) && document.getString("clothes").equals(outer_info)) {
                                            long num = (long) document.get("num");
                                            num = num + 1;
                                            dataDB.put("num", num);
                                            document.getReference().set(dataDB, SetOptions.merge());
                                            break;
                                        }

                                    }
                                }
                            }
                        });

                dismiss();
                Toast.makeText(getActivity(),"피드백이 반영되었습니다",Toast.LENGTH_SHORT).show();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 2 clicked");
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
            mListener = (GoodFeedbackBSD.BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    public void getClothes(String top, String bottom, String outer, String season){

        top_info = top;
        bottom_info = bottom;
        outer_info = outer;
        season_info = season;

    }

}
