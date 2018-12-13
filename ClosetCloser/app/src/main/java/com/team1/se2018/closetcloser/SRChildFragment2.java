package com.team1.se2018.closetcloser;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SRChildFragment2 extends Fragment implements SRImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private SRImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private ArrayList<SRUpload> mUploads;
    private Bitmap imgdwn;
    private String upld;
    private String topurl;
    private String bottomurl;
    private String outerurl;
    private String topurl2;
    private String bottomurl2;
    private String outerurl2;
    private String topurl3;
    private String bottomurl3;
    private String outerurl3;



    // TODO: Rename and change types of parameters
    public static SRChildFragment2 newInstance(String param1, String param2) {
        SRChildFragment2 fragment = new SRChildFragment2();
        Bundle args;
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
        View root = inflater.inflate(R.layout.activity_test_images, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mProgressCircle = root.findViewById(R.id.progress_circle);



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
    public void onItemClick(int position) {
    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        SRUpload selectedItem = mUploads.get(position);
//        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                mDatabaseRef.child(selectedKey).removeValue();
            }
        });
    }

    public void geturl(String top, String bottom, String outer, String top2, String bottom2, String outer2, String top3, String bottom3, String outer3){

        final String[] downurl = new String[1];
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fhj = FirebaseAuth.getInstance();
        final FirebaseUser fu = fhj.getCurrentUser();
        DocumentReference docRef;


        docRef = db.collection("SellerCloset").document("man").collection("winter__top").document(top);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        topurl = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__top").document(top2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        topurl2 = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__top").document(top3);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        topurl3 = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__bottom").document(bottom);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bottomurl = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__bottom").document(bottom2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bottomurl2 = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__bottom").document(bottom3);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bottomurl3 = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__outer").document(outer);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        outerurl = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__outer").document(outer2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        outerurl2 = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        docRef = db.collection("SellerCloset").document("man").collection("winter__outer").document(outer3);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        outerurl3 = document.get("downurl").toString();
                    } else {
                    }
                } else {
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            public void run() {
                giveImage();
            }
        }, 1000);


    }

    public void giveImage(){

        mUploads = new ArrayList<>();
        SRUpload srUpload1 = new SRUpload(topurl, bottomurl, outerurl);
        SRUpload srUpload2 = new SRUpload(topurl2, bottomurl2, outerurl2);
        SRUpload srUpload3 = new SRUpload(topurl3, bottomurl3, outerurl3);
        mAdapter = new SRImageAdapter(SRChildFragment2.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(SRChildFragment2.this);


        mAdapter.addItem(srUpload1);
        mAdapter.addItem(srUpload2);
        mAdapter.addItem(srUpload3);


        mStorage = FirebaseStorage.getInstance();
        String uid = firebaseUser.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user/"+uid+"/winter");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SRUpload upload = postSnapshot.getValue(SRUpload.class);
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}