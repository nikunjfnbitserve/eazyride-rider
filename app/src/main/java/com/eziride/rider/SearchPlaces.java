package com.eziride.rider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class SearchPlaces extends AppCompatActivity {

    EditText Splaces;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public static String TAG="SearchPlace";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);
        Splaces=findViewById(R.id.splace);

        Splaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);


                    Intent intent= new Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.FULLSCREEN,fields
                    ).build(getApplicationContext());

                   startActivityForResult(intent,PLACE_AUTOCOMPLETE_REQUEST_CODE);

                }catch(Exception e)
                {
                    e.printStackTrace();

                }

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
