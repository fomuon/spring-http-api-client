package org.example.httpclient;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpApiClientRegistar implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        for (HttpApiClientBeanMetadata beanMetadata : getBeanDefinitionWithHttpApiClient(beanFactory)) {

            String beanName = beanMetadata.getBeanName();
            AnnotationMetadata metadata = beanMetadata.getBeanDefinition().getMetadata();

            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ApiClient.class.getName());

            String baseUrl = (String)annotationAttributes.get("baseUrl");

//            if (!Arrays.asList(metadata.getInterfaceNames()).contains(serviceInterface.getName())) {
//                throw new BeanDefinitionValidationException("'" + metadata.getClassName()
//                        + "' did not implement '" + serviceInterface.getName() + "'");
//            }

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HttpApiClientInterceptor.class)
                    .addPropertyReference("baseUrl", baseUrl);

            ((DefaultListableBeanFactory)beanFactory).registerBeanDefinition(beanName, builder.getBeanDefinition());
        }
    }

    private List<HttpApiClientBeanMetadata> getBeanDefinitionWithHttpApiClient(ConfigurableListableBeanFactory beanFactory) {
        List<HttpApiClientBeanMetadata> beanDefinitions = new ArrayList<>();

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);

            if (beanDefinition instanceof ScannedGenericBeanDefinition) {
                AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) beanDefinition).getMetadata();

                if (metadata.getAnnotationTypes().contains(ApiClient.class.getName())) {
                    beanDefinitions.add(new HttpApiClientBeanMetadata(beanDefinitionName, (ScannedGenericBeanDefinition)beanDefinition));
                }
            }
        }

        return beanDefinitions;
    }

    private static class HttpApiClientBeanMetadata {
        private String beanName;
        private ScannedGenericBeanDefinition beanDefinition;

        HttpApiClientBeanMetadata(String beanName, ScannedGenericBeanDefinition beanDefinition) {
            this.beanName = beanName;
            this.beanDefinition = beanDefinition;
        }

        String getBeanName() {
            return beanName;
        }

        ScannedGenericBeanDefinition getBeanDefinition() {
            return beanDefinition;
        }
    }
}
