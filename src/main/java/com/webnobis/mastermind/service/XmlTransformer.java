package com.webnobis.mastermind.service;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.webnobis.mastermind.model.ColorType;

/**
 * Transforms model classes from or to XML
 * 
 * @author steffen
 *
 */
public interface XmlTransformer {

// Has also color type to JAXBContext added, to avoid javax.xml.bind.MarshalException:
// (... nor any of its super class is known to this context)

	/**
	 * Transforms XML to model
	 * 
	 * @param <T>       model type
	 * @param xml       XML
	 * @param modelType model type class
	 * @return model
	 * @throws DataBindingException if the the XML transformation fails
	 * @see Unmarshaller#unmarshal(java.io.Reader)
	 */
	@SuppressWarnings("unchecked")
	static <T> T toModel(String xml, Class<T> modelType) {
		try (CharArrayReader in = new CharArrayReader(Objects.requireNonNull(xml, "xml is null").toCharArray())) {
			try {
				Unmarshaller unmarshaller = JAXBContext
						.newInstance(Objects.requireNonNull(modelType, "model type is null"), ColorType.class)
						.createUnmarshaller();
				return (T) unmarshaller.unmarshal(in);
			} catch (JAXBException e) {
				throw new DataBindingException(
						MessageFormat.format("Invalid xml {0} for type {1} found. {2}", xml, modelType, e.getMessage()),
						e);
			}
		}
	}

	/**
	 * Transforms model to XML, encoded as UTF-8
	 * 
	 * @param model model
	 * @return XML
	 * @throws DataBindingException if the the XML transformation fails
	 * @see Marshaller#JAXB_ENCODING
	 * @see Marshaller#marshal(Object, java.io.Writer)
	 */
	static String toXml(Object model) {
		try (CharArrayWriter out = new CharArrayWriter()) {
			Marshaller marshaller = JAXBContext
					.newInstance(Objects.requireNonNull(model, "model is null").getClass(), ColorType.class)
					.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(model, out);
			out.flush();
			return out.toString();
		} catch (JAXBException e) {
			throw new DataBindingException(MessageFormat.format("Invalid model {0} found. {1}", model, e.getMessage()),
					e);
		}
	}

}
