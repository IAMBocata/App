package cat.iam.bocatas.app.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.Product;
import cat.iam.bocatas.app.views.ProductActivity;

/**
 * L'adaptador de la llista de productes, aquesta classe ens gestiona com mostrar la informació de la llista de productes
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Product> products;
    private Activity activity;

    public ProductsAdapter(List<Product> products, Activity activity) {
        this.products = products;
        this.activity = activity;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {

        Product p = products.get(position);

        holder.productName.setText(p.getName());

        if (p.isLiked()) {
            holder.like.setBackground(activity.getDrawable(R.drawable.heartfull));
        }

        Glide.with(activity.getApplicationContext()).load(Constants.URL_SERVER + p.getUrlPhoto()).into(holder.wallpaperCard);

        holder.wallpaperCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity.getApplicationContext(), ProductActivity.class);

                i.putExtra("product", products.get(position));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(i, ActivityOptionsCompat.makeSceneTransitionAnimation(
                            activity, view, activity.getResources().getString(R.string.transitionname_picture)).toBundle());
                } else {
                    activity.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    protected class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView wallpaperCard;
        TextView productName;
        CheckBox like;

        public ProductViewHolder(View itemView) {
            super(itemView);

            wallpaperCard   = (ImageView) itemView.findViewById(R.id.pictureCard);
            productName     = (TextView) itemView.findViewById(R.id.productNameCard);
            like            = (CheckBox) itemView.findViewById(R.id.likeCheckCard);
        }
    }
}
