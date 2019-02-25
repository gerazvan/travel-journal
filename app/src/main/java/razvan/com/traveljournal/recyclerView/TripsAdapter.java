package razvan.com.traveljournal.recyclerView;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.io.File;
import java.util.List;

import razvan.com.traveljournal.NavigationDrawerActivity;
import razvan.com.traveljournal.R;
import razvan.com.traveljournal.models.Trip;

public class TripsAdapter extends FirestoreAdapter<TripsViewHolder> {


    public interface OnTripSelectedListener {

        void onTripSelected(DocumentSnapshot trip);

        void onTripLongPressed(DocumentSnapshot trip);

        void onIconPressed(DocumentSnapshot trip, ImageView iconView);

    }

    private OnTripSelectedListener mListener;

    public TripsAdapter(Query query, OnTripSelectedListener listener) {
        super(query);
        mListener = listener;
    }



    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_item, viewGroup, false);
        return new TripsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripsViewHolder tripsViewHolder, final int i) {

        Trip currentTrip = getSnapshot(i).toObject(Trip.class);

        String[] startDateParts = currentTrip.getStartDate().toString().split(" ");
        tripsViewHolder.titleTextView.setText(currentTrip.getTripName() + " " + startDateParts[startDateParts.length - 1]);
        tripsViewHolder.locationTextView.setText(currentTrip.getDestination());
        tripsViewHolder.ratingTextView.setText(currentTrip.getRating() + " / 5.0");

        File imageFile = new File(currentTrip.getImagePath());
        if(imageFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(currentTrip.getImagePath());
                tripsViewHolder.tripImage.setImageBitmap(myBitmap);
                tripsViewHolder.tripImage.setClipToOutline(true);
        }
        if((boolean)getSnapshot(i).get("isFavourite")) {
            tripsViewHolder.bookmarkedIcon.setImageResource(R.drawable.ic_bookmarked);
            Log.e("Aici", currentTrip.getIsFavourite() + "");
        } else {
            tripsViewHolder.bookmarkedIcon.setImageResource(R.drawable.ic_bookmarked_not);
            Log.e("Aici", currentTrip.getIsFavourite() + "");
        }


        // Click listener
        tripsViewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onTripSelected(getSnapshot(i));
                }
            }
        });
        tripsViewHolder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mListener != null) {
                    mListener.onTripLongPressed(getSnapshot(i));
                }
                return true;
            }
        });

        tripsViewHolder.bookmarkedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onIconPressed(getSnapshot(i), tripsViewHolder.bookmarkedIcon);
                }
            }
        });


    }



}
