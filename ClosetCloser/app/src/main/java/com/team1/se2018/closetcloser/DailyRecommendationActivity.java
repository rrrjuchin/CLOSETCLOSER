package com.team1.se2018.closetcloser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

public class DailyRecommendationActivity extends Fragment
        implements DRChildFragment.OnFragmentInteractionListener,
        DRChildFragment2.OnFragmentInteractionListener{

    private TextView mTextView;
    private OnFragmentInteractionListener mListener;

    final Fragment childFragment = new DRChildFragment();
    final Fragment childFragment2 = new DRChildFragment2();
    final Fragment childFragment3 = new DRChildFragment3();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();



    final String SERVER_URL_R = "http://54.180.112.26/recommend.php";
    final int MY_SOCKET_TIMEOUT_MS_R = 50000;

    protected String[] random_category = new String[3];
    protected String[] random_color = new String[3];

    protected String[] recommend_category = new String[3];
    protected String[] recommend_color = new String[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_daily_recommendation, container, false);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.drchild_fragment_container, childFragment).commit();

        Button btn_rec3 = (Button) rootView.findViewById(R.id.btn_rec3);
        btn_rec3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                randseltop_ini();

                Toast.makeText(getActivity(),"눌렷다!!!!!!!!",Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        Button btn_rec1 = (Button) view.findViewById(R.id.btn_rec1);
        btn_rec1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.drchild_fragment_container, childFragment).commit();

            }
        });

        Button btn_rec2 = (Button) view.findViewById(R.id.btn_rec2);
        btn_rec2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.drchild_fragment_container, childFragment2).commit();
            }
        });

        Button btn_rec3 = (Button) view.findViewById(R.id.btn_rec3);
        btn_rec3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.drchild_fragment_container, childFragment3).commit();
            }
        });

        // create bottom sheet
        Button deleteBtn = (Button) view.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadFeedbackBSD badFeedbackBSD = BadFeedbackBSD.getInstance();
                badFeedbackBSD.show(getActivity().getSupportFragmentManager(), "bottomSheet");
            }
        });

        FrameLayout feedbackBtn = (FrameLayout) view.findViewById(R.id.drchild_fragment_container);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodFeedbackBSD goodFeedbackBSD = GoodFeedbackBSD.getInstance();
                goodFeedbackBSD.show(getActivity().getSupportFragmentManager(), "bottomSheet");
            }
        });

        FloatingActionButton refreshBtn = (FloatingActionButton) view.findViewById(R.id.btn_refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
                // get new recommendation
                recommendationService();
            }
        });
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
    public void messageFromChildFragment(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }

    private void randseltop_ini() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fhj = FirebaseAuth.getInstance();
        FirebaseUser fu = fhj.getCurrentUser();
        final int[] i = {0};
        final int[] cnt = {0};
        final int[] randomindex = {0};
        final ArrayList randListRes = new ArrayList();
        final ArrayList randListTop = new ArrayList();
        db.collection("/Usercloset/"+fu.getUid()+"/winter__top")
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

                            for (i[0] = 0; i[0] < 3; i[0]++) {
                                randomindex[0] = randomRange(0, cnt[0] - 1);

                                randListRes.add(randListTop.get(randomindex[0]));
                                System.out.println(randListRes);
                            }
                        } else {

                            System.out.println("Shit");

                        }
                    }
                });
    }


    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }


    private void recommendationService(){
        String user_id = firebaseUser.getUid();

        // post image to server
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL_R, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject obj = new JSONObject(response);
                    recommend_category[0] = obj.optString("category_1");
                    recommend_category[1] = obj.optString("category_2");
                    recommend_category[2] = obj.optString("category_3");
                    recommend_color[0] = obj.optString("color_1");
                    recommend_color[1] = obj.optString("color_2");
                    recommend_color[2] = obj.optString("color_3");
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
                params.put("category_1", random_category[0]);
                params.put("color_1", random_color[0]);
                params.put("category_2", random_category[1]);
                params.put("color_2", random_color[1]);
                params.put("category_3", random_category[2]);
                params.put("color_3", random_color[2]);
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

    void test(){
        random_category[0] = "coat";
        random_category[1] = "padding";
        random_category[2] = "sweater";

        random_color[0] = "black";
        random_color[1] = "red";
        random_color[2] = "brown";

    }
}