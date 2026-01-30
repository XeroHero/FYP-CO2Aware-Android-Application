package dev.xerohero.co2aware.network;

import java.util.List;

import dev.xerohero.co2aware.models.Itinerary;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetItineraryService {
    @GET("v1/stations?contract=dublin&apiKey=17566ea530bc36033158dda33346ffbc2313d405")
    Call<List<Itinerary>> getAllBikeStations();
}
