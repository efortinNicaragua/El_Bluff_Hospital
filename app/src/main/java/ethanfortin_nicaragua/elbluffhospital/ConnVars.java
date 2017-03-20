package ethanfortin_nicaragua.elbluffhospital;

/**
 * Created by wildcat on 2/6/2017.
 * Based on tutorial written by Belel Khan - SimplifiedCoding.net
 */

public class ConnVars {

    // IP Addresses for PHP Scripts
    public static final String URL_FETCH_SPECIFIC_DRUG = "http://192.168.0.100/android_connect/fetch_druginfo_row.php";
    public static final String URL_FETCH_DRUGINFO_ALL = "http://192.168.0.100/android_connect/fetch_druginfo_all.php";
    public static final String URL_FETCH_SHIPMENT = "http://192.168.0.100/android_connect/fetch_shipment_row.php";

    public static final String URL_ADD_SHIPMENT = "http://192.168.0.100/android_connect/add_shipment_row.php";

    // JSON Tags

    public static final String TAG_SUCCESS = "success";

    public static final String TAG_DRUGINFO = "druginfo";
    public static final String TAG_DRUGINFO_ID = "drugid";
    public static final String TAG_DRUGINFO_NAME = "drugname";
    public static final String TAG_DRUGINFO_QUANT = "drugtotal";

    public static final String TAG_SHIPMENT = "shipment";
    public static final String TAG_SHIPMENT_SHIPDATE = "shipdate";
    public static final String TAG_SHIPMENT_DRUGID = "drugid";
    public static final String TAG_SHIPMENT_DRUGNAME = "drugname";
    public static final String TAG_SHIPMENT_SHIPQUANT = "shipquant";
    public static final String TAG_SHIPMENT_DRUGINCR = "drugincr";

}
