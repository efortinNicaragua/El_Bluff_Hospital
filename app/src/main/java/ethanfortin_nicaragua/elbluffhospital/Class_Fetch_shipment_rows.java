package ethanfortin_nicaragua.elbluffhospital;

import java.util.Date;

/**
 * Created by Wildcat on 1/3/2017.
 */

public class Class_Fetch_shipment_rows {
    public String drugid;
    public String drugname;
    public int shipquant;
    public String shipdate;
    public Class_Fetch_shipment_rows(){
        super();
    }

    public Class_Fetch_shipment_rows(String shipdate, String drugid, String drugname, int shipquant) {

        super();
        this.drugid = drugid;
        this.drugname=drugname;
        this.shipquant =shipquant;
        this.shipdate=shipdate;

    }
    public String getDrugID() { return drugid; }
    public String getDrugName() { return drugname; }
    public int getShipQuant() { return shipquant; }
    public String getShipDate(){return shipdate;}
    public String getAllData(){
        String all = drugid + " " + drugname + " " + shipquant + " "+ shipdate;
        return all;
    }
}
