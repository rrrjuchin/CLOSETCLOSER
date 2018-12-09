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

    String userID = null;
    String season = null;

    String top_id_1 = null;
    String top_id_2 = null;
    String top_id_3 = null;

    String bottom_id_1 = null;
    String bottom_id_2 = null;
    String bottom_id_3 = null;

    String outer_id_1 = null;
    String outer_id_2 = null;
    String outer_id_3 = null;

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
                // test
                test();
                // get new recommendation

                // post image to server
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL_R, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);

                            bottom_id_1 = obj.optString("bottom_id_1");
                            bottom_id_2 = obj.getString("bottom_id_2");
                            bottom_id_3 = obj.getString("bottom_id_3");

                            outer_id_1 = obj.getString("outer_id_1");
                            outer_id_2 = obj.getString("outer_id_2");
                            outer_id_3 = obj.getString("outer_id_3");

                            // test toast
                            Toast.makeText(getActivity(),bottom_id_1+bottom_id_2+bottom_id_3+outer_id_1+outer_id_2+outer_id_3,Toast.LENGTH_SHORT).show();

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
                        params.put("userID", userID);
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



    void test(){
        userID = firebaseUser.getUid();
        season = "winter";
        top_id_1 = "Long_Sleeve__38243e52-b01d-4923-87ae-4243c793c66c";
        top_id_2 = "Sweater__cd361af1-9c6e-4fd5-ad32-b8f3445e21ce";
        top_id_3 = "Long_Sleeve__d2d20a3e-3e27-4a13-b780-6d32bdaa2ad8";
    }
}