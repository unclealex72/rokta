package uk.co.unclealex.rokta.gwt.client.raphael;

public class ImageWidget extends RaphaelWidget<ImageObject, ImageWidget> {

	ImageWidget(ImageObject ImageObject) {
		super(ImageObject);
	}

	@Override
	public ImageWidget me() {
		return this;
	}
		
	public ImageObject getImageObject() {
		return getRaphaelObject();
	}
}
