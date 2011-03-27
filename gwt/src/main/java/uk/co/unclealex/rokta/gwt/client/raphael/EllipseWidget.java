package uk.co.unclealex.rokta.gwt.client.raphael;

public class EllipseWidget extends RaphaelWidget<EllipseObject, EllipseWidget> {

	EllipseWidget(EllipseObject EllipseObject) {
		super(EllipseObject);
	}

	@Override
	public EllipseWidget me() {
		return this;
	}	

	public EllipseObject getEllipseObject() {
		return getRaphaelObject();
	}
}
