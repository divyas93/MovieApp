package in.movieapp.com.movieapp.Network;

import retrofit2.Retrofit;

/**
 * Created by DivyaSethi on 23/07/18.
 */
public class NetworkUtils {

    public RetorfitAPIInterface getRetorfitAPIInterface(String url) {
        Retrofit client = RetrofitClient.getRetrofitAPIClient(url);
        return client.create(RetorfitAPIInterface.class);
    }
}
