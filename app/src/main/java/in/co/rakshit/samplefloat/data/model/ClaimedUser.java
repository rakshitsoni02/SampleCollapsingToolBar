
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
public class ClaimedUser {

    public String name;
    public String id;
    public Object email;
    public Role role;
    public List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();

}
