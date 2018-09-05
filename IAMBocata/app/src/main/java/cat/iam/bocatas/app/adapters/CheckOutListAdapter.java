package cat.iam.bocatas.app.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.CheckOutObject;
import cat.iam.bocatas.app.views.ProductActivity;
import cat.iam.bocatas.app.views.fragments.CheckOutFragment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * L'adaptador de la llista de compra, aquesta classe ens permet mostrar la informacio de la llista de compra en un recycler view.
 */

public class CheckOutListAdapter extends RecyclerView.Adapter<CheckOutListAdapter.CheckOutItemViewHolder> {

    private List<CheckOutObject> checkOutObjects;
    private Activity activity;
    private CheckOutFragment fragment;

    public CheckOutListAdapter(List<CheckOutObject> checkOutObjects, Activity activity, CheckOutFragment fragment) {
        this.checkOutObjects = checkOutObjects;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public CheckOutItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new CheckOutItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CheckOutItemViewHolder holder, final int position) {

        final CheckOutObject coo = checkOutObjects.get(position);

        Glide.with(activity).load(Constants.URL_SERVER + coo.getProduct().getUrlPhoto()).into(holder.circleImageView);

        holder.productName.setText(coo.getProduct().getName());
        holder.productPrice.setText(coo.getProduct().getPrice() + " €");
        holder.productQuantity.setText(coo.getQuantity()+"");

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coo.addOne();
                update(holder, coo);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coo.removeOne();

                if (coo.getQuantity() <= 0) {
                    checkOutObjects.remove(position);
                }

                update(holder, coo);
            }
        });
    }

    private void update(CheckOutItemViewHolder holder, CheckOutObject coo) {
        holder.productQuantity.setText(coo.getQuantity()+"");
        fragment.prepareTotal();
    }

    @Override
    public int getItemCount() {
        return checkOutObjects.size();
    }

    protected class CheckOutItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView productName;
        TextView productPrice;
        TextView productQuantity;

        ImageButton add;
        ImageButton remove;

        public CheckOutItemViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.checkOutCardViewImageView);

            productName     = (TextView) itemView.findViewById(R.id.checkOutCardViewProductName);
            productPrice    = (TextView) itemView.findViewById(R.id.checkOutCardViewProductPrice);
            productQuantity = (TextView) itemView.findViewById(R.id.checkOutProductQuantity);

            add         = (ImageButton) itemView.findViewById(R.id.checkOutAddItemButton);
            remove      = (ImageButton) itemView.findViewById(R.id.checkOutRemoveItemButton);
        }
    }
}
