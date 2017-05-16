package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 1/26/2017
 */

public class PatientinfoFields {

    public String patid, patname, address, telephone, gender, marstat, allergies, medcond, dob;
    public int children, height, weight;
    public PatientinfoFields(){
        super();
    }

    public PatientinfoFields(String patid, String patname, String address, String telephone, String gender, String marstat, int height, int weight,
                             int children, String allergies, String medcond, String dob) {

        super();
        this.patid = patid;
        this.patname = patname;
        this.address = address;
        this.telephone = telephone;
        this.height = height;
        this.gender = gender;
        this.marstat = marstat;
        this.children = children;
        this.weight = weight;
        this.allergies = allergies;
        this.medcond = medcond;
        this.dob = dob;
    }

    public String getPatid() { return patid; }
    public String getPatname() { return patname; }
    public String getAddress() { return address; }
    public String getTelephone() { return telephone; }
    public String getGender() { return gender; }
    public String getMarstat() { return  marstat; }
    public String getAllergies() { return allergies; }
    public String getMedcond() { return medcond; }
    public String getDob() { return dob; }
    public int getHeight() { return height; }
    public int getChildren() { return children; }
    public int getWeight() { return weight; }

    public String getAllData(){
        String all = patid + " "
                + patname + " "
                + address + " "
                + telephone + " "
                + height + " "
                + gender + " "
                + marstat + " "
                + children + " "
                + weight + " "
                + allergies + " "
                + medcond + " "
                + weight;
        return all;
    }
}
