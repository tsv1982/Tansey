package com.example.ex_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ex_1.Entity.ImageNewsEntity;
import com.example.ex_1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<ImageNewsEntity> {

    private LayoutInflater inflater;
    private int layout;
    private List<ImageNewsEntity> imageList;
    Context context;

    public ImageAdapter(Context context, int resource, List<ImageNewsEntity> mesages) {
        super(context, resource, mesages);
        this.imageList = mesages;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public View getView(int position, View convertView, final ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        final ImageView imageNewsView = view.findViewById(R.id.image_list_view_News_for_dialog);

        imageNewsView.setOnClickListener(new View.OnClickListener() {
            private boolean isImageScaled = false;
            @Override
            public void onClick(View view) {  // увеличение картинки
                        if (!isImageScaled) view.animate().scaleX(1.4f).scaleY(1.4f).setDuration(500);
                        if (isImageScaled) view.animate().scaleX(1f).scaleY(1f).setDuration(500);
                        isImageScaled = !isImageScaled;

                }

        });

        ImageNewsEntity entity1 = imageList.get(0);

        String s = entity1.getURL();
        String[] arr = s.split(",");

           Picasso.with(view.getContext())
                    .load(arr[position].replaceAll(",", "").trim())
                    .placeholder(R.drawable.loading)   // заглушка во время загрузки
                    .error(R.drawable.loading_error)  // если еррор
                    .resize(1000, 800)
                    .into(imageNewsView);

        return view;
    }
}