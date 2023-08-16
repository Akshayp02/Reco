package com.example.mrreco;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class profileFragment extends Fragment {

    private final int GALLERY_REQ_CODE = 100;
    ImageView profileimg;
    TextView User_name;
    FirebaseAuth auth;
//    FireStorage fireStorage;
    public profileFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // to find id
        profileimg = view.findViewById(R.id.profileimg);
        User_name =view.findViewById(R.id.UserName);


        User_name.setText("Aksahy Peherkar");


        profileimg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);



                return false;
            }
        });



        // your logic here


        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK);{

            if(requestCode==RESULT_OK);{
                // for gallery

                profileimg.setImageURI(data.getData());


            }

        }



    }


}