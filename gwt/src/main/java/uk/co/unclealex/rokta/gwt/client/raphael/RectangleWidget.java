package uk.co.unclealex.rokta.gwt.client.raphael;

public class RectangleWidget extends RaphaelWidget<RectangleObject, RectangleWidget> {

	RectangleWidget(RectangleObject RectangleObject) {
		super(RectangleObject);
	}

	@Override
	public RectangleWidget me() {
		return this;
	}
	
	public RectangleObject getRectangleObject() {
		return getRaphaelObject();
	}
}
