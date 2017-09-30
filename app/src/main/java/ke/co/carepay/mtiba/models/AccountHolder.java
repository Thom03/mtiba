package ke.co.carepay.mtiba.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kingkong on 9/30/17.
 */
@Parcel
public class AccountHolder {
    private String c;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long dateOfBirth;
    private String gender;
    private HashMap<String, String> identification = new HashMap<>();
    private int userRef;
    private String phoneNumber;

    public AccountHolder(){}

    public AccountHolder(String firstName, String middleName, String lastName, Long dateOfBirth, String gender, HashMap<String, String> identification, int userRef, String phoneNumber) {
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.gender = gender;
        this.middleName = middleName;
        this.identification = identification;
        this.userRef = userRef;
        this.phoneNumber = phoneNumber;
        this.c = ".AccountHolder";
    }
    public String getFirstName(){
        return firstName;
    }
    public String getMiddleName(){
        return middleName;
    }
    public  String getLastName(){
        return lastName;
    }
    public Long getDateOfBirth(){
        return dateOfBirth;
    }
    public String getGender(){
        return gender;
    }
    public HashMap<String, String> getIdentification(){
        return identification;
    }
    public int getUserRef(){
        return userRef;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }

}
