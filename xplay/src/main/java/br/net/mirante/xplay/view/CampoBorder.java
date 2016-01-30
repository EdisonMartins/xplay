package br.net.mirante.xplay.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.Model;

public class CampoBorder extends Border {

	private static final long serialVersionUID = 1L;

	public CampoBorder(String id, String label) {
		super(id);
		addToBorder(new Label("label", Model.of(label)));
	}
	
	

}
