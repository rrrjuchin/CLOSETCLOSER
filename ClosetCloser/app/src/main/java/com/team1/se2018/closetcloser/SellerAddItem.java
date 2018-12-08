package com.team1.se2018.closetcloser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class SellerAddItem extends AppCompatActivity {

    ImageView imageView;
    Button button;
    Button btn_2;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller);

        imageView = (ImageView)findViewById(R.id.image111);

        button = (Button)findViewById(R.id.button111);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, 1);
            }
        });

        btn_2 = (Button)findViewById(R.id.btn_regselleritem);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Uri itemup = data.getData();
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    // 이미지 표시
                    imageView.setImageBitmap(img);

                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    Bitmap btmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    btmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    final byte [] dataup = baos.toByteArray();
                    final StorageReference mountainsRef = storageRef.child("jktest1.jpg");

                    btn_2.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            //if 문 사용하여 계절 카테고리 나눔
                            /*




                            */

                            UploadTask uploadTask = mountainsRef.putBytes(dataup);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(SellerAddItem.this,"업로드 성공",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MallMenuActivity.class);
                                    //intent.putExtra("userID", user.getUid());
                                    startActivity(intent);
                                    finish();

                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}