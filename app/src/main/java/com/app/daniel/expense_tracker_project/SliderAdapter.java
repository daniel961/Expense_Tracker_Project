package com.app.daniel.expense_tracker_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {


    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }


    //arrays
    public int[] slide_images = {
      R.drawable.example_1,
      R.drawable.example_2,
      R.drawable.example_3
    };

    public String[] slide_headings = {
      "מחיקת הוצאה",
      "מחיקת כל ההוצאות",
      "מצב הכנסות הוצאות כסף שנותר לבזבוז"
    };

    public String[] slide_desc = {
      "למחיקת הוצאה ספציפית יש ללחוץ לחיצה ארוכה על הוצאה ולאחר מכן לאשר את המחיקה",
      "כדי למחוק את כל ההוצאות יש ללחוץ על הכפתור התחתון ולאחר מכן לאשר את המחיקה חשוב לזוכר לא יהיה ניתן לשחזר את המחיקה",
      "בראש האפליקציה ניתן לצפות במצב ההכנסות וההוצאות ומכאן לדעת עוד כמה כסף נותר לנו החודש השתמשו בכספכם בצורה חכמה."
    };



    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView introduction_image = (ImageView)view.findViewById(R.id.introduction_image);
        TextView head_tv = (TextView)view.findViewById(R.id.head_tv);
        TextView desc_tv = (TextView)view.findViewById(R.id.desc_tv);
        TextView number_slide = (TextView)view.findViewById(R.id.number_slide);


        introduction_image.setImageResource(slide_images[position]);
        head_tv.setText(slide_headings[position]);
        desc_tv.setText(slide_desc[position]);

        if(position == 2){
            number_slide.setText("התחל להנות מהחסכון");
        }else {
            number_slide.setText("החלק לשקופית הבאה");
        }

        container.addView(view);



        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);



    }





}
