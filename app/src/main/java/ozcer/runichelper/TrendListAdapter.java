package ozcer.runichelper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ozcer on 2017-10-13.
 */

public class TrendListAdapter extends ArrayAdapter<String[]>{

    public TrendListAdapter(@NonNull Context context, String[][] trends) {
        super(context, R.layout.trend_row, trends);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater  = LayoutInflater.from(getContext());
        View trendRow = inflater.inflate(R.layout.trend_row, parent, false);
        String trendTime = getItem(position)[0];
        String trendDelta = getItem(position)[1];

        TextView tvTime = (TextView) trendRow.findViewById(R.id.trend_time);
        TextView tvDelta = (TextView) trendRow.findViewById(R.id.trend_delta);

        tvTime.setText(trendTime);
        tvDelta.setText(trendDelta);

        return trendRow;
    }
}
