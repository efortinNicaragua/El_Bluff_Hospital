package ethanfortin_nicaragua.elbluffhospital;

/**
 * Created by Wildcat on 12/3/2016.
 */

public class Class_FetchAllDrugInfo {
    public String drugid;
    public String drugname;
    public int drugtotal;
    public Class_FetchAllDrugInfo(){
        super();
    }

public Class_FetchAllDrugInfo(String drugid, String drugname, int drugtotal) {

        super();
        this.drugid = drugid;
        this.drugname=drugname;
        this.drugtotal =drugtotal;

    }
    public String getDrugID() { return drugid; }
    public String getDrugName() { return drugname; }
    public int getDrugTotal() { return drugtotal; }
    public String getAllData(){String all= drugid+ " "+ drugname + " "+drugtotal; return all;}
}