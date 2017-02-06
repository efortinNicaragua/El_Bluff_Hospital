package ethanfortin_nicaragua.elbluffhospital;

/**
 * Created by wildcat on 2/6/2017.
 * Based on tutorial written by Belel Khan - SimplifiedCoding.net
 */

public class ConnVars {

    // IP Addresses for PHP Scripts
    public static final String URL_FETCH_SPECIFIC_DRUG_NAME = "http://192.168.0.101/android_connect/fetch_druginfo_row_name.php";
    public static final String URL_FETCH_SPECIFIC_DRUG_ID = "http://192.168.0.101/android_connect/fetch_druginfo_row_id.php";

    // Keys used to to send request to scripts

    // JSON Tags
    public static final String TAG_JSON_ARRAY = "result";

    public static final String TAG_DRUGINFO_ID = "drugid";
    public static final String TAG_DRUGINFO_NAME = "drugname";
    public static final String TAG_DRUGINFO_QUANT = "drugtotal";

    //
}
