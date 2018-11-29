package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 4/6/2017.
 */

public class VisitFields {
    public int visitid;
    public String patid;
    public String visitdate;
    public String doctor;
    public String height;
    public String weight;
    public String allergies;
    public String reason;
    public String meds;

    public VisitFields() {super();}

    public VisitFields(int visitid, String patid, String visitdate, String doctor, String height, String weight, String allergies, String reason, String meds){
        super();
        this.visitid = visitid;
        this.patid = patid;
        this.visitdate = visitdate;
        this.doctor = doctor;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
        this.reason = reason;
        this.meds = meds;
    }

        public VisitFields(int visitid, String visitdate, String reason) {
            super();

            this.visitid = visitid;
            this.patid = null;
            this.visitdate = visitdate;
            this.doctor = null;
            this.height = null;
            this.weight = null;
            this.allergies = null;
            this.reason = reason;
            this.meds = null;
        }

//    public int D_VisitId() { return visitid; }
    public String D_PatId() { return patid; }
    public String D_VisitDate() { return visitdate; }
    public String D_Doctor() { return doctor; }
    public String D_Height() { return height; }
    public String D_Weight() { return weight; }
    public String D_Allergies() { return allergies; }
    public String D_reason() { return reason; }
    public String D_Meds() { return meds; }

    /**ML: Get all data as one string.**/
    public String D_AllData(){
        String all = visitid + " "
                + patid + " "
                + visitdate + " "
                + doctor + " "
                + height + " "
                + weight + " "
                + allergies + " "
                + reason + " "
                + meds + " ";
        return all;
    }

}
