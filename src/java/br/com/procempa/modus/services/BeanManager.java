package br.com.procempa.modus.services;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

public class BeanManager {

	public static String parseBeanToXML(Object bean) throws IOException, SAXException, IntrospectionException {
        StringWriter outputWriter = new StringWriter(); 
        BeanWriter beanWriter = new BeanWriter(outputWriter);
        
        beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(true);
        beanWriter.getBindingConfiguration().setObjectStringConverter(new CustomConvertor());
        beanWriter.getBindingConfiguration().setMapIDs(false);
        beanWriter.enablePrettyPrint();
        
        beanWriter.write(bean.getClass().getSimpleName(), bean);
		return outputWriter.toString();
	}

	public static Object parseXMLToBean(String xml, Class classe) throws IntrospectionException, IOException, SAXException {
        StringReader xmlReader = new StringReader(xml);
        BeanReader beanReader  = new BeanReader();
        beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(true);
        beanReader.getBindingConfiguration().setObjectStringConverter(new CustomConvertor());
        beanReader.getBindingConfiguration().setMapIDs(false);
        beanReader.registerBeanClass(classe.getSimpleName(), classe);
        
        Object bean = (Object) beanReader.parse(xmlReader);
        return bean;
	}
}