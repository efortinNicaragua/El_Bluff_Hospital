package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 4/6/2017.
 */

/**ML: Initialize class variable names. Classes are used to access all information as a bulk**/
public class VisitFields {
    public String C_patid;
    public String C_visitdate;
    public String C_reason;
    public String C_doctor;
   // public String C_rxid;

    public VisitFields() {super();}


    /**ML: Pass the class the r_** variable which is the string returned from the visitShow method in Fetch Visits.
     * Use this to make the class variable equal to the variable returned from the JSON array.
     */

    public VisitFields(String r_patid, String r_visitdate, String r_reason, String r_doctor){
        super();
        this.C_patid = r_patid;
        this.C_visitdate = r_visitdate;
        this.C_reason = r_reason;
        this.C_doctor = r_doctor;
        //this.C_rxid = r_rxid;
    }


    /**ML: These are used for testing purposes. In order to see just the value of C_rxid in the class file, you can just call D_RXId**/
    public String D_PatId() { return C_patid; }
    public String D_VisitDate() { return C_visitdate; }
    public String D_Reason() { return C_reason; }
    public String D_Doctor() { return C_doctor; }
    //public String D_Rxid() { return C_rxid; }

    /**ML: Get all data as one string.**/
    public String D_AllData(){
        String all = C_patid + " " + C_visitdate + " " + C_reason + " " + C_doctor + " " ;
        return all;
    }

}
