package org.example.httpclient;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

public class HttpApiClientInterceptor implements MethodInterceptor, BeanClassLoaderAware, InitializingBean {
    private Class<?> serviceInterface;
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private RestTemplate restTemplate;

    public void setServiceInterface(Class<?> serviceInterface) {
        Assert.notNull(serviceInterface, "'serviceInterface' must not be null");
        Assert.isTrue(serviceInterface.isInterface(), "'serviceInterface' must be an interface");
        this.serviceInterface = serviceInterface;
    }

    /**
     * Return the interface of the service to access.
     */
    public Class<?> getServiceInterface() {
        return this.serviceInterface;
    }

    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {



        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {

    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
