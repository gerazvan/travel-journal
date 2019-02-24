package razvan.com.traveljournal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.io.File;

import razvan.com.traveljournal.models.Trip;
import razvan.com.traveljournal.recyclerView.TripsAdapter;

public class ViewTripActivity extends AppCompatActivity  implements EventListener<DocumentSnapshot>  {

    private static final String TAG = "TripDetail";

    private ImageView tripImage;
    private TextView destination;
    private TextView tripName;
    private TextView tripType;


    private FirebaseFirestore mFirestore;
    private DocumentReference mTripRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);

        tripImage = findViewById(R.id.trip_image_viewTrip);
        destination = findViewById(R.id.trip_location_viewTrip);
        tripName = findViewById(R.id.trip_name_viewTrip);
        tripType = findViewById(R.id.trip_type_viewTrip);

        String tripId= getIntent().getExtras().getString(NavigationDrawerActivity.TRIP_ID);
        String dbId = getIntent().getExtras().getString(NavigationDrawerActivity.DB_ID);

        // Initialize Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // Get reference to the trip
        mTripRef = mFirestore.collection(dbId).document(tripId);
    }

    @Override
    public void onStart() {
        super.onStart();
        mTripRef.addSnapshotListener(this);
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "restaurant:onEvent", e);
            return;
        }

        onTripLoaded(snapshot.toObject(Trip.class));
    }

    private void onTripLoaded(Trip trip) {
        File imageFile = new File(trip.getImagePath());
        if(imageFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(trip.getImagePath());
            tripImage.setImageBitmap(myBitmap);
            destination.setText(trip.getDestination());
            tripName.setText(trip.getTripName());
            tripType.setText(trip.getTripType());
        }
    }

}
