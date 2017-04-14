package ethanfortin_nicaragua.elbluffhospital;

/**
 * Created by wildcat on 2/6/2017.
 * Based on tutorial written by Belel Khan - SimplifiedCoding.net
 */

public class ConnVars {

    // IP Addresses for PHP Scripts
    public static final String URL_FETCH_SPECIFIC_DRUG = "http://192.168.1.4/android_connect/fetch_druginfo_row.php";
    public static final String URL_FETCH_DRUGINFO_ALL = "http://192.168.0.100/android_connect/fetch_druginfo_all.php";
    public static final String URL_FETCH_SHIPMENT = "http://192.168.0.100/android_connect/fetch_shipment_rows.php";
    public static final String URL_FETCH_PATIENT_GENERAL_INFO="http://10.0.0.96/android_connect/fetch_patientinfo_row.php";
    public static final String URL_FETCH_PAT_RX = "http://192.168.1.4/android_connect/fetch_prescription_row.php";
    public static final String URL_ADD_PRESCRIPTION="http://10.0.0.96/android_connect/add_shipment_row.php";
    public static final String URL_FETCH_PAT_VISIT="http://10.0.0.96/android_connect/fetch_vh_rx_row.php";
    public static final String URL_ADD_PATIENTINFO_ROW="http://10.0.0.96/android_connect/add_patientinfo_row.php";
    // JSON Tags!

    public static final String TAG_SUCCESS = "success";

    public static final String TAG_DRUGINFO = "druginfo";
    public static final String TAG_DRUGINFO_ID = "drugid";
    public static final String TAG_DRUGINFO_NAME = "drugname";
    public static final String TAG_DRUGINFO_QUANT = "drugtotal";

    public static final String TAG_PATIENTINFO= "patientinfo";
    public static final String TAG_NEWPAT_ERRORMESSAGES="New_ErrorMessages";

    public static final String TAG_SHIPMENT = "shipments";
    public static final String TAG_SHIPMENT_SHIPDATE = "shipdate";
    public static final String TAG_SHIPMENT_DRUGID = "drugid";
    public static final String TAG_SHIPMENT_DRUGNAME = "drugname";
    public static final String TAG_SHIPMENT_SHIPQUANT = "shipquant";
    public static final String TAG_SHIP_ERRORMESSAGES="Ship_ErrorMessages";

    public static final String TAG_PRESCRIPTIONS = "prescription";
    public static final String TAG_PRESCRIPTIONS_RXID = "rxid";
    public static final String TAG_PRESCRIPTIONS_DRUGID = "drugid";
    public static final String TAG_PRESCRIPTIONS_TRANSDATE = "transdate";
    public static final String TAG_PRESCRIPTIONS_QUANTITY = "quantity";
    public static final String TAG_PRESCRIPTIONS_PATID = "patid";
    public static final String TAG_PRESCRIPTIONS_DIRECTIONS = "directions";
    public static final String TAG_PRESCRIPTIONS_DURATION = "duration";
    public static final String TAG_PRESCRIPTIONS_DOCTOR = "doctor";
    public static final String TAG_PRESCRIPTIONS_SYMPTOMS = "symptoms";
    //The drugname will have to come from a join in the php script
    public static final String TAG_PRESCRIPTIONS_DRUGNAME = "drugname";

    public static final String TAG_VISIT_HISTORY = "visithistory";
    public static final String TAG_VISITS_PATID = "patid";
    public static final String TAG_VISITS_VISITDATE = "visitdate";
    public static final String TAG_VISITS_REASON = "reason";
    public static final String TAG_VISITS_DOCTOR = "doctor";
    //this one needs to come from a join in the php script to the prescriptions table
    public static final String TAG_VISITS_RXID = "rxid";

}
