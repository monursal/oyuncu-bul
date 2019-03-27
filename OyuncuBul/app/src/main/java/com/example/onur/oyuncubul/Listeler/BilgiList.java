package com.example.onur.oyuncubul.Listeler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onur.oyuncubul.Message;
import com.example.onur.oyuncubul.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur on 21.03.2018.
 */
public class BilgiList extends ArrayAdapter<Message> {

    ListView bilgiListView;
    private View.OnClickListener clickListener;
    private Activity context;
    private List<Message> bilgiList;
    public ArrayList<String> keyList = new ArrayList<>();




    public BilgiList(Activity context, List<Message> bilgiList) {
        super(context, R.layout.bilgi_list, bilgiList);
        this.context = context;
        this.bilgiList = bilgiList;

    }
    /*DatabaseReference databaseReference, databaseReference2;
    FirebaseAuth mAuth;
    private String userID;
    private FirebaseDatabase mFirebasedatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;*/


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();


        View listViewItem = inflater.inflate(R.layout.bilgi_list, null, true);
        final TextView bildiriIsım = listViewItem.findViewById(R.id.bildiriIsim);
        final TextView bildiriYas = listViewItem.findViewById(R.id.bildiriYas);
        final TextView bildiriTel = listViewItem.findViewById(R.id.bildiriTel);


        Message message = bilgiList.get(position);


        keyList.add(message.getMessageId());
        bildiriIsım.setText("İsim: " + message.getMessageIsım());
        bildiriYas.setText("Yaş: " + message.getMessageYas());
        bildiriTel.setText("Tel: " + message.getMessageTel());


        Boolean esit=message.getMessageOnay().equals("1");
        Boolean esit2=message.getMessageOnay().equals("0");

        if(esit){
            listViewItem.setBackgroundColor(Color.CYAN);
        } else if(esit2)
        {
            listViewItem.setBackgroundColor(Color.RED);
        }
        return listViewItem;
    }

}
