package org.example.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;

public class RestTemplateApiClientsRegistrar implements ImportBeanDefinitionRegistrar {
    private Logger log = LoggerFactory.getLogger(RestTemplateApiClientsRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableRestTemplateApiClients.class.getName()));

//        if (attrs != null) {
            scanAndRegisterHttpInvoker(attrs, registry);
//        }
    }

    private void scanAndRegisterHttpInvoker(AnnotationAttributes attrs, BeanDefinitionRegistry registry) {
        String[] basePackages = attrs.getStringArray("basePackages");
        String restTemplateRef = attrs.getString("restTemplateRef");

//        Assert.notEmpty(basePackages, "basePackages must not be empty");

        ClassPathApiClientScanner scanner = new ClassPathApiClientScanner(registry);
        List<GenericBeanDefinition> beanDefinitions = scanner.scanCandidates(basePackages);

        for (GenericBeanDefinition definition : beanDefinitions) {
            AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) definition).getMetadata();

            if (metadata.getAnnotationTypes().contains(ApiClient.class.getName())) {
                String serviceInterface = definition.getBeanClassName();

                definition.getPropertyValues().addPropertyValue("serviceInterface", serviceInterface);

                if (!StringUtils.isEmpty(restTemplateRef)) {
                    definition.getPropertyValues().add("restTemplate",
                            new RuntimeBeanReference(restTemplateRef));
                }
            }

            definition.setBeanClass(HttpApiClientInvokerProxyFactoryBean.class);
        }
    }
}
