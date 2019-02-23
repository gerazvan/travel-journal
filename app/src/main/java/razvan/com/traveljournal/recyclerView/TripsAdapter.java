package razvan.com.traveljournal.recyclerView;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.io.File;
import java.util.List;

import razvan.com.traveljournal.R;
import razvan.com.traveljournal.models.Trip;

public class TripsAdapter extends FirestoreAdapter<TripsViewHolder> {


    public interface OnTripSelectedListener {

        void onTripSelected(DocumentSnapshot trip);

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
    public void onBindViewHolder(@NonNull TripsViewHolder tripsViewHolder, int i) {

        Trip currentTrip = getSnapshot(i).toObject(Trip.class);

        String[] startDateParts = currentTrip.getStartDate().toString().split("/");
        tripsViewHolder.titleTextView.setText(currentTrip.getTripName() + " " + startDateParts[2]);
        tripsViewHolder.locationTextView.setText(currentTrip.getDestination());

        File imageFile = new File(currentTrip.getImagePath());
        if(imageFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(currentTrip.getImagePath());
                tripsViewHolder.tripImage.setImageBitmap(myBitmap);
            }

    }



}