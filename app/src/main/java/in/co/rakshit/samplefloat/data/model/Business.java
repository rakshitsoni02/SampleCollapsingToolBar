
package in.co.rakshit.samplefloat.data.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@Generated("org.jsonschema2pojo")
public class Business {

    public List<Subcategory> subcategories = new ArrayList<Subcategory>();
    public List<Email> emails = new ArrayList<Email>();
    public Boolean premiumSeller;
    public Nowfloats nowfloats;
    public String profileImage;
    public Address address;
    public Object parentId;
    public Object poc;
    public Category category;
    public Timings timings;
    public Object registeredName;
    public List<BusinessType> businessTypes = new ArrayList<BusinessType>();
    public String website;
    public String location;
    public String id;
    public Object socialNetworks;
    public Boolean onlinePayment;
    public String bookingStatus;
    public String timestamp;
    public String description;
    public List<Object> businessImages = new ArrayList<Object>();
    public String status;
    public ClaimedUser claimedUser;
    public List<Object> paymentMethods = new ArrayList<Object>();
    public Object premiumSellerInfo;
    public Object placeId;
    public List<Feature> features = new ArrayList<Feature>();
    public List<String> knownFor = new ArrayList<String>();
    public String source;
    public String updatedAt;
    public Object coverImage;
    public String name;
    public Object generalInfo;
    public Object employeeCount;
    public Object registrationDetails;
    public List<PhoneNumber_> phoneNumbers = new ArrayList<PhoneNumber_>();

}
