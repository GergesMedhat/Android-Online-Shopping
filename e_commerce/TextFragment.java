package com.example.e_commerce;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TextFragment extends Fragment {

    ListView lst;
    Button btn;
    EditText txt;
    ArrayAdapter<String>adapter;
    DataBase db;
    Cursor c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_text, container, false);
        db=new DataBase(getActivity());
        lst=(ListView) rootView.findViewById(R.id.search_lst);
        adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        lst.setAdapter(adapter);
        btn=(Button) rootView.findViewById(R.id.button5);
        txt=(EditText) rootView.findViewById(R.id.srch_txt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prod=txt.getText().toString();
                 c=db.getProductByName(prod);
                if(c!=null){
                    while (!c.isAfterLast()) {
                        adapter.add(c.getString(0));
                        c.moveToNext();
                    }

                }
                else{
                    Toast.makeText(getActivity(),prod +" Not Found",Toast.LENGTH_SHORT).show();
                    txt.setText("");
                }



            }
        });

      return rootView;
    }
}
