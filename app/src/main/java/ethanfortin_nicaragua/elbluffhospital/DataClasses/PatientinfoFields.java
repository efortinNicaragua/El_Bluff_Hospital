package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 1/26/2017
 */

public class PatientinfoFields {

    public String patid, patname, fileid, address, telephone, gender, civil_status;
    public PatientinfoFields(){
        super();
    }

    public PatientinfoFields(String patid, String fileid, String patname, String address, String telephone, String gender, String civil_status) {

        super();
        this.patid = patid;
        this.fileid=fileid;
        this.patname = patname;
        this.address = address;
        this.telephone = telephone;
        this.gender = gender;
        this.civil_status = civil_status;
    }

    public String getPatid() { return patid; }
    public String getPatname() { return patname; }
    public String getAddress() { return address; }
    public String getTelephone() { return telephone; }
    public String getGender() { return gender; }
    public String getCivil_Status() { return  civil_status; }

    public String getAllData(){
        String all = patid + " "
                + fileid + " "
                + patname + " "
                + address + " "
                + telephone + " "
                + gender + " "
                + civil_status + " ";
        return all;
    }
}
