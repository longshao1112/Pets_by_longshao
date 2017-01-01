package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by longshao on 2016/12/29.
 */

public final class PetContract {

    private PetContract(){}

    /**
     * The "CONTENT AUTHORITY" is a name for the entire contact provider, similar to the
     * relationship of between of the domain name and a website. A convenient string to
     * use for the connect authority is the package name for the app, which is guaranteed
     * to be uniqued on the device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the contact the content provider
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path(appended to base contact URI for possible URI's).
     */
    public static final String PATH_PETS = "pets";

    public static abstract class PetEntry implements BaseColumns{

        /**
         * The content URI to access the pets' data in the provider.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        public static final String TABLE_NAME = "pets";

        public static final String COLUMN_PET_ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
