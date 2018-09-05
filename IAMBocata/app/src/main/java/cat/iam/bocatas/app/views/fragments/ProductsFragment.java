package cat.iam.bocatas.app.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.adapters.ProductsAdapter;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.model.Product;

/**
 * Fragment que mostra el llistat de productes
 */

public class ProductsFragment extends Fragment {

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    ProductsAdapter adapter;

    List<Product> products;

    public ProductsFragment() {

    }

    public void setProducts(List<Product> products) {
        this.products = AllInfoObjectContainer.getAllInfoObject().getProductList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //addFakeProducts();
        prepareRecyclerView();
    }

    private void prepareRecyclerView() {
        rv = (RecyclerView) getActivity().findViewById(R.id.recyclerViewProducts);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new ProductsAdapter(products, getActivity());
        rv.setAdapter(adapter);
    }

    public void addFakeProducts() {
        Product p = new Product("Hamburguesaka", "hamburguesaka", 2.15f, null, null);
        p.setUrlPhoto("https://unareceta.com/wp-content/uploads/2016/10/hamburguesa.jpg");
        products.add(p);
        products.add(p);
        products.add(p);
        products.add(p);
        products.add(p);
    }
}
