package cat.iam.bocatas.app.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.model.Product;
import cat.iam.bocatas.app.views.ProductActivity;

/**
 * Fragment principal que mostra el producte del dia i l'ultim producte afegit
 */

public class MainFragment extends Fragment {


    private final String TAG = "MainFragment";

    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        List<Product> products = AllInfoObjectContainer.getAllInfoObject().getProductList();

        Product product = null;
        final Product last = products.get(products.size()-1);


        for (Product p : products) {
            Log.d(TAG, "onCreateView: "+p.isOftheday());
            if (p.isOftheday()) product = p;
        }


        //Producte del dia
        if(product != null ) {
            cargarProducteDia(product, view);
        } else {
            product = products.get(new Random().nextInt(products.size()));
            cargarProducteDia(product, view);
        }

        //Producte afegit

        CardView llLast = view.findViewById(R.id.layoutLastProdAdded);
        TextView tvNameLast = view.findViewById(R.id.tvNameProductLast);
        TextView tvPriceLast = view.findViewById(R.id.tvPriceProductLast);
        ImageView ivImageLast = view.findViewById(R.id.ivImageProductLast);

        tvNameLast.setText(last.getName());
        tvPriceLast.setText(last.getPrice()+" €");
        Glide.with(this)
                .load(Constants.URL_SERVER+last.getUrlPhoto())
                .asBitmap()
                .into(ivImageLast);

        llLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProductActivity.class);
                i.putExtra("product", last);
                startActivity(i);
            }

        });

        return view;
    }

    private void cargarProducteDia(final Product product, View view) {

        CardView ll = view.findViewById(R.id.layoutProductDelDia);
        TextView tvNameDia = view.findViewById(R.id.tvNameProduct);
        TextView tvPriceDia = view.findViewById(R.id.tvPriceProduct);
        ImageView ivImageDia = view.findViewById(R.id.ivImageProduct);

        tvNameDia.setText(product.getName());
        tvPriceDia.setText(product.getPrice() + " €");

        Glide.with(this)
                .load(Constants.URL_SERVER+product.getUrlPhoto())
                .asBitmap()
                .into(ivImageDia);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProductActivity.class);
                i.putExtra("product", product);
                startActivity(i);
            }

        });
    }

}
