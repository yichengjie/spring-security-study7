package com.yicj.study.jpa.config;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * YamlPropertySourceFactory
 * </p>
 *
 * @author yicj
 * @since 2024/08/20 18:27
 */
public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Resource resourceResource = resource.getResource();
        if(!resourceResource.exists()){
            return new PropertiesPropertySource(null, new Properties());
        } else if(
                resourceResource.getFilename().endsWith(".yml") ||
                        resourceResource.getFilename().endsWith(".yaml")){
            List<PropertySource<?>> sources = new YamlPropertySourceLoader()
                    .load(resourceResource.getFilename(),resourceResource);
            return sources.getFirst();
        }
        return super.createPropertySource(name, resource);
    }
}
