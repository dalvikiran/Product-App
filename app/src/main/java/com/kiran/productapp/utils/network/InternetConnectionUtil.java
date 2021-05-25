package com.kiran.productapp.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.kiran.productapp.utils.Constants;


public class InternetConnectionUtil {

    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;

    //getting the connectivity status if the device is connnected on wifi or mobile
    public static int getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    //getting the connectivity status if the device is connnected on wifi or mobile
    public static String getConnectivityStatusString(Context context) {

        int conn = InternetConnectionUtil.getConnectivityStatus(context);

        String status = null;
        if (conn == InternetConnectionUtil.TYPE_WIFI) {
            //status = "Wifi enabled";
            status = Constants.CONNECT_TO_WIFI;
        } else if (conn == InternetConnectionUtil.TYPE_MOBILE) {
            //status = "Mobile data enabled";
            System.out.println(Constants.CONNECT_TO_MOBILE);
            status = getNetworkClass(context);
        } else if (conn == InternetConnectionUtil.TYPE_NOT_CONNECTED) {
            status = Constants.NOT_CONNECT;
        }

        return status;
    }

    //getting the network type whether it is wifi, 3g ,4g
    private static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info == null || !info.isConnected())
            return "-"; //not connected
        if(info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if(info.getType() == ConnectivityManager.TYPE_MOBILE){
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return "4G";
                default:
                    return "UNKNOWN";
            }
        }
        return "UNKNOWN";
    }
}
