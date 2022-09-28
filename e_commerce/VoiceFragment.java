package com.example.e_commerce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import java.util.ArrayList;
import java.util.Objects;


public class VoiceFragment extends Fragment {


    EditText myText;
    Cursor matchedSearch;
    int voiceCode=1;
    ListView myList;
    DataBase db;
    Button search;
    ActivityResultLauncher<Intent>activityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_voice, container, false);
        db= new DataBase(getActivity());
        myList=(ListView) rootView.findViewById(R.id.listView);
        myText=(EditText) rootView.findViewById(R.id.myText);
        ImageButton voiceBtn=(ImageButton) rootView.findViewById(R.id.imageButton);
        search=(Button)rootView.findViewById(R.id.srch_btn);
      /*  activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if ( result.getResultCode() == getActivity().RESULT_OK) {

                            ArrayList<String> text = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            myText.setText(text.get(0));
                            ArrayAdapter<String> ProductAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
                            myList.setAdapter(ProductAdapter);
                            matchedSearch = db.getProductByName(text.get(0));
                            if (matchedSearch != null) {
                                while (!matchedSearch.isAfterLast()) {
                                    ProductAdapter.add(matchedSearch.getString(0));
                                    matchedSearch.moveToNext();
                                }
                            } else {
                                Toast.makeText(getActivity(), "No Matched Products", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_SHORT).show();
                        }





                }}); */

       /*         search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String prod = myText.getText().toString();
                        if (prod.isEmpty()) {
                            Toast.makeText(getActivity(), "Please Enter Name of product", Toast.LENGTH_SHORT).show();
                        } else {
                            Cursor c = db.getProductByName(prod);
                            if (c != null) {


                                Toast.makeText(getActivity(), "No Matched Products22", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getActivity(), "No Matched Products", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }); */
        voiceBtn.setOnClickListener(v -> {
            Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent,voiceCode);
      //     if(intent.resolveActivity(getActivity().getPackageManager())!=null){
        //    activityResultLauncher.launch(intent);

        /*    else{
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }  */

        });

      return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==voiceCode&&resultCode==getActivity().RESULT_OK)
        {


            ArrayList<String>text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            myText.setText(text.get(0));
            ArrayAdapter<String>ProductAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
            myList.setAdapter(ProductAdapter);
            matchedSearch=db.getProductByName(text.get(0));
            if(matchedSearch != null)
            {
                while (!matchedSearch.isAfterLast())
                {
                    ProductAdapter.add(matchedSearch.getString(0));
                   matchedSearch.moveToNext();
                }
            }
            else{
                Toast.makeText(getActivity(),"No Matched Products",Toast.LENGTH_SHORT).show();
            }
       }
        else {
            Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_SHORT).show();
        }
    }
}