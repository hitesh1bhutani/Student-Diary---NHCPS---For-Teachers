package com.example.hitesh1bhutani.studentdiary_nhcps_forteachers;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by hitesh1bhutani on 3/31/2017.
 */

public class Subjects extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String URL = bundle.getString(getResources().getString(R.string.key_classNameURL));
    }
}
