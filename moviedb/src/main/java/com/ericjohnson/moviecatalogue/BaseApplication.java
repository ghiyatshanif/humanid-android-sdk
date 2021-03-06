package com.ericjohnson.moviecatalogue;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.nbs.humanidui.util.ContextProvider;


/**
 * Created by Dimas Prakoso on 26/11/2019.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        ContextProvider.initialize(this);
    }


}
