package razvan.com.traveljournal;

import android.Manifest;
import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import razvan.com.traveljournal.utils.CustomDatePickerFragment;


public class AddTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final int SELECTPHOTO_REQUEST_CODE = 100;
    public static final int TAKEPHOTO_REQUEST_CODE = 110;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private EditText tripName;
    private EditText destination;
    private RadioGroup tripType;
    private Button startDateButton;
    private Button endDateButton;
    private TextView priceTextView;
    private SeekBar price;
    private RatingBar rating;
    private TextView imagePathTextView;


    private String mStartDate;
    private String mEndDate;
    private boolean currentDatePick;
    private String mPhotoPath;
    private Uri photoUri;



    public static final String TRIPNAME = "tripname";
    public static final String DESTINATION = "destination";
    public static final String TRIPTYPE = "triptype";
    public static final String STARTDATE = "startdate";
    public static final String ENDDATE = "enddate";
    public static final String PRICE = "price";
    public static final String RATING = "rating";
    public static final String PHOTOPATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        reqCameraAccess();

        tripName = findViewById(R.id.trip_name_editText);
        destination = findViewById(R.id.destination_editText);
        tripType = findViewById(R.id.trip_type_radioGroup);
        priceTextView = findViewById(R.id.seek_bar_textView);
        price = findViewById(R.id.price_seekBar);
        startDateButton = findViewById(R.id.start_date_button);
        endDateButton = findViewById(R.id.end_date_button);
        rating = findViewById(R.id.rating_bar);
        imagePathTextView = findViewById(R.id.image_path_textView);


        price.setMax(200);
        price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priceTextView.setText("Price (" + price.getProgress() * 10 + " EUR)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mStartDate = null;
        mEndDate = null;
        mPhotoPath = null;

    }

    public void btnSaveOnClick(View view) {
        //get data
        String mTripName = tripName.getText().toString();
        if(mTripName == null || mTripName.isEmpty()) {
            Toast.makeText(AddTripActivity.this, "Please enter a trip name", Toast.LENGTH_SHORT).show();
            return;
        }
        String mDestination = destination.getText().toString();
        if(mDestination == null || mDestination.isEmpty()) {
            Toast.makeText(AddTripActivity.this, "Please enter a destination", Toast.LENGTH_SHORT).show();
            return;
        }
        String mTripType = null;
        switch (tripType.getCheckedRadioButtonId()) {
            case R.id.city_break_radioButton:
                mTripType = "City Break";
                break;
            case R.id.seaside_radioButton:
                mTripType = "SeaSide";
                break;
            case R.id.mountains_radioButton:
                mTripType = "Mountains";
                break;
        }
        if(mTripType == null || mTripType.isEmpty()) {
            Toast.makeText(AddTripActivity.this, "Please enter select a trip type", Toast.LENGTH_SHORT).show();
            return;
        }

        if(price.getProgress() == 0) {
            Toast.makeText(AddTripActivity.this, "Please adjust the trip's price", Toast.LENGTH_SHORT).show();
            return;
        }
        String mPrice = Integer.toString(price.getProgress() * 10);
        String mRating = Float.toString(rating.getRating());

        if(mStartDate == null) {
            Toast.makeText(AddTripActivity.this, "Please enter a start date", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mEndDate == null) {
            Toast.makeText(AddTripActivity.this, "Please enter an end date", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("d/M/y");
        try {
            Date sDate = format.parse(mStartDate);
            Date eDate = format.parse(mEndDate);
            if (eDate.compareTo(sDate) < 0) {
                Toast.makeText(AddTripActivity.this, "The end date you entered occurs before the start date", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(mPhotoPath == null) {
            Toast.makeText(AddTripActivity.this, "Please select a photo", Toast.LENGTH_SHORT).show();
            return;
        }



        Intent intent = new Intent(AddTripActivity.this, NavigationDrawerActivity.class);
        intent.putExtra(TRIPNAME, mTripName);
        intent.putExtra(DESTINATION, mDestination);
        intent.putExtra(TRIPTYPE, mTripType);
        intent.putExtra(STARTDATE, mStartDate);
        intent.putExtra(ENDDATE, mEndDate);
        intent.putExtra(PRICE, mPrice);
        intent.putExtra(RATING, mRating);
        intent.putExtra(PHOTOPATH, mPhotoPath);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddTripActivity.this, NavigationDrawerActivity.class);
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    public void btnStartDatePickerOnClick(View view) {
        currentDatePick = false;
        DialogFragment newFragment = new CustomDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void btnEndDatePickerOnClick(View view) {
        currentDatePick = true;
        DialogFragment newFragment = new CustomDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        if (currentDatePick == false) {
            mStartDate = day + "/" + (month + 1) + "/" + year;
            startDateButton.setHint(mStartDate);

        } else {
            mEndDate = day + "/" + (month + 1) + "/" + year;
            endDateButton.setHint(mEndDate);
        }
    }


    public void btnSelectPhotoOnClick(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, SELECTPHOTO_REQUEST_CODE);
    }

    public void btnTakePhotoOnClick(View view) {

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        photoUri  = FileProvider.getUriForFile(this,"razvan.com.traveljournal.provider", photoFile);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        startActivityForResult(intent, TAKEPHOTO_REQUEST_CODE);

    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == SELECTPHOTO_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mPhotoPath = cursor.getString(columnIndex);
                cursor.close();

                imagePathTextView.setText(mPhotoPath);
            }
        } else if(requestCode == TAKEPHOTO_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                imagePathTextView.setText(mPhotoPath);
            }
        }
    }



    private void reqCameraAccess() {
        if (checkPermission()) {
            //main logic or main code
            // . write your main code to execute, It will execute if the permission is already given.
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
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
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
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
        new AlertDialog.Builder(AddTripActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
