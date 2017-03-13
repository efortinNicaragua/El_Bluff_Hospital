package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

/**
 * Created by wildcat on 12/7/2016.
 */

public class AddPatientInfoRow {

    public String patname;
    public String patid;
    public String address;
    public String telephone;
    public String dob;
    public String gender;
    public String marstat;
    public int children;
    public int height;
    public int weight;
    public String allergies;
    public String medcond;
    public AddPatientInfoRow() {
        super();
    }

    public AddPatientInfoRow(String patname, String patid, String address, String telephone, String dob, String gender, String marstat, int children, int height, int weight, String allergies, String medcond) {

        super();
        this.patname = patname;
        this.patid = patid;
        this.address = address;
        this.telephone = telephone;
        this.dob = dob;
        this.gender = gender;
        this.marstat = marstat;
        this.children = children;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
        this.medcond = medcond;

    }
}