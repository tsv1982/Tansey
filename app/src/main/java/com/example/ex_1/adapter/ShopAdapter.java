package com.example.ex_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex_1.Entity.ShopEntity;
import com.example.ex_1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopAdapter extends ArrayAdapter<ShopEntity> {

    private LayoutInflater inflater;
    private int layout;
    private List<ShopEntity> shopList;

    public ShopAdapter(Context context, int resource, List<ShopEntity> shopList) {
        super(context, resource, shopList);
        this.shopList = shopList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView imageShop = view.findViewById(R.id.IV_shop_image);
        TextView nameShop = view.findViewById(R.id.TV_shop_name);
        TextView sizeShop = view.findViewById(R.id.TV_size);
        TextView priceShop = view.findViewById(R.id.TV_price);
        TextView abautShop = view.findViewById(R.id.TV_shop_abaut);

        ShopEntity shopEntity = shopList.get(position);

        nameShop.setText(shopEntity.getNameShop());
        sizeShop.setText(shopEntity.getSizeShop());
        priceShop.setText(shopEntity.getPriseShop());
        abautShop.setText(shopEntity.getAbautShop());

        Picasso.with(view.getContext())
                .load(shopEntity.getUrlFotoShop())
                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                .error(R.drawable.loading_error)  // если еррор
                .into(imageShop);

        return view;
    }
}
