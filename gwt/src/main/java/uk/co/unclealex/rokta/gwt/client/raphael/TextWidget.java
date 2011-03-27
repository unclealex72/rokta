package uk.co.unclealex.rokta.gwt.client.raphael;

public class TextWidget extends RaphaelWidget<TextObject, TextWidget> {

	TextWidget(TextObject TextObject) {
		super(TextObject);
	}

	@Override
	public TextWidget me() {
		return this;
	}
	
	public TextObject getTextObject() {
		return getRaphaelObject();
	}
}
