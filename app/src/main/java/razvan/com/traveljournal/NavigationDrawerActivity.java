package razvan.com.traveljournal;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import razvan.com.traveljournal.recyclerView.TripsAdapter;
import razvan.com.traveljournal.models.Trip;


public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSION_REQUEST_CODE = 201;
    private static final int ADD_NEW_TRIP = 301;
    private static final int LIMIT = 50;
    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MainActivity";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirestore;

    //Auth
    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;

    private RecyclerView tripsRecyclerView;
    CollectionReference trips;
    private Query mQuery;
    //private List<Trip> mTrips;
    private TripsAdapter tripsAdapter;


    private ImageView profileImage;
    private TextView profileName;
    private TextView profilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        reqStoragePerm();

        mUsername = ANONYMOUS;

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mEmail = mFirebaseUser.getEmail();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);



        View headerView = navigationView.getHeaderView(0);
        profileImage = headerView.findViewById(R.id.drawer_profile_imageView);
        profileName = headerView.findViewById(R.id.drawer_name_textView);
        profilEmail = headerView.findViewById(R.id.drawer_email_textView);
        Picasso.get().load(mPhotoUrl).fit().into(profileImage);
        profileName.setText(mUsername);
        profilEmail.setText(mEmail);

        //Firestore
        initFirestore();
        trips = mFirestore.collection("trips");

        //RecyclerView
        tripsRecyclerView = findViewById(R.id.trips_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tripsRecyclerView.setLayoutManager(layoutManager);
        //mTrips = getTrips();
        tripsAdapter = new TripsAdapter(mQuery, null);
        tripsRecyclerView.setAdapter(tripsAdapter);
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();

        // Get the 50 highest rated restaurants
        mQuery = mFirestore.collection("trips")
                .orderBy("tripName", Query.Direction.ASCENDING)
                .limit(LIMIT);
    }

    private void reqStoragePerm() {

        if (checkPermission()) {

        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(NavigationDrawerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.nav_home) {

            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_favourite) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_email) {

        } else if (id == R.id.nav_signout) {

            mFirebaseAuth.signOut();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mUsername = ANONYMOUS;
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



//    private List<Trip> getTrips() {
//        List<Trip> trips = new ArrayList<>();
//        trips.add(new Trip("Winter", "Paris", "2016", "a"));
//        trips.add(new Trip("Spring", "London", "2017","a"));
//        trips.add(new Trip("Summer", "Berlin", "2017", "a"));
//        trips.add(new Trip("Autumn", "Cairo", "2017", "a"));
//        trips.add(new Trip("Winter", "Madrid", "2017", "a"));
//        trips.add(new Trip("Spring", "Sibiu", "2018", "a"));
//        trips.add(new Trip("Summer", "Barcelona", "2018", "a"));
//        trips.add(new Trip("Autumn", "Roma", "2018", "a"));
//        trips.add(new Trip("Winter", "Milano", "2018", "a"));
//        trips.add(new Trip("Spring", "Viena", "2019", "a"));
//        return trips;
//    }


    public void btnGoToOnClick(View view) {
        Intent intent = new Intent(NavigationDrawerActivity.this, AddTripActivity.class);
        startActivityForResult(intent, ADD_NEW_TRIP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_NEW_TRIP) {
            if(resultCode == Activity.RESULT_OK) {
                String mTripName = data.getStringExtra(AddTripActivity.TRIPNAME);
                String mDestination = data.getStringExtra(AddTripActivity.DESTINATION);
                String mTripType = data.getStringExtra(AddTripActivity.TRIPTYPE);
                String mStartDate = data.getStringExtra(AddTripActivity.STARTDATE);
                String mEndDate = data.getStringExtra(AddTripActivity.ENDDATE);
                String mPrice = data.getStringExtra(AddTripActivity.PRICE);
                String mRating = data.getStringExtra(AddTripActivity.RATING);
                String mImagePath = data.getStringExtra(AddTripActivity.PHOTOPATH);

                int mPriceToInt = Integer.parseInt(mPrice);
                SimpleDateFormat format = new SimpleDateFormat("d/M/y");
                Date mStartDateToDate = null;
                Date mEndDateToDate = null;
                try {
                    mStartDateToDate = format.parse(mStartDate);
                    mEndDateToDate = format.parse(mEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                double mRatingToDouble = Double.parseDouble(mRating);

                Trip t = new Trip(mTripName, mDestination, mTripType, mPriceToInt, mStartDateToDate, mEndDateToDate, mRatingToDouble, mImagePath);
                trips.add(t);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}