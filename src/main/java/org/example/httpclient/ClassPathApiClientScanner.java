package org.example.httpclient;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassPathApiClientScanner extends ClassPathBeanDefinitionScanner {
    public ClassPathApiClientScanner(BeanDefinitionRegistry registry) {
        super(registry, false);

        addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().getAnnotationTypes().contains(ApiClient.class.getName());
    }

    public List<GenericBeanDefinition> scanCandidates(String[] basePackages) {
        Set<BeanDefinitionHolder> definitionHolders = super.doScan(basePackages);

        List<GenericBeanDefinition> beanDefinitions = new ArrayList<>();

        for (BeanDefinitionHolder definitionHolder : definitionHolders) {
            beanDefinitions.add((GenericBeanDefinition)definitionHolder.getBeanDefinition());
        }

        return beanDefinitions;
    }
}
