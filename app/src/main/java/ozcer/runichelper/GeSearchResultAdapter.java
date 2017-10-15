package ozcer.runichelper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by ozcer on 2017-10-14.
 */

public class GeSearchResultAdapter extends ArrayAdapter<String[]>{

    public GeSearchResultAdapter(@NonNull Context context, ArrayList<String[]> searchResult) {
        super(context, R.layout.ge_search_result_row, searchResult);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater  = LayoutInflater.from(getContext());
        View resultRow = inflater.inflate(R.layout.ge_search_result_row, parent, false);

        ImageView ivItemImage = (ImageView) resultRow.findViewById(R.id.geSearch_itemImage);
        TextView tvName = (TextView) resultRow.findViewById(R.id.geSearch_itemName);
        TextView tvPrice = (TextView) resultRow.findViewById(R.id.geSearch_itemPrice);

        String imageUrl = getItem(position)[0];
        String name = getItem(position)[1];
        String price = getItem(position)[2];
        String id = getItem(position)[3];

        Picasso.with(this.getContext()).load(imageUrl).into(ivItemImage);
        tvName.setText(name);
        tvPrice.setText(price);

        return resultRow;
    }
}

