package stratos.apps.ecodrive.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import stratos.apps.ecodrive.model.Registration;

public interface ApiInterface {

    @POST("/users")
    Call<Void> registerUser(@Body List<Registration> registration);

    @POST("/driverData")
    Call<Void> sendTripData(@Body List tripData);
}
