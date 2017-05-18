package ethanfortin_nicaragua.elbluffhospital;

/**
 * Created by wildcat on 2/6/2017.
 * Based on tutorial written by Belel Khan - SimplifiedCoding.net
 */

public class ConnVars {

    // Tol Basement May 15 - 153.104.62.23
    // Bartley May 16 - 192.168.1.2

    // Image Upload Addresses - 192.168.1.2
    public static final String URL_UPLOAD = "http://192.168.1.3/image_upload/upload.php";
    public static final String URL_IMAGES = "http://192.168.1.3/image_upload/get_image.php";


    //Tag values to read from json
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";


    // IP Addresses for PHP Scripts
    public static final String URL_FETCH_SPECIFIC_DRUG = "http://192.168.1.3/android_connect/fetch_druginfo_row.php";
    public static final String URL_FETCH_DRUGINFO_ALL = "http://192.168.1.3/android_connect/fetch_druginfo_all.php";
    public static final String URL_FETCH_SHIPMENT = "http://192.168.1.3/android_connect/fetch_shipment_rows.php";
    public static final String URL_FETCH_PATIENTINFO_ROW = "http://192.168.1.3/android_connect/fetch_patientinfo_row.php";
    public static final String URL_FETCH_PAT_RX = "http://192.168.1.3/android_connect/fetch_prescription_row.php";
    public static final String URL_FETCH_PAT_VISIT = "http://192.168.1.3/android_connect/fetch_vh_rx_row.php";
    public static final String URL_ADD_PRESCRIPTION = "http://192.168.1.3/android_connect/add_shipment_row.php";
    public static final String URL_ADD_PATIENTINFO_ROW = "http://192.168.1.3/android_connect/add_patientinfo_row.php";
    public static final String URL_UPDATE_DRUG_RX_ROW="http://192.168.1.3/android_connect/update_drug_rx_row.php";
    public static final String URL_ADD_VH_ROW="http://192.168.1.3/android_connect/add_vh_row.php";
  // JSON Tags!
    public static final String TAG_SUCCESS = "success";

    public static final String TAG_DRUGINFO = "druginfo";
    public static final String TAG_DRUGINFO_ID = "drugid";
    public static final String TAG_DRUGINFO_NAME = "drugname";
    public static final String TAG_DRUGINFO_QUANT = "drugtotal";

    public static final String TAG_PATIENTINFO= "patientinfo";
    public static final String TAG_NEWPAT_ERRORMESSAGES="New_ErrorMessages";
    public static final String TAG_PATIENTINFO_ID = "patid";
    public static final String TAG_PATIENTINFO_NAME = "patname";
    public static final String TAG_PATIENTINFO_ADDRESS = "address";
    public static final String TAG_PATIENTINFO_TELEPHONE = "telephone";
    public static final String TAG_PATIENTINFO_DOB = "dob";
    public static final String TAG_PATIENTINFO_GENDER = "gender";
    public static final String TAG_PATIENTINFO_MARSTAT = "marstat";
    public static final String TAG_PATIENTINFO_CHILDREN = "children";
    public static final String TAG_PATIENTINFO_HEIGHT = "height";
    public static final String TAG_PATIENTINFO_WEIGHT = "weight";
    public static final String TAG_PATIENTINFO_ALLERGIES = "allergies";
    public static final String TAG_PATIENTINFO_MEDCOND = "medcond";
    public static final String TAG_PATIENTINFO_INVPID = "invpid";

    public static final String TAG_SHIPMENT = "shipment";
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
    public static final String TAG_NEWPRE_ERRORMESSAGES="Prescription_ErrorMessages";
    //The drugname will have to come from a join in the php script
    public static final String TAG_PRESCRIPTIONS_DRUGNAME = "drugname";

    public static final String TAG_VISITHISTORY = "visithistory";
    public static final String TAG_VISITHISTORY_VISITID = "visitid";
    public static final String TAG_VISITHISTORY_PATID = "patid";
    public static final String TAG_VISITHISTORY_VISITDATE = "visitdate";
    public static final String TAG_VISITHISTORY_REASON = "reason";
    public static final String TAG_VISITHISTORY_DOCTOR = "doctor";
    public static final String TAG_VISITHISTORY_ERRORMESSAGES="VH_ErrorMessages";
    //this one needs to come from a join in the php script to the prescriptions table
    public static final String TAG_VISITS_RXID = "rxid";

}
//1!!!s