package com.team1.se2018.closetcloser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MCChildFragment3 extends Fragment
        implements MCChildFragmentItem.OnFragmentInteractionListener , TestImageAdapter.OnItemClickListener {

    private MyClosetActivity.OnFragmentInteractionListener mListener;

    final Fragment childFragment = new MCChildFragmentItem();

    private RecyclerView mRecyclerView;
    private TestImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ValueEventListener mDBListener;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private List<Upload> mUploads;

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
        View root = inflater.inflate(R.layout.activity_test_images, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mProgressCircle = root.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new TestImageAdapter(MCChildFragment3.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(MCChildFragment3.this);

        mStorage = FirebaseStorage.getInstance();

        String uid = firebaseUser.getUid();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user/"+uid + "/winter__top");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + mDatabaseRef);

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user/"+uid + "/winter__bottom");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + mDatabaseRef);

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user/"+uid + "/winter__outer");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + mDatabaseRef);

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
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

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        final Upload selectedItem = mUploads.get(position);
        final String selectedtype = selectedItem.getType();
        final String selectedname = selectedItem.getName();

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+selectedtype);

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                String uid = firebaseUser.getUid();

                if(selectedtype.equals("top")){

                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("user/"+uid + "/winter__top");
                    db.collection("Usercloset").document(uid).collection("winter__top").document(selectedname)
                            .delete();

                    db.collection("Transaction").document(uid).collection("bottom")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.getString("top").equals(selectedname)){
                                                document.getReference().delete();
                                            }
                                        }
                                    }
                                }

                            });
                    db.collection("Transaction").document(uid).collection("outer")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.getString("top").equals(selectedname)){
                                                document.getReference().delete();
                                            }
                                        }
                                    }
                                }

                            });

                }
                else if(selectedtype.equals("bottom")){
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("user/"+uid + "/winter__bottom");
                    db.collection("Usercloset").document(uid).collection("winter__bottom").document(selectedname)
                            .delete();


                    db.collection("Transaction").document(uid).collection("bottom")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.getString("clothes").equals(selectedname)){
                                                document.getReference().delete();
                                            }
                                        }
                                    }
                                }

                            });

                }
                else{
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("user/"+uid + "/winter__outer");
                    db.collection("Usercloset").document(uid).collection("winter__outer").document(selectedname)
                            .delete();


                    db.collection("Transaction").document(uid).collection("outer")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.getString("clothes").equals(selectedname)){
                                                document.getReference().delete();
                                            }
                                        }
                                    }
                                }

                            });

                }

                mDatabaseRef.child(selectedname).removeValue();
                Fragment currentFragment = getFragmentManager().findFragmentById(R.id.mcchild_fragment_container);
                FragmentTransaction fragTransaction =   (currentFragment).getFragmentManager().beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}