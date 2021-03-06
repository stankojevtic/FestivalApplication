package com.example.festivalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.festivalapp.Helpers.BitmapConvertor;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalEditActivity extends AppCompatActivity {

    private EditText startDateEditText;
    private EditText endDateEditText;
    private EditText startTimeEditText;
    private EditText nameEditText;
    private EditText addressEditText;
    private EditText descriptionEditText;
    private Button saveButton;
    private Spinner spinnerFestivalTypes;
    private ArrayAdapter<FestivalType> festivalTypesAdapter;
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startDateOnDateListener;
    private DatePickerDialog.OnDateSetListener endDateOnDateListener;
    private List<FestivalType> festivalTypes;
    private Festival festival;
    private FestivalType festivalsFestivalType;
    private Button buttonChooseImage;
    private ImageView image;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_festival_layout);

        festival = (Festival) getIntent().getSerializableExtra("Festival");

        spinnerFestivalTypes = findViewById(R.id.dialog_festival_type);
        initializeEditText();
        initializeSpinner();
        initializeImage();

        if (festival != null) {
            initializeValues(festival);
        }

        final FestivalEditActivity thisActivity = this;

        saveButton = (Button) findViewById(R.id.Save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.length() == 0) {
                    nameEditText.setError("Enter Name");
                } else if (addressEditText.length() == 0) {
                    addressEditText.setError("Enter Address");
                } else if (startDateEditText.length() == 0) {
                    startDateEditText.setError("Enter Start Date");
                } else if (endDateEditText.length() == 0) {
                    endDateEditText.setError("Enter End Date");
                } else if (startTimeEditText.length() == 0) {
                    startTimeEditText.setError("Enter Start Time");
                } else if (image.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Please upload image for this festival.", Toast.LENGTH_SHORT).show();
                } else if (!isDateAfter(startDateEditText.getText().toString(),
                            endDateEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Start date can't be greater than end date.", Toast.LENGTH_SHORT).show();
                } else {
                    LatLng addressLatLng = getLocationFromAddress(FestivalEditActivity.this,
                            addressEditText.getText().toString());

                    if (addressLatLng == null) {
                        addressEditText.setError("Invalid Address");
                    } else {
                        spinnerFestivalTypes = (Spinner) findViewById(R.id.dialog_festival_type);
                        FestivalType ft = (FestivalType) spinnerFestivalTypes.getSelectedItem();

                        Bitmap bitmap123 = bitmap;

                        final Festival festivalToSave = new Festival(
                                nameEditText.getText().toString(),
                                startDateEditText.getText().toString(),
                                endDateEditText.getText().toString(),
                                startTimeEditText.getText().toString(),
                                addressEditText.getText().toString(),
                                String.valueOf(addressLatLng.latitude),
                                String.valueOf(addressLatLng.longitude),
                                ft.getId(),
                                descriptionEditText.getText().toString(),
                                BitmapConvertor.BitMapToString(bitmap));

                        new AlertDialog.Builder(thisActivity)
                                .setTitle("Save")
                                .setMessage("Do you really want to proceed?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (festival != null) {
                                            festivalToSave.id = festival.id;
                                            FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                                            Call<ResponseBody> call = service.updateFestival(festivalToSave);

                                            call.enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                                    if (!response.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                    finish();
                                                    Toast.makeText(getApplicationContext(), "Festival successfully updated.", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                                            Call<ResponseBody> call = service.createFestival(festivalToSave);

                                            call.enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                                    if (!response.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                    finish();
                                                    Toast.makeText(getApplicationContext(), "Festival successfully added.", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                })
                                .setNegativeButton("No", null).show();
                    }
                }
            }
        });
    }

    private void initializeValues(Festival festival) {
        nameEditText.setText(festival.getName());
        addressEditText.setText(festival.getAddress());
        image.setImageBitmap(BitmapConvertor.StringToBitMap(festival.getImage()));
        bitmap = BitmapConvertor.StringToBitMap(festival.getImage());
        //spinnerFestivalTypes.setSelection(festivalTypesAdapter.getPosition(festivalsFestivalType));
        startDateEditText.setText(festival.getStartDate());
        endDateEditText.setText(festival.getEndDate());
        startTimeEditText.setText(festival.getTimeStart());
        descriptionEditText.setText(festival.getDescription());
    }

    public boolean isDateAfter(String startDate, String endDate)
    {
        try
        {
            String myFormatString = "dd/MM/yy";
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date endD = df.parse(endDate);
            Date startD = df.parse(startDate);

            if (endD.after(startD) || (!endD.before(startD) && !endD.after(startD)))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private void initializeSpinner() {
        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<List<FestivalType>> call = service.getAllFestivalTypes();
        call.enqueue(new Callback<List<FestivalType>>() {
            @Override
            public void onResponse(@NonNull Call<List<FestivalType>> call, @NonNull Response<List<FestivalType>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }
                festivalTypes = response.body();
                festivalTypesAdapter = new
                        ArrayAdapter<FestivalType>(getApplicationContext(), R.layout.spinner_custom, festivalTypes);
                festivalTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFestivalTypes.setAdapter(festivalTypesAdapter);
            }

            @Override
            public void onFailure(Call<List<FestivalType>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeImage() {
        buttonChooseImage = (Button) findViewById(R.id.choose_image_button);
        image = (ImageView) findViewById(R.id.festival_image);
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 555);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 555 && resultCode == RESULT_OK && data != null)
        {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeEditText() {
        addressEditText = (EditText) findViewById(R.id.Address);
        nameEditText = (EditText) findViewById(R.id.Name);
        startDateEditText = (EditText) findViewById(R.id.StartDate);
        endDateEditText = (EditText) findViewById(R.id.EndDate);
        descriptionEditText = (EditText) findViewById(R.id.Description);
        startDateOnDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDate();
            }
        };

        endDateOnDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDate();
            }
        };

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FestivalEditActivity.this, startDateOnDateListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FestivalEditActivity.this, endDateOnDateListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startTimeEditText = (EditText) findViewById(R.id.StartTime);

        startTimeEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FestivalEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeEditText.setText(convertDate(selectedHour) + ":" + convertDate(selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    private void updateStartDate() {
        String dateFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDateEditText.setText(sdf.format(calendar.getTime()));
    }

    private void updateEndDate() {
        String dateFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        endDateEditText.setText(sdf.format(calendar.getTime()));
    }
}
