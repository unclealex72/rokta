package uk.co.unclealex.rokta.gwt.client.raphael;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class AbstractAttributes<A extends AbstractAttributes<A>> extends JavaScriptObject {

	protected AbstractAttributes() {
		// Required constructor
	}

	public final native Integer getCx() 
	/*-{ return this.cx; }-*/;
	
	public final native A setCx(Integer cx) 
	/*-{ this.cx = cx; return this; }-*/;
	
	public final native Integer getCy() 
	/*-{ return this.cy; }-*/;
	
	public final native A setCy(Integer cy) 
	/*-{ this.cy = cy; return this; }-*/;
	
	public final native String getFill() 
	/*-{ return this.fill; }-*/;
	
	public final native A setFill(String fill) 
	/*-{ this.fill = fill; return this; }-*/;
	
	public final native String getFillOpacity() 
	/*-{ return this['fill-opacity']; }-*/;
	
	public final native A setFillOpacity(double fillOpacity) 
	/*-{ this['fill-opacity'] = fillOpacity; return this; }-*/;
	
	public final native Integer getFontSize() 
	/*-{ return this['font-size']; }-*/;
	
	public final native A setFontSize(Integer fontSize) 
	/*-{ this['font-size'] = fontSize; return this; }-*/;
	
	public final native Integer getHeight() 
	/*-{ return this.height; }-*/;
	
	public final native A setHeight(Integer height) 
	/*-{ this.height = height; return this; }-*/;
	
	public final native Double getOpacity() 
	/*-{ return this.opacity; }-*/;
	
	public final native A setOpacity(Double opacity) 
	/*-{ this.opacity = opacity; return this; }-*/;
	
	public final native String getPath() 
	/*-{ return this.path; }-*/;
	
	public final native A setPath(String path) 
	/*-{ this.path = path; return this; }-*/;
	
	public final native Integer getR() 
	/*-{ return this.r; }-*/;
	
	public final native A setR(Integer r) 
	/*-{ this.r = r; return this; }-*/;
	
	public final native Double getRotation() 
	/*-{ return this.rotation; }-*/;
	
	public final native A setRotation(Double rotation) 
	/*-{ this.rotation = rotation; return this; }-*/;
	
	public final native Integer getRx() 
	/*-{ return this.rx; }-*/;
	
	public final native A setRx(Integer rx) 
	/*-{ this.rx = rx; return this; }-*/;
	
	public final native Integer getRy() 
	/*-{ return this.ry; }-*/;
	
	public final native A setRy(Integer ry) 
	/*-{ this.ry = ry; return this; }-*/;
	
	public final native String getScale() 
	/*-{ return this.scale; }-*/;
	
	public final native A setScale(String scale) 
	/*-{ this.scale = scale; return this; }-*/;
	
	public final native String getStroke() 
	/*-{ return this.stroke; }-*/;
	
	public final native A setStroke(String stroke) 
	/*-{ this.stroke = stroke; return this; }-*/;
	
	public final native Double getStrokeOpacity() 
	/*-{ return this['stroke-opacity']; }-*/;
	
	public final native A setStrokeOpacity(Double strokeOpacity) 
	/*-{ this['stroke-opacity'] = strokeOpacity; return this; }-*/;
	
	public final native int getStrokeWidth() 
	/*-{ return this['stroke-width']; }-*/;
	
	public final native A setStrokeWidth(int strokeWidth) 
	/*-{ this['stroke-width'] = strokeWidth; return this; }-*/;
	
	public final native String getTranslation() 
	/*-{ return this.translation; }-*/;
	
	public final native A setTranslation(String translation) 
	/*-{ this.translation = translation; return this; }-*/;
	
	public final native Integer getWidth() 
	/*-{ return this.width; }-*/;
	
	public final native A setWidth(Integer width) 
	/*-{ this.width = width; return this; }-*/;
	
	public final native Integer getX() 
	/*-{ return this.x; }-*/;
	
	public final native A setX(Integer x) 
	/*-{ this.x = x; return this; }-*/;
	
	public final native Integer getY() 
	/*-{ return this.y; }-*/;
	
	public final native A setY(Integer y) 
	/*-{ this.y = y; return this; }-*/;
}