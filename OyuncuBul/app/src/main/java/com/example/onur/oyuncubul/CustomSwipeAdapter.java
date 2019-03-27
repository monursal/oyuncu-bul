package com.example.onur.oyuncubul;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Onur on 27.04.2018.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    private int[] image_resources ={R.drawable.haberi,R.drawable.haberii,R.drawable.haberiii };
    private String[] text_resources ={"Turnuvada gecenin sonuçları\n" +
            "Yasa Doğalgaz Cici Sokak 9-9 Real Gençlik\n" +
            "Aliağa Masterlar 5-0 Bayburtspor (hükmen)\n" +
            "S.S. 32 Nolu Kasalı Kam. Koop. 3-8 Poyraz Mücevherat","Güler Kuyumculuk “Come Back”\n"+
            "D grubundaki 3. Maçlar bu gece oynandı.\n" +
            "Gecenin ilk maçında Yörükler Derneği ve Ege Hakan karşı karşıya geldi. Son dakikaya kadar maç ortada geçerken, maçı 6-5’le kazanan taraf Yörükler Derneği oldu.\n" +
            "Gecenin ikinci maçı ise inanılmaz bir mücadeleye sahne oldu. Maça fırtına gibi başlayan 87 Spor 5-0 öne geçti. Çok çabuk toparlanan Güler Kuyumculuk aradaki farkı kapattı. Maçın son saniyesinde bulduğu golle mücadeleden 1 puanla ayrıldı ve maç 6-6 sona erdi.\n" +
            "Gecenin son maçında ise Era L.T.D, Ultraslan Aliağa karşınsında etkili bir oyun sergileyerek mücadeleden 11-3 galip ayrılan taraf oldu.\n" +
            "Turnuvada yarın maç oynanmayacak.\n" +
            "ÇÜNKÜ YARIN ŞAMPİYONLUK GÜNÜ","BİLGİLENDİRME\n" +
            "Aliağa Gençlik Turnuvasında\n" +
            "A grubunda yer alan akımlarının maçlarının\n" +
            "18 Nisan Çarşamba günü oynanacak olan\n" +
            "Aliağa Spor Fk- İzmir Spor maçı ile aynı güne denk gelmesinden dolayı A grubunda yer alan takımların isteği üzerine ertelenmiştir. Maçlar Perşembe ve Cuma günü oynanacaktır.\n" +
            "\n" +
            "19 Nisan Perşembe\n" +
            "Saat 19.00 Ç.S.K - Mehter\n" +
            "Saat 23.00 Sivas Beydili gençlik- Karakuzu\n" +
            "\n" +
            "20 Nisan Cuma\n" +
            "Saat 19.00 Uzunhasanlar- Karaköy\n" +
            "Saat 23.00 Balık evi -Fenerbahçe Taraftar Derneği"};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageHaber = item_view.findViewById(R.id.imagehaber);
        TextView  textHaber = item_view.findViewById(R.id.texthaber);
        imageHaber.setImageResource(image_resources[position]);
        textHaber.setText(text_resources[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
