package razvan.com.traveljournal.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import razvan.com.traveljournal.R;

public class TripsViewHolder extends RecyclerView.ViewHolder {

    public ImageView tripImage;
    public TextView titleTextView;
    public TextView locationTextView;
    public TextView ratingTextView;

    public TripsViewHolder(@NonNull View itemView) {
        super(itemView);

        tripImage = itemView.findViewById(R.id.recycler_imageView);
        titleTextView = itemView.findViewById(R.id.recycler_title_textView);
        locationTextView = itemView.findViewById(R.id.recycler_location_textView);
        ratingTextView = itemView.findViewById(R.id.recycler_rating_textView);

    }
}
