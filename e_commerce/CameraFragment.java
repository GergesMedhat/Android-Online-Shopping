package com.example.e_commerce;

import android.animation.IntArrayEvaluator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class CameraFragment extends Fragment {

   private static final int CAMERA_REQUEST=1888;
   private ImageView myCaptureImage;
   ActivityResultLauncher<Intent>activityResultLauncher;
    Button b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_camera, container, false);
        myCaptureImage = (ImageView) rootView.findViewById(R.id.imageView);
        b=(Button) rootView.findViewById(R.id.button);

   /*  activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
           @Override
            public void onActivityResult(ActivityResult activityResult) {
               int result=activityResult.getResultCode();


                if (result==getActivity().RESULT_OK) {

                    Bitmap myimage = (Bitmap) activityResult.getData().getExtras().get("data");
                        myCaptureImage.setImageBitmap(myimage);
                }
                else
                {
                    Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_SHORT).show();
                }

            }
        }); */

         b.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(intent,CAMERA_REQUEST);
              //       activityResultLauncher.launch(intent);


             }
         });

 /*            if(cameraIntent.resolveActivity(getActivity().getPackageManager())!=null){
               activityResultLauncher.launch(cameraIntent);
             }
             else{
                 Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
             } */



        return rootView;
    }

    @Override
   public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode== getActivity().RESULT_OK){

            Bitmap myimage = (Bitmap) data.getExtras().get("data");
            myCaptureImage.setImageBitmap(myimage);
        }
    }
}