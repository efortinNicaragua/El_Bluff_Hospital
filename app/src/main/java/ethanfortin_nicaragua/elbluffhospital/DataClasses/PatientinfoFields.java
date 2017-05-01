package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 1/26/2017.
 */

public class PatientinfoFields {

    public String C_patid, C_patname, C_address, C_telephone, C_gender, C_marstat, C_allergies, C_medcond, C_dob;
    public int C_children, C_height, C_weight;
    public PatientinfoFields(){
        super();
    }

    public PatientinfoFields(String r_patid, String r_patname, String r_address, String r_telephone, String r_gender, String r_marstat, int r_height, int r_weight,
                             int r_children, String r_allergies, String r_medcond, String r_dob) {

        super();
        this.C_patid = r_patid;
        this.C_patname = r_patname;
        this.C_address = r_address;
        this.C_telephone = r_telephone;
        this.C_height = r_height;
        this.C_gender = r_gender;
        this.C_marstat = r_marstat;
        this.C_children = r_children;
        this.C_weight = r_weight;
        this.C_allergies = r_allergies;
        this.C_medcond = r_medcond;
        this.C_dob = r_dob;
    }

    public String getPatid() { return C_patid; }
    public String getPatname() { return C_patname;}
    public String getAddress(){return C_address;}
    public String getTelephone(){return C_telephone;}
    public int getHeight(){return C_height;}
    public String getGender(){return C_gender;}
    public String getMarstat(){return  C_marstat;}
    public int getChildren(){return C_children;}
    public int getWeight(){return C_weight;}
    public String getAllergies(){return C_allergies;}
    public String getMedcond(){return C_medcond;}
    public String dob(){return C_dob;}

    public String getAllData(){
        String all = C_patid + " " + C_patname + " " + C_address + " " + C_telephone + " " + C_height + " " + C_gender + " " + C_marstat + " " + C_children + " " + C_weight + " " + C_allergies + " " + C_medcond + " " + C_weight + " ";
        return all;
    }


}
