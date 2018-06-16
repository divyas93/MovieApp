package in.movieapp.com.movieapp.Network;

import in.movieapp.com.movieapp.AppConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DivyaSethi.
 */

public class RetrofitClient {

    private static Retrofit retrofitClient = null;

    public static Retrofit getRetrofitAPIClient() {
        retrofitClient = new Retrofit.Builder()
                .baseUrl(AppConstants.movieBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitClient;
    }

}