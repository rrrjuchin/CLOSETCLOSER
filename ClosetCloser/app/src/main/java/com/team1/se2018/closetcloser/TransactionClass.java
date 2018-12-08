package com.team1.se2018.closetcloser;

import android.support.annotation.NonNull;
import android.util.Log;

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

public class TransactionClass {

    private final String[] names_top = {"Blouse", "Long_Shirts", "Long_Sleeve", "Sleeveless", "Short_Shirt", "Short_Sleeve", "Sweater"};
    private final String[] names_bottom = {"Cotton_Pants", "Jeans", "Long_Skirts", "Short_Pants", "Short_Jeans", "Short_Skirts", "Slacks"};
    private final String[] names_outer = {"Blazer", "Blouson", "Coat", "Hoodie", "Padding", "Cardigan"};
    private final String[] names_color = {"black", "beige", "brown", "burgundy", "white", "purple", "pink", "dark_blue", "turquoise", "yellow", "orange", "green", "olive_green", "red", "grey"};

    private final int[][] score_top_bottom = {
            {1, 3, 1, 1, 2, 1, 4},
            {2, 4, 4, 1, 1, 1, 1},
            {4, 1, 1, 1, 1, 1, 6},
            {1, 1, 3, 4, 6, 4, 1},
            {1, 1, 6, 6, 4, 6, 1},
            {3, 1, 1, 1, 1, 1, 2},
            {6, 6, 1, 1, 3, 1, 3}};

    private final int[][] score_top_outer = {
            {6, 4, 1, 1, 4, 1, 1},
            {1, 1, 3, 3, 1, 4, 1},
            {4, 6, 1, 1, 1, 1, 4},
            {1, 1, 4, 6, 1, 6, 1},
            {1, 1, 6, 1, 1, 1, 6},
            {3, 1, 1, 4, 6, 1, 1}};

    private final int[][] score_color = {
            {5, 5, 3, 4, 4, 3, 3, 3, 3, 4, 3, 4, 3, 5, 4},
            {5, 3, 5, 4, 2, 1, 3, 4, 2, 3, 2, 4, 4, 3, 3},
            {3, 4, 3, 4, 1, 1, 2, 3, 1, 1, 3, 1, 5, 2, 3},
            {4, 4, 4, 3, 2, 1, 1, 2, 2, 1, 1, 2, 4, 2, 5},
            {4, 5, 5, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
            {2, 1, 2, 2, 3, 3, 4, 2, 3, 4, 5, 3, 2, 3, 2},
            {1, 2, 2, 1, 5, 4, 3, 4, 5, 2, 1, 1, 1, 1, 3},
            {3, 3, 3, 3, 4, 3, 4, 3, 4, 3, 2, 1, 3, 2, 5},
            {1, 1, 1, 1, 5, 3, 5, 4, 3, 2, 4, 2, 2, 4, 2},
            {1, 1, 1, 1, 1, 4, 2, 2, 1, 3, 4, 3, 1, 1, 1},
            {2, 2, 3, 2, 3, 4, 3, 1, 4, 5, 3 ,3 ,3, 4, 1},
            {3, 3, 2, 3, 3, 2, 2, 1, 2, 4, 1, 3, 4, 3, 4},
            {2, 4, 4, 3, 1, 2, 1, 3, 1, 2, 3, 4, 3, 1, 1},
            {4, 2, 1, 2, 4, 2, 1, 1, 3, 1, 4, 2, 1, 3, 2},
            {5, 3, 4, 5, 2, 5, 4, 5, 4, 3, 2, 5, 2, 4, 3}};

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private Map<String,Object> dataDB = new HashMap<>();

    private String classifyCategories(String category){
        if(category.equals("Blouse") || category.equals("Long_Shirts") || category.equals("Long_Sleeve") || category.equals("Sleeveless") ||
                category.equals("Short_Shirt") || category.equals("Short_Sleeve") || category.equals("Sweater")){
            return "top";
        }else if(category.equals("Cotton_Pants") || category.equals("Jeans") || category.equals("Long_Skirts") || category.equals("Short_Pants") ||
                category.equals("Short_Jeans") || category.equals("Short_Skirts") || category.equals("Slacks")){
            return "bottom";

        }else if(category.equals("Blazer") || category.equals("Blouson") || category.equals("Coat") ||
                category.equals("Hoodie") || category.equals("Padding") || category.equals("Cardigan")){
            return "outer";
        }else{
            return "false";
        }
    }

    // scoring function
    public int makeTransaction(String sub_category_from, String color_from, String sub_category_to, String color_to) {
        // first parameter is top
        // third parameter is bottom or outer

        String category_from = classifyCategories(sub_category_from);
        if (category_from.equals("bottom") || category_from.equals("outer") || category_from.equals("false")) {
            return -1;
        }

        String category_to = classifyCategories(sub_category_to);
        if (category_to.equals("top") || category_to.equals("false")) {
            return -1;
        }

        int ret_score;
        int category_score = 0;

        int index_top = -1;
        for (int i = 0; i < names_top.length; i++) {

            if (sub_category_from.equals(names_top[i])) {
                index_top = i;
                break;
            }
        }
        if (index_top == -1) {
            return -1;
        }

        if (sub_category_to.equals("bottom")) {
            int index_bottom = -1;
            for (int i = 0; i < names_bottom.length; i++) {

                if (sub_category_to.equals(names_bottom[i])) {
                    index_bottom = i;
                    break;
                }
            }
            category_score = score_top_bottom[index_top][index_bottom];
        } else if (sub_category_to.equals("outer")) {
            int index_outer = -1;
            for (int i = 0; i < names_outer.length; i++) {
                if (sub_category_to.equals(names_outer[i])) {
                    index_outer = i;
                    break;
                }
            }
            category_score = score_top_outer[index_top][index_outer];
        }

        int color_score;
        int index_color_from = -1;
        for(int i=0; i<names_color.length; i++){
            if(color_from.equals(names_color[i])){
                index_color_from = i;
                break;
            }
        }

        int  index_color_to = -1;
        for(int i=0; i<names_color.length; i++){
            if(color_to.equals(names_color[i])){
                index_color_to = i;
                break;
            }
        }

        color_score = score_color[index_color_from][index_color_to];

        ret_score = category_score + color_score;

        return ret_score;

    }


    public void getmyClothes(final String season, final String type, final String category, final String color, final String uid){

        if( type.equals("top")){
            String db_path = season + "__" + "bottom";
            db.collection("Usercloset").document(firebaseUser.getUid()).collection(db_path)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    int num = makeTransaction(category, color, document.getString("category"), document.getString("color"));
                                    dataDB.put("top", uid);
                                    dataDB.put("clothes", document.getId());
                                    dataDB.put("num", num);
                                    db.collection("Transaction").document(firebaseAuth.getUid()).collection("bottom").document().set(dataDB, SetOptions.merge());

                                }

                            } else {
                                Log.d("transss", "Error getting documents: ", task.getException());
                            }
                        }
                    });

            db_path = season + "__" + "outer";
            db.collection("Usercloset").document(firebaseUser.getUid()).collection(db_path)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    int num = makeTransaction(category, color, document.getString("category"), document.getString("color"));
                                    dataDB.put("top", uid);
                                    dataDB.put("clothes", document.getId());
                                    dataDB.put("num", num);
                                    db.collection("Transaction").document(firebaseAuth.getUid()).collection("outer").document().set(dataDB, SetOptions.merge());

                                }

                            } else {
                                Log.d("transss", "Error getting documents: ", task.getException());
                            }
                        }
                    });


        }else if (classifyCategories(category).equals("bottom")){

            String db_path = season + "__" + "top";
            db.collection("Usercloset").document(firebaseUser.getUid()).collection(db_path)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    int num = makeTransaction(document.getString("category"), document.getString("color"), category, color);
                                    dataDB.put("top", document.getId());
                                    dataDB.put("clothes", uid);
                                    dataDB.put("num", num);
                                    db.collection("Transaction").document(firebaseAuth.getUid()).collection("bottom").document().set(dataDB, SetOptions.merge());

                                }

                            } else {
                                Log.d("transss", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }else if (classifyCategories(category).equals("outer")){

            String db_path = season + "__" + "top";
            db.collection("Usercloset").document(firebaseUser.getUid()).collection(db_path)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    int num = makeTransaction(document.getString("category"), document.getString("color"), category, color);
                                    dataDB.put("top", document.getId());
                                    dataDB.put("clothes", uid);
                                    dataDB.put("num", num);
                                    db.collection("Transaction").document(firebaseAuth.getUid()).collection("outer").document().set(dataDB, SetOptions.merge());

                                }

                            } else {
                                Log.d("transss", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }

    }



}
