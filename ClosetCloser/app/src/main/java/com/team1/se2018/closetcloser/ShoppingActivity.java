package com.team1.se2018.closetcloser;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ShoppingActivity extends Fragment
        implements SRChildFragment.OnFragmentInteractionListener, SRChildFragment2.OnFragmentInteractionListener{

    final String SERVER_URL_A = "http://54.180.112.26/all_new_styles/client.php";
    final int MY_SOCKET_TIMEOUT_MS_R = 50000;


    private OnFragmentInteractionListener mListener;
    Bundle bundle = new Bundle();
    SRChildFragment2 fragInfo = new SRChildFragment2();
    final Fragment childFragment = new SRChildFragment();
    final Fragment childFragment2 = new SRChildFragment2();
    final Fragment childFragment3 = new SRChildFragment3();
    String userID = null;
    String season = "winter";

    String man = "man";

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

    String top1,top2,top3;
    String bottom1,bottom2,bottom3;
    String outer1,outer2,outer3;

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
                randseltop_ini();
                //fragInfo.setArguments(bundle);
                /////////////////////////////////real downurl address. we bundle this 9 from here to fragInfo

                ////////////////////////////////////////////////////////////////////
                transaction.replace(R.id.srchild_fragment_container,  fragInfo).commit();
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
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.get("sex") == "M"){
                            check_sex = "man";
                        }else{
                            check_sex = "woman";
                        }
                        db.collection("/SellerCloset/man/winter__top")
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
                                            top_id_1 = randListRes.get(0).toString();
                                            top_id_2 = randListRes.get(1).toString();
                                            top_id_3 = randListRes.get(2).toString();
                                            found = true;

                                            recommend(found, progressDialog);


                                            //bundle.putString("top1", top_id_1);
                                            //bundle.putString("top2", top_id_2);
                                            //bundle.putString("top3", top_id_3);

                                        } else {

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
    //////////////////////////////////////////////////this function is from key to downurl;

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

    public void recommend(boolean found, final ProgressDialog progressDialog){


        Log.d("here22", String.valueOf(found));
        if(!found){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "옷장에 등록된 옷의 개수가 부족합니다", Toast.LENGTH_LONG).show();
            return;
        }


        // get new recommendation
        // post image to server
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL_A, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try{
                    progressDialog.dismiss();

                    JSONObject obj = new JSONObject(response);

                    bottom_id_1 = obj.optString("bottom_id_1");
                    bottom_id_2 = obj.getString("bottom_id_2");
                    bottom_id_3 = obj.getString("bottom_id_3");

                    outer_id_1 = obj.getString("outer_id_1");
                    outer_id_2 = obj.getString("outer_id_2");
                    outer_id_3 = obj.getString("outer_id_3");


                    // test toast

                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.srchild_fragment_container, childFragment2).commit();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            SRChildFragment2 frag = (SRChildFragment2) childFragment2.getFragmentManager().findFragmentById(R.id.srchild_fragment_container);
                            frag.geturl(top_id_1, bottom_id_1, outer_id_1, top_id_2, bottom_id_2, outer_id_2, top_id_3, bottom_id_3, outer_id_3);
                        }
                    }, 100);


                    //DRChildFragment frag = (DRChildFragment) childFragment.getFragmentManager().findFragmentById(R.id.drchild_fragment_container);
                    //frag.getimgUID(top_id_1, bottom_id_1, outer_id_1, season);

                    // FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    // transaction.replace(R.id.drchild_fragment_container, childFragment).commit();


                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), "error: "+error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("sex", man);
                params.put("season", season);
                params.put("top_id_1", top_id_1);
                params.put("top_id_2", top_id_2);
                params.put("top_id_3", top_id_3);




                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS_R,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // may occurs error
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }


}