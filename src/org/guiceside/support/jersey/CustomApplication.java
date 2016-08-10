package org.guiceside.support.jersey;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by zhenjiaWang on 16/8/8.
 */
public class CustomApplication extends ResourceConfig {
    public CustomApplication()
    {
        register(CORSFilter.class);
        register(JsonProcessingFeature.class);
//        packages("com.howtodoinjava.jersey");
        packages("com.mingdao.oaapi");
        register(LoggingFilter.class);
//        property(JsonGenerator.PRETTY_PRINTING, true);

    }


}
