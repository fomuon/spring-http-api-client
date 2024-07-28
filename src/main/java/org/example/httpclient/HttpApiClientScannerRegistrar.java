package org.example.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

public class HttpApiClientScannerRegistrar implements ImportBeanDefinitionRegistrar {
    private final Logger log = LoggerFactory.getLogger(HttpApiClientScannerRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(ApiClient.class.getName()));

//        if (attrs != null) {
            scanAndRegisterHttpApiClientInvoker(attrs, registry);
//        }
    }

    private void scanAndRegisterHttpApiClientInvoker(AnnotationAttributes attrs, BeanDefinitionRegistry registry) {
//        String baseUrl = attrs.getString("baseUrl");
//        String[] basePackages = attrs.getStringArray("basePackages");
//        String httpInvokerRequestExecutorRef = attrs.getString("httpInvokerRequestExecutorRef");

//        Assert.hasText(baseUrl, "baseUrl is required");
//        Assert.notEmpty(basePackages, "basePackages must not be empty");

        ClassPathApiClientScanner scanner = new ClassPathApiClientScanner(registry);
        List<GenericBeanDefinition> beanDefinitions = scanner.scanCandidates(new String[] { "org.example" });

        for (GenericBeanDefinition definition : beanDefinitions) {

            AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) definition).getMetadata();

            if (metadata.getAnnotationTypes().contains(ApiClient.class.getName())) {
                System.out.println(metadata.getClassName());
                String serviceInterface = definition.getBeanClassName();
                definition.getPropertyValues().addPropertyValue("serviceInterface",  serviceInterface);
//                beanDefinitions.add(new HttpAliClientRegistar.HttpApiClientBeanMetadata(beanDefinitionName, (ScannedGenericBeanDefinition)beanDefinition));
            }

//            definition.getPropertyValues().addPropertyValue("baseUrl", baseUrl);


            definition.setBeanClass(HttpApiClientInvokerProxyFactoryBean.class);
        }
    }
}
