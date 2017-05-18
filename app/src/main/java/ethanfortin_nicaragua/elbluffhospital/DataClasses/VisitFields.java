package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 4/6/2017.
 */

public class VisitFields {
    public int visitid;
    public String patid;
    public String visitdate;
    public String reason;
    public String doctor;

    public VisitFields() {super();}

    public VisitFields(int visitid, String patid, String visitdate, String reason, String doctor){
        super();
        this.visitid = visitid;
        this.patid = patid;
        this.visitdate = visitdate;
        this.reason = reason;
        this.doctor = doctor;
    }

    public VisitFields(int visitid, String visitdate, String reason) {
        super();

        this.visitid = visitid;
        this.patid = null;
        this.visitdate = visitdate;
        this.reason = reason;
        this.doctor = null;
    }

    public String D_PatId() { return patid; }
    public String D_VisitDate() { return visitdate; }
    public String D_Reason() { return reason; }
    public String D_Doctor() { return doctor; }

    /**ML: Get all data as one string.**/
    public String D_AllData(){
        String all = visitid + " " + patid + " " + visitdate + " " + reason + " " + doctor + " " ;
        return all;
    }

}
