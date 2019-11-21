package rhsso.authenticator.models;

import com.google.common.flogger.FluentLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileNumber {

    private String mobileNumber;
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
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

        logger.atInfo().log("Validating mobile number: " + this.mobileNumber);
        String regex = "^((\\d{1,3})[\\s-]?)?(\\d{9})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.mobileNumber.trim());
        boolean isValid = m.find() && m.group().equals(this.mobileNumber.trim());
        logger.atInfo().log("Is Valid mobile: " + isValid);
        return  isValid;


    }
}
