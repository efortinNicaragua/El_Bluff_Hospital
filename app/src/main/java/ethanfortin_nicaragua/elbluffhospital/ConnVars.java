package ethanfortin_nicaragua.elbluffhospital;

/**
 * Created by wildcat on 2/6/2017.
 * Based on tutorial written by Belel Khan - SimplifiedCoding.net
 */

public class ConnVars {

    // IP Addresses for PHP Scripts
    public static final String URL_FETCH_SPECIFIC_DRUG = "http://192.168.0.100/android_connect/fetch_druginfo_row.php";
    public static final String URL_FETCH_DRUGINFO_ALL = "http://192.168.0.100/android_connect/fetch_druginfo_all.php";
    public static final String URL_FETCH_SHIPMENTS = "http://192.168.0.100/android_connect/fetch_shipment_rows.php";
    public static final String URL_FETCH_PATIENT_GENERAL_INFO="http://192.168.0.100/android_connect/fetch_patientinfo_row.php";

    // JSON Tags

    public static final String TAG_DRUGINFO = "druginfo";
    public static final String TAG_DRUGINFO_ID = "drugid";
    public static final String TAG_DRUGINFO_NAME = "drugname";
    public static final String TAG_DRUGINFO_QUANT = "drugtotal";
    public static final String TAG_PatientInfo= "patientinfo";

    public static final String TAG_SHIPMENTS = "shipments";
    public static final String TAG_SHIPMENTS_SHIPDATE = "shipdate";
    public static final String TAG_SHIPMENTS_DRUGID = "drugid";
    public static final String TAG_SHIPMENTS_DRUGNAME = "drugname";
    public static final String TAG_SHIPMENTS_SHIPQUANT = "shipquant";

    //
}
