package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 12/7/2016.
 */

public class Class_FetchDruginfoRow {
    public String drugid;
    public String drugname;
    public int drugtotal;

    public Class_FetchDruginfoRow(String drugid, String drugname, int drugtotal) {

        super();
        this.drugid = drugid;
        this.drugname = drugname;
        this.drugtotal = drugtotal;

    }

    public String getDrugID() { return drugid; }
    public String getDrugName() { return drugname; }
    public int getDrugTotal() { return drugtotal; }
    public String getAllData(){
        String all = drugid + " " + drugname + " " + drugtotal;
        return all;
    }
}
