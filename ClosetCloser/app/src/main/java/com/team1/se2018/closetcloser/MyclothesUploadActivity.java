package com.team1.se2018.closetcloser;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.google.common.io.Files.getFileExtension;

public class MyclothesUploadActivity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText mEditTextFileName;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private TransactionClass transaction;

    private String SERVER_URL = "http://54.180.112.26/upload.php";
    private int MY_SOCKET_TIMEOUT_MS = 50000;
    private String staticuid;
    private String clothes_season = null;
    private String clothes_type = null;
    private String clothes_category = null;
    private String clothes_color = null;
    private Uri clothes_path = null;
    private String storageref = null;
    private String saveseason = null;

    private Spinner season;
    private Spinner type;
    private Spinner category;
    private Spinner color;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String,Object> dataDB = new HashMap<>();

    private final int GALLERY_CODE=1112;

    private ImageView image_view;

    private Bitmap img;
    private String[] predict_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mStorageRef = FirebaseStorage.getInstance().getReference("user");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user");
        season = (Spinner) findViewById(R.id.season);
        type = (Spinner) findViewById(R.id.type);
        category = (Spinner) findViewById(R.id.category);
        color = (Spinner) findViewById(R.id.color);
        transaction = new TransactionClass();

        final Button image_button = (Button) findViewById(R.id.image);
        final Button upload_button = (Button) findViewById(R.id.upload);
        final Button predict_button = (Button) findViewById(R.id.predict);
        final Button close_button = (Button) findViewById(R.id.close);
        image_view = (ImageView)findViewById(R.id.image_view);


        season.setSelection(0);
        type.setSelection(0);
        category.setSelection(0);
        color.setSelection(0);

        clothes_category = (String) category.getItemAtPosition(0);
        clothes_season = (String) season.getItemAtPosition(0);
        clothes_type = (String) type.getItemAtPosition(0);
        clothes_color = (String) color.getItemAtPosition(0);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.clothes_type, android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter1);

        season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str = (String) season.getSelectedItem();
                clothes_season = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str = (String) type.getSelectedItem();
                clothes_type = str;

                if (str.equals(type.getItemAtPosition(0))) {
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter1);

                } else if (str.equals(type.getItemAtPosition(1))) {
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.top, android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter1);
                } else if (str.equals(type.getItemAtPosition(2))) {
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.bottom, android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter1);
                } else {
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.outer, android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        category.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (clothes_type.equals(type.getItemAtPosition(0)) ) {
                    Toast.makeText(getApplicationContext(), "type을 먼저 선택해주십시오", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str = (String) category.getSelectedItem();
                clothes_category = str;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str = (String) color.getSelectedItem();
                clothes_color = str;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        upload_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                saveseason = clothes_season + "__" + clothes_type;
                staticuid = UUID.randomUUID().toString();
                if(!(clothes_path==null) && !clothes_season.equals(season.getItemAtPosition(0)) && !clothes_type.equals(type.getItemAtPosition(0)) && !clothes_category.equals(category.getItemAtPosition(0)) && !clothes_color.equals(color.getItemAtPosition(0))){

                    String image_path = firebaseUser.getUid() + '/' + System.currentTimeMillis() + "." + getFileExtension(clothes_path);
                    dataDB.put("category", clothes_category);
                    dataDB.put("color", clothes_color);
                    dataDB.put("img", image_path);

                    String document_Path = clothes_season + "__" + clothes_type;
                    final String new_path = clothes_category + "__" + staticuid;

                    db.collection("Usercloset").document(firebaseUser.getUid()).collection(document_Path).document(new_path).set(dataDB, SetOptions.merge());
                    transaction.getmyClothes(clothes_season, clothes_type, clothes_category, clothes_color, new_path);


                    final StorageReference fileReference = mStorageRef.child(image_path);
                    final Uri[] downloadUri = new Uri[1];

                    final ProgressDialog progressDialog = new ProgressDialog(MyclothesUploadActivity.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    mUploadTask = fileReference.putFile(clothes_path)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler handler = new Handler();
                                    progressDialog.dismiss();

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;
                                    String str2 = new_path;
                                    Uri downloadUrl = urlTask.getResult();
                                    Upload upload = new Upload(str2, downloadUrl.toString(), clothes_type);
                                    String uploadId = mDatabaseRef.push().getKey();

                                    uploadId = firebaseUser.getUid() + "/" + saveseason  + "/" + str2;

                                    mDatabaseRef.child(uploadId).setValue(upload);
                                    Toast.makeText(MyclothesUploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                                    season.setSelection(0);
                                    type.setSelection(0);
                                    category.setSelection(0);
                                    color.setSelection(0);

                                    clothes_category = (String) category.getItemAtPosition(0);
                                    clothes_season = (String) season.getItemAtPosition(0);
                                    clothes_type = (String) type.getItemAtPosition(0);
                                    clothes_color = (String) color.getItemAtPosition(0);
                                    clothes_path = null;
                                    image_view.setVisibility(View.GONE);


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MyclothesUploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(), "모든 항목을 선택해주십시오", Toast.LENGTH_SHORT).show();
                }
            }
        });

        close_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        image_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }

            }

        });

        predict_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(MyclothesUploadActivity.this);
                progressDialog.setTitle("Predict");
                progressDialog.show();
                progressDialog.setMessage("Loading..");

                // post image to server
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        predict_result = new String[2];
                        predict_result = receiveJsonArray(response);

                        setSpinner();

                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error: "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        String imageData = imageToString(img);
                        params.put("image", imageData);

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));

                RequestQueue requestQueue = Volley.newRequestQueue(MyclothesUploadActivity.this);
                requestQueue.add(stringRequest);


            }
        });

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if(requestCode == 0) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GALLERY_CODE);

            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == GALLERY_CODE){

                InputStream in = null;
                try {

                    clothes_path = data.getData();
                    in = getContentResolver().openInputStream(clothes_path);

                    img = BitmapFactory.decodeStream(in);

                    String imagePath = getRealPathFromURI(clothes_path); // path 경로
                    ExifInterface exif = null;
                    Log.e("please_help",imagePath);
                    try {
                        exif = new ExifInterface(imagePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int exifDegree = exifOrientationToDegrees(exifOrientation);

                    image_view.setVisibility(View.VISIBLE);
                    image_view.setImageBitmap(rotate(img, exifDegree));

                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private String[] receiveJsonArray(String dataObject){

        String predicted_category = null;
        String predicted_color = null;

        String[] ret_arr = new String[2];

        try{
            JSONObject obj = new JSONObject(dataObject);
            predicted_category = obj.optString("Category");
            predicted_color = obj.optString("Color");
            ret_arr[0] = predicted_category;
            ret_arr[1] = predicted_color;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return ret_arr;
    }

    void setSpinner(){

        String[] temp_category;
        String[] type_list;
        String[] color_list;
        String[] top_list;
        String[] bottom_list;
        String[] outer_list;

        int found = 0;

        clothes_category = predict_result[0];
        clothes_color = predict_result[1];


        type_list = getResources().getStringArray(R.array.clothes_type);
        color_list = getResources().getStringArray(R.array.clothes_colors);

        type.setSelection(0, true);
        color.setSelection(0, true);
        category.setSelection(0, true);

        temp_category = getResources().getStringArray(R.array.top);
        int len = temp_category.length;

        for(int i=0; i<len; i++){
            Log.d("clothes", Integer.toString(i));
            Log.d("clothes", clothes_category);
            Log.d("clothes", temp_category[i]);
            if(clothes_category.equals(temp_category[i])){

                Log.d("clothes", "here");
                clothes_type =  (String) type.getItemAtPosition(1);
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.top, android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(adapter1);
                found = 1;
                break;
            }
        }

        if(found == 0){

            temp_category = getResources().getStringArray(R.array.bottom);
            len = temp_category.length;

            for(int i=0; i<len; i++){
                Log.d("clothes", Integer.toString(i));
                Log.d("clothes", clothes_category);
                Log.d("clothes", temp_category[i]);
                if(clothes_category.equals(temp_category[i])){

                    Log.d("clothes", "here");

                    clothes_type =  (String) type.getItemAtPosition(2);
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.bottom, android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter1);
                    found = 1;
                    break;
                }
            }
        }

        if(found == 0){

            temp_category = getResources().getStringArray(R.array.outer);
            len = temp_category.length;

            for(int i=0; i<len; i++){
                Log.d("clothes", Integer.toString(i));
                Log.d("clothes", clothes_category);
                Log.d("clothes", temp_category[i]);
                if(clothes_category.equals(temp_category[i])){

                    Log.d("clothes", "here");
                    clothes_type =  (String) type.getItemAtPosition(3);
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MyclothesUploadActivity.this, R.array.outer, android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter1);
                    found = 1;
                    break;
                }
            }

        }
        Log.d("clothes", clothes_type);
        Log.d("clothes", clothes_category);
        Log.d("clothes", clothes_color);

        int indextype = Arrays.asList(type_list).indexOf(clothes_type);
        int indexcolor = Arrays.asList(color_list).indexOf(clothes_color);
        final int indexcategory = Arrays.asList(temp_category).indexOf(clothes_category);

        Log.d("clothes_index", Integer.toString(indextype));
        Log.d("clothes_index", Integer.toString(indexcolor));

        type.setSelection(indextype, true);
        color.setSelection(indexcolor, true);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                category.setSelection(indexcategory);
            }
        }, 100);

    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {

        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

}