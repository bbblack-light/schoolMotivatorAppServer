package com.elena.schoolMotivatorAppServer.mosRuIntegration;

import com.elena.schoolMotivatorAppServer.mosRuIntegration.IDnevnikApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
@Configuration
public class RetrofitConfiguration {
    @Value("${dnevnik.mos.ru}")
    private String dnevnikUrl;

    @Bean
    public Retrofit retrofitInit() {
        return new Retrofit.Builder()
                .baseUrl(dnevnikUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public IDnevnikApi dnevnikApiRegister(Retrofit retrofit) {
        return retrofit.create(IDnevnikApi.class);
    }
}
