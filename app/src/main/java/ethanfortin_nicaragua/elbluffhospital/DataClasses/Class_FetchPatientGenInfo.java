package ethanfortin_nicaragua.elbluffhospital.DataClasses;

/**
 * Created by wildcat on 1/26/2017.
 */

public class Class_FetchPatientGenInfo {

    public String category;
    public String information;
       public Class_FetchPatientGenInfo(){
        super();
    }

    public Class_FetchPatientGenInfo(String category, String information) {

        super();
        this.category = category;
        this.information=information;

    }
    public String getCategory() { return category; }
    public String getInformation() { return information; }
   public String getAllData(){
        String all = category + " " + information;
        return all;
    }


}
