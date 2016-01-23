package br.net.mirante.xplay.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class SimNaoConverter implements IConverter<Boolean> {

	private static final long serialVersionUID = 1L;

	private static final String NAO = "Não";
	private static final String SIM = "Sim";

	@Override
	public String convertToString(Boolean value, Locale locale) {
		if (Boolean.TRUE.equals(value)) {
			return SIM;
		} else {
			return NAO;
		}
	}

	@Override
	public Boolean convertToObject(String value, Locale locale) throws ConversionException {
		if (SIM.equalsIgnoreCase(value)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

}
