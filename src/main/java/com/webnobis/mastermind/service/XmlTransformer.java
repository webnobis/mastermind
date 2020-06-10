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

import com.webnobis.mastermind.model.Source;

public interface XmlTransformer {

	@SuppressWarnings("unchecked")
	static <T> Source<T> toSource(String xml) {
		try (CharArrayReader in = new CharArrayReader(Objects.requireNonNull(xml, "xml is null").toCharArray())) {
			return JAXB.unmarshal(in, Source.class);
		}
	}
	
	static String toXml(Object model) {
		try (CharArrayWriter out = new CharArrayWriter()) {
			try {
				Marshaller marshaller = JAXBContext.newInstance(model.getClass()).createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
				marshaller.marshal(model, out);
			} catch (JAXBException e) {
				throw new UncheckedIOException(new IOException(e));
			}
			out.flush();
			return out.toString();
		}
	}

}
