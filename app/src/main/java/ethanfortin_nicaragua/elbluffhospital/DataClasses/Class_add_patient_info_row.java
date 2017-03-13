package ethanfortin_nicaragua.elbluffhospital.DataClasses;

import java.util.Date;

/**
 * Created by Wildcat on 12/8/2016.
 */

public class Class_add_patient_info_row {
    public String patname;
    public String patid;
    public String adress;
    public String tel;
    public String gender;
    public String marstat;
    public Date dob;
    public int children;
    public int height;
    public int weight;
    public String allergies;
    public String medcond;

    public Class_add_patient_info_row(){super();}

    public Class_add_patient_info_row(String patname,String patid,String tel, String adress,String gender, String marstat, Date dob,int children, int height, int weight,String allergies,String medcond)
    {
    super();
    this.patname= patname;
    this.patid=patid;
    this.tel=tel;
    this.adress=adress;
    this.gender=gender;
    this.marstat=marstat;
    this.dob=dob;
    this.children=children;
    this.height= height;
    this.weight=weight;
    this.allergies=allergies;
    this.medcond=medcond;
}

    public String patname(){return patname;}
    public String patid(){return patid;}
    public String tel(){return tel;}
    public String adress(){return adress;}
    public String gender(){return gender;}
    public String marstat(){return marstat;}
    public Date dob(){return dob;}
    public int children(){return children;}
    public int height(){return height;}
    public int weight(){return weight;}
    public String allergies(){return allergies;}
    public String medcond(){return medcond;}
}