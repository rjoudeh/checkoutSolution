package com.checkout.xml;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * 
 * @author Rania
 * 
 */
public class XmlUtil {
	/**
	 * 
	 * @return
	 * @throws JAXBException
	 */
	public static String generateXml(Object bean) throws JAXBException {
		// Create context class
		JAXBContext jaxbContext = JAXBContext.newInstance(bean.getClass());

		// Create the marshaller from the context
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// Marshalling
		StringWriter sw = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(bean, sw);
		System.out.println(sw.toString());
		return sw.toString();
	}
}
