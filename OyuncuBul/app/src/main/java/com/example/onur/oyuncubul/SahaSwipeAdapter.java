package com.example.onur.oyuncubul;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Onur on 12.05.2018.
 */

public class SahaSwipeAdapter extends PagerAdapter {
    private int[] image_resources2 ={R.drawable.enka,R.drawable.merkez,R.drawable.saka };
    private String[] text_resources ={"Enka \n Atatürk Mah., 360/1 Sok., Aliağa \n İzmir/Türkiye","Merkez\n Atatürk Cad., No:73B, Aliağa \n İzmir/Türkiye","Enka\n Atatürk Cad., No:73B, Aliağa \n (232)6165407"};

    private Context ctx;
    private LayoutInflater layoutInflater;


    public SahaSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return image_resources2.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.activity_saha_swipe,container,false);
        ImageView imageSaha = item_view.findViewById(R.id.imagesaha);
        TextView textSaha = item_view.findViewById(R.id.textsaha);
        imageSaha.setImageResource(image_resources2[position]);
        textSaha.setText(text_resources[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
