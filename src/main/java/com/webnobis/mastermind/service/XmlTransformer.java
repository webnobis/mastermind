package com.webnobis.mastermind.service;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Transforms model classes from or to XML
 * 
 * @author steffen
 *
 */
public interface XmlTransformer {

	/**
	 * Transforms XML to model
	 * 
	 * @param <T>       model type
	 * @param xml       XML
	 * @param modelType model type class
	 * @return model
	 * @throws UncheckedIOException if the the XML
	 *                              transformation fails
	 * @see JAXB#unmarshal(java.io.Reader, Class)
	 */
	static <T> T toModel(String xml, Class<T> modelType) {
		try (CharArrayReader in = new CharArrayReader(Objects.requireNonNull(xml, "xml is null").toCharArray())) {
			return JAXB.unmarshal(in, Objects.requireNonNull(modelType));
		}
	}

	/**
	 * Transforms model to XML, encoded as UTF-8
	 * 
	 * @param model model
	 * @return XML
	 * @throws UncheckedIOException if the the XML
	 *                              transformation fails
	 * @see Marshaller#JAXB_ENCODING
	 * @see Marshaller#marshal(Object, java.io.Writer)
	 */
	static String toXml(Object model) {
		try (CharArrayWriter out = new CharArrayWriter()) {
			try {
				Marshaller marshaller = JAXBContext.newInstance(model.getClass()).createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.marshal(model, out);
			} catch (JAXBException e) {
				throw new UncheckedIOException(new IOException(e));
			}
			out.flush();
			return out.toString();
		}
	}

}
