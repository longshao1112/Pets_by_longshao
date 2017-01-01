/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new PetDbHelper(this);

        displayDatabaseInfo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayDatabaseInfo();

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        PetDbHelper mDbHelper = new PetDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PetEntry.COLUMN_PET_ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };

        String selection = null;

        String[] selectionArgs = null;

        String DASH_SEP = " - ";

        Cursor cursor = getContentResolver().query(
                PetEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );



        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).

            int idColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());

            displayView.append("\n\n" + PetEntry.COLUMN_PET_ID + DASH_SEP +
                    PetEntry.COLUMN_PET_NAME + DASH_SEP +
                    PetEntry.COLUMN_PET_BREED + DASH_SEP +
                    PetEntry.COLUMN_PET_GENDER + DASH_SEP +
                    PetEntry.COLUMN_PET_WEIGHT + "\n\n"
            );
            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                String currentGender = cursor.getString(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);

                displayView.append(currentId + DASH_SEP +
                        currentName + DASH_SEP +
                        currentBreed + DASH_SEP +
                        currentGender + DASH_SEP +
                        currentWeight + "\n"
                );
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

//    private void displayDatabaseInfo() {
//        String[] projection = {
//                PetEntry._ID,
//                PetEntry.COLUMN_PET_NAME,
//                PetEntry.COLUMN_PET_BREED,
//                PetEntry.COLUMN_PET_GENDER,
//                PetEntry.COLUMN_PET_WEIGHT
//        };
//
//        Cursor cursor = getContentResolver().query(
//                PetEntry.CONTENT_URI,
//                projection,
//                null,
//                null,
//                null
//        );
//        try {
//            //Figure out the column index of id name breed gender weight.
//            int idColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_ID);
//            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
//            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
//            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
//            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
//
//            String DASH_SEP = " - ";
//
//            // Display the number of rows in the Cursor (which reflects the number of rows in the
//            // pets table in the database).
//            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
//            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
//
//            displayView.append("\n\n" + PetEntry.COLUMN_PET_ID + DASH_SEP +
//                    PetEntry.COLUMN_PET_NAME + DASH_SEP +
//                    PetEntry.COLUMN_PET_BREED + DASH_SEP +
//                    PetEntry.COLUMN_PET_GENDER + DASH_SEP +
//                    PetEntry.COLUMN_PET_WEIGHT + "\n\n"
//            );
//
//            while (cursor.moveToNext()) {
//
//                int currentId = cursor.getInt(idColumnIndex);
//
//                String currentName = cursor.getString(nameColumnIndex);
//                String currentBreed = cursor.getString(breedColumnIndex);
//                String currentGender = cursor.getString(genderColumnIndex);
//                String currentWeight = cursor.getColumnName(weightColumnIndex);
//
//                displayView.append(currentId + DASH_SEP +
//                        currentName + DASH_SEP +
//                        currentBreed + DASH_SEP +
//                        currentGender + DASH_SEP +
//                        currentWeight + "\n"
//                );
//
//            }
//        } finally {
//            // Always close the cursor when you're done reading from it. This releases all its
//            // resources and makes it invalid.
//            cursor.close();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
