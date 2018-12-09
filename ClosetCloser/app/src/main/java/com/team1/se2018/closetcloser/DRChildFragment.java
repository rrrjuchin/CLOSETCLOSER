package com.team1.se2018.closetcloser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DRChildFragment extends Fragment{

    private OnFragmentInteractionListener mListener;

    private List<Upload> mUploads;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private TestImageAdapter mAdapter;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String top_img;
    private String bottom_img;
    private String outer_img;

    private ImageView ivOuter;
    private ImageView ivTop;
    private ImageView ivBottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_drchild, container, false);

        ivOuter = (ImageView)rootView.findViewById(R.id.outer_image);
        ivTop = (ImageView)rootView.findViewById(R.id.top_image);
        ivBottom = (ImageView)rootView.findViewById(R.id.bottom_image);

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

    public void getimgUID(String top, String bottom, String outer, String season) {

        if(top == null){
            return;
        }

        String doc_path = "Usercloset/" + firebaseUser.getUid() + "/" + season + "__top";

        DocumentReference docRef = db.collection(doc_path).document(top);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        downloadimg(document.get("img").toString(), ivTop);
                    }
                }
            }
        });


        doc_path = "Usercloset/" + firebaseUser.getUid() + "/" + season + "__bottom";

        docRef = db.collection(doc_path).document(bottom);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        downloadimg(document.get("img").toString(), ivBottom);
                    }
                }
            }
        });

        doc_path = "Usercloset/" + firebaseUser.getUid() + "/" + season + "__outer";

        docRef = db.collection(doc_path).document(outer);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        downloadimg(document.get("img").toString(), ivOuter);
                    }
                }
            }
        });


    }

    public void downloadimg(String img,final ImageView iv){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("/user/"+img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(DRChildFragment.this.getActivity())
                        .load(uri)
                        .fit()
                        .into(iv);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
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