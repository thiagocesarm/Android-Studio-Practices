package services;

import android.content.Context;

import com.example.thiago.myfirstapp.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinfo on 16/08/2017.
 */

public class Service {

    protected RestTemplate restTemplate;
    protected ObjectMapper objectMapper = null;

    public Service() {
        restTemplate = null;
    }

    protected RestTemplate getRestTemplateInstance() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        }
        return restTemplate;
    }

    protected ObjectMapper getObjectMapperInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    List getLocais(Context context, String queries) throws Exception {
        List list = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity responseEntity = getRestTemplateInstance().exchange(context.getString(R.string.circular_url_base) + "/locais" + queries, HttpMethod.GET, requestEntity, String.class);
        if (responseEntity.getBody() != null) {
            String result = responseEntity.getBody().toString();
            list = new ArrayList<>();
            if (result != null && !result.isEmpty()) {
                if (result.charAt(0) == '[') {
                    JavaType type = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Local.class);
                    list = getObjectMapperInstance().readValue(result, type);
                } else {
                    list.add((Local) getObjectMapperInstance().readValue(result, Local.class));
                }
            }
        }
        if (list != null && !list.isEmpty()) {
            return list;
        } else {
            return null;
        }
    }
}
