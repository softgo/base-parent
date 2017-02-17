package com.application.base.core.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * 读取配置文件信息.
 */
public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String keyPath;

	private String[] encryptPropNames;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        if (encryptPropNames != null && encryptPropNames.length > 0 && props.size() > 0) {
            for (String propName : encryptPropNames) {
                String value = props.getProperty(propName);
                System.out.println( DESEncryptUtil.doEncrypt(value, keyPath));
                props.setProperty(propName, DESEncryptUtil.doEncrypt(value, keyPath));
            }
        }
        super.processProperties(beanFactoryToProcess, props);
    }

   
	public void setEncryptPropNames(String[] encryptPropNames) {
        this.encryptPropNames = encryptPropNames;
    }
   
    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }
}
