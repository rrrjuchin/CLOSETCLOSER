package com.team1.se2018.closetcloser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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


public class BadFeedbackBSD extends BottomSheetDialogFragment {

    public static BadFeedbackBSD getInstance() {
        return new BadFeedbackBSD();
    }

    private BottomSheetListener mListener;
    private String top_info;
    private String bottom_info;
    private String outer_info;
    private String season_info;

    private FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    Map<String,Object> dataDB = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bad_feedback_bsd, container, false);

        Button okbutton = v.findViewById(R.id.okbutton);
        CheckBox badbottom = v.findViewById(R.id.checkbox);
        CheckBox badouter = v.findViewById(R.id.checkbox2);
        CheckBox badboth = v.findViewById(R.id.checkbox3);


        badbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("bad coordination checked");
                db.collection("Transaction").document(firebaseUser.getUid()).collection("bottom")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("top").equals(top_info) && document.getString("clothes").equals(bottom_info)) {
                                            long num = (long) document.get("num");
                                            num = num - 1;
                                            dataDB.put("num", num);
                                            document.getReference().set(dataDB, SetOptions.merge());
                                            break;
                                        }

                                    }
                                }
                            }
                        });

            }

        });

        badouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("bad color coordination checked");
                db.collection("Transaction").document(firebaseUser.getUid()).collection("outer")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("top").equals(top_info) && document.getString("clothes").equals(outer_info)) {
                                            long num = (long) document.get("num");
                                            num = num - 1;
                                            dataDB.put("num", num);
                                            document.getReference().set(dataDB, SetOptions.merge());
                                            break;
                                        }

                                    }
                                }
                            }
                        });
            }
        });

        badboth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("bad for season checked");
                db.collection("Transaction").document(firebaseUser.getUid()).collection("bottom")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("top").equals(top_info) && document.getString("clothes").equals(bottom_info)) {
                                            long num = (long) document.get("num");
                                            num = num - 1;
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
                                            num = num - 1;
                                            dataDB.put("num", num);
                                            document.getReference().set(dataDB, SetOptions.merge());
                                            break;
                                        }

                                    }
                                }
                            }
                        });
            }
        });

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 1 clicked");
                Toast.makeText(getActivity(),"피드백이 반영되었습니다",Toast.LENGTH_SHORT).show();
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

    public void getClothes(String top, String bottom, String outer, String season){

        top_info = top;
        bottom_info = bottom;
        outer_info = outer;
        season_info = season;

    }


}
