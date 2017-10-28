package com.demodevices;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Superadmin1 on 28/10/2017.
 */

public class Permissions {

    public static boolean isGrantedPermissions(Context context, String permissionsType){
        int permission = ActivityCompat.checkSelfPermission(context,permissionsType);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static void verifyPermissions(Activity activity, String[] permissionsType){
        ActivityCompat.requestPermissions(activity,permissionsType,Constants.REQUEST_CODE_PERMISSION);
    }
}
