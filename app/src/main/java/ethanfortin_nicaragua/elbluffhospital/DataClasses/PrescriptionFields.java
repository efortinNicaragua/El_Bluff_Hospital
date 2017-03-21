package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 2/13/2017.
 */


/**ML: Initialize class variable names. Classes are used to access all information as a bulk!**/
public class PrescriptionFields {
    public String C_rxid;
    public String C_drugid;
    public String C_transdate;
    public Integer C_quantity;
    public String C_patid;
    public String C_directions;
    public String C_duration;
    public String C_doctor;
    public String C_symptoms;
    public String C_drugname;

    public PrescriptionFields(){
        super();
    }


    /**ML: Pass the class the r_** variable which is the string returned from the rxShow method in Fetch Prescriptions.
     * Use this. to make the class variable equal to the variable returned from the JSON array. **/
    public PrescriptionFields(String r_rxID, String r_drugID, String r_transDate, Integer r_quantity, String r_patID, String r_directions, String r_duration,
                              String r_doctor, String r_symptoms
                              // ,String r_drugName
    ) {

        super();
        this.C_rxid = r_rxID;
        this.C_drugid = r_drugID;
        this.C_transdate = r_transDate;
        this.C_quantity = r_quantity;
        this.C_patid = r_patID;
        this.C_directions = r_directions;
        this.C_duration = r_duration;
        this.C_doctor = r_doctor;
        this.C_symptoms = r_symptoms;
        //this.C_drugname = r_drugName;

    }


    /**ML: These are used for testing purposes. In order to see just the value of C_rxid in the class file, you can just call D_RXId**/
    public String D_RXId() { return C_rxid; }
    public String D_DrugId() { return C_drugid; }
    public String D_TransDate() { return C_transdate; }
    public Integer D_Quantity() { return C_quantity; }
    public String D_PatId() { return C_patid; }
    public String D_Directions() { return C_directions; }
    public String D_Duration() { return C_duration; }
    public String D_Doctor() { return C_doctor; }
    public String D_Symptoms() { return C_symptoms; }
   // public String D_DrugName() { return C_drugname; }



    /**ML: Get all data as one string. **/
    public String D_AllData(){
        String all = C_rxid + " " + C_drugid + " " + C_transdate + " " + C_quantity + " " + C_patid + " " + C_directions + " " + C_duration + " "
                + C_doctor + " " + C_symptoms + " " + C_drugname + " ";
        return all;
    }


}
