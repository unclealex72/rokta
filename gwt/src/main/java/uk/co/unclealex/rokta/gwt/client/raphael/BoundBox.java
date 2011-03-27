package uk.co.unclealex.rokta.gwt.client.raphael;

import com.google.gwt.core.client.JavaScriptObject;

public class BoundBox extends JavaScriptObject {

	protected BoundBox() {
		// Required constructor
	}
	
	public final native int getX()
	/*-{ return this.x; }-*/;

	public final native int getY()
	/*-{ return this.y; }-*/;

	public final native int getWidth()
	/*-{ return this.width; }-*/;

	public final native int getHeight()
	/*-{ return this.height; }-*/;
}
