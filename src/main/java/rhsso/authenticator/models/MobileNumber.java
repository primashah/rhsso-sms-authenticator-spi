package rhsso.authenticator.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileNumber {

    private String mobileNumber;
    public String get(){
        return this.mobileNumber;

    }

    public MobileNumber(String value){
        this.mobileNumber = value;
    }

    public boolean isValid(){
        if(this.mobileNumber == null || this.mobileNumber.isEmpty()) {
            return false;
        }


        Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
        Matcher m = p.matcher(this.mobileNumber);
        return (m.find() && m.group().equals(this.mobileNumber));


    }
}
