package cat.iam.bocatas.app.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cat.iam.bocatas.app.R;

/**
 * Fragment que mostra el codi qr de l'usuari
 */

public class QRFragment extends Fragment {

    String urlQr;
    ImageView qrIv;

    public QRFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_qr, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.qrIv = getActivity().findViewById(R.id.qrImageView);
        Glide.with(getContext()).load(urlQr).into(qrIv);
    }

    public void setUrlQr(String urlQr) {
        this.urlQr = urlQr;
    }
}
