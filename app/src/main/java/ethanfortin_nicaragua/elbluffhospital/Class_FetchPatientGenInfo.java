package ethanfortin_nicaragua.elbluffhospital;

/**
 * Created by wildcat on 1/26/2017.
 */

public class Class_FetchPatientGenInfo {

    public String patid, patname,address, telephone, gender, marstat, allergies, medcond;
    public int children, height, weight;
    public Class_FetchPatientGenInfo(){
        super();
    }

    public Class_FetchPatientGenInfo(String patid, String patname, String address, String telephone, String gender, String marstat, int height, int weight,
                                     int children, String allergies, String medcond) {

        super();
        this.patid = patid;
        this.patname=patname;
        this.address=address;
        this.telephone=telephone;
        this.height=height;
        this.gender=gender;
        this.marstat=marstat;
        this.children=children;
        this.weight=weight;
        this.allergies=allergies;
        this.medcond=medcond;

    }
    public String getPatid() { return patid; }
    public String getPatname() { return patname;}
    public String getAddress(){return address;}
    public String getTelephone(){return telephone;}
    public int getHeight(){return height;}
    public String getGender(){return gender;}
    public String getMarstat(){return  marstat;}
    public int getChildren(){return children;}
    public int getWeight(){return weight;}
    public String getAllergies(){return allergies;}
    public String getMedcond(){return medcond;}

    public String getAllData(){
        String all = patname + " " + patid;
        return all;
    }


}
