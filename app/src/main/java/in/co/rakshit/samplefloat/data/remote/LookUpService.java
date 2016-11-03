package in.co.rakshit.samplefloat.data.remote;

import com.google.gson.Gson;

import in.co.rakshit.samplefloat.data.model.Base;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface LookUpService {
    String ENDPOINT = "http://ec2-54-169-238-70.ap-southeast-1.compute.amazonaws.com:5000/";

    @GET("businesses")
    Observable<Base> getData();

    /********
     * Helper class that sets up a new services
     *******/
    class Creator {
        public static LookUpService newLookUpService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LookUpService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(LookUpService.class);
        }
    }
}
