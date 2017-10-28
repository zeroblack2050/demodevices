package com.demodevices;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView camera;
    private ImageView gallery;
    private RecyclerView recyclerview;
    private LinearLayout llPhotoItems;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> arrayFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        setListener();
        arrayFiles = new ArrayList<>();
        callAdapter();
        observerLayoutGetSize();


    }

    private void observerLayoutGetSize() {
        ViewTreeObserver observer = llPhotoItems.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                photoAdapter.setSize(llPhotoItems.getWidth(),llPhotoItems.getHeight());
                photoAdapter.setFiles(arrayFiles);
                photoAdapter.notifyDataSetChanged();
            }
        });
    }


    private void callAdapter() {
        photoAdapter = new PhotoAdapter(MainActivity.this);
        photoAdapter.setFiles(arrayFiles);
        recyclerview.setAdapter(photoAdapter);

    }


    private void setListener(){
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGallery();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGallery();
            }
        });


    }

    private void showGallery(){
        if(Permissions.isGrantedPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            showGalleryIntent();
        }else {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE};
            Permissions.verifyPermissions(this,permissions);
        }
    }

    private void showGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if(Build.VERSION.SDK_INT < 19){
            startActivityForResult(intent,Constants.GALLERY_KITKAT);
        }else {
            String[] type = {"image/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, type);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(intent,Constants.GALLERY);
        }
    }



    private void loadViews(){
        gallery = (ImageView)findViewById(R.id.main_galleryItems);
        camera = (ImageView)findViewById(R.id.main_cameraItems);
        recyclerview = (RecyclerView) findViewById(R.id.main_rvItems);
        llPhotoItems = (LinearLayout)findViewById(R.id.main_llItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerview.setLayoutManager(layoutManager);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Constants.GALLERY_KITKAT){
            resultGalleryKitkatLess(data.getData());
        }
        if(requestCode == Constants.GALLERY){
            resultGalleryKitkatHigher(data);
        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(16)
    private void resultGalleryKitkatHigher(Intent data) {
        ClipData clipData = data.getClipData();

        if (clipData == null){
            resultGalleryKitkatLess(data.getData());
        }else{
            for (int i=0; i <clipData.getItemCount(); i++){
                grantUriPermission(getPackageName(), clipData.getItemAt(i).getUri(),Intent.FLAG_GRANT_READ_URI_PERMISSION);
                setArrayFilesName(clipData.getItemAt(i).getUri().toString());
            }
        }


    }

    private void resultGalleryKitkatLess(Uri uri) {
        grantUriPermission(getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        setArrayFilesName(uri.toString());
    }

    private void setArrayFilesName(String file) {
        arrayFiles.add(file);
        photoAdapter.setFiles(arrayFiles);
        photoAdapter.notifyDataSetChanged();
    }
}
