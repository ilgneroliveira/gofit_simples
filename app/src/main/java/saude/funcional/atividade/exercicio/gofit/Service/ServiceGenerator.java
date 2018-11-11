package saude.funcional.atividade.exercicio.gofit.Service;

import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ServiceGenerator
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class ServiceGenerator {
    //URL base do endpoint. Deve sempre terminar com /
    private static final String API_BASE_URL = "http://ec2-35-162-202-113.us-west-2.compute.amazonaws.com/";

//    public static <S> S createService(Class<S> serviceClass) {
//
//        //Instancia do interceptador das requisições
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
//                .readTimeout(15, TimeUnit.SECONDS);
//
//        httpClient.addInterceptor(loggingInterceptor);
//
//
//        //Instância do retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(new Gson()))
//                .client(httpClient.build())
//                .build();
//
//        return retrofit.create(serviceClass);
//    }
//
//    public static final String API_BASE_URL = "http://api.url";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
