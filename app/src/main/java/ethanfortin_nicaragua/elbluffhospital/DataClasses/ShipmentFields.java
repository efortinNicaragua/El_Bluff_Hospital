package ethanfortin_nicaragua.elbluffhospital.DataClasses;

import java.util.Date;

/**
 * Created by Wildcat on 1/3/2017.
 */

public class ShipmentFields {
    public String drugid;
    public String drugname;
    public int shipquant;
    public String shipdate;
    public String expdate;
    public ShipmentFields(){
        super();
    }

    public ShipmentFields(String shipdate, String drugid, String drugname, int shipquant, String expdate) {

        super();
        this.drugid = drugid;
        this.drugname=drugname;
        this.shipquant =shipquant;
        this.shipdate=shipdate;
        this.expdate=expdate;

    }
    public String getDrugID() { return drugid; }
    public String getDrugName() { return drugname; }
    public int getShipQuant() { return shipquant; }
    public String getShipDate(){return shipdate;}
    public String getExpDate(){return expdate;}
    public String getAllData(){
        String all = drugid + " " + drugname + " " + shipquant + " "+ shipdate+ " "+expdate;
        return all;
    }
}
