package uk.co.unclealex.rokta.gwt.client.raphael;

public class Attributes extends AbstractAttributes<Attributes> {

	protected Attributes() {
		// Required constructor
	}
	
	public final native String getFont() 
	/*-{ return this.font; }-*/;
	
	public final native Attributes setFont(String font) 
	/*-{ this.font = font; return this }-*/;
	
	public final native String getFontFamily() 
	/*-{ return this['font-family']; }-*/;
	
	public final native Attributes setFontFamily(String fontFamily) 
	/*-{ this['font-family'] = fontFamily; return this; }-*/;
	
	public final native String getFontWeight() 
	/*-{ return this['font-weight']; }-*/;
	
	public final native Attributes setFontWeight(String fontWeight) 
	/*-{ this['font-weight'] = fontWeight; return this; }-*/;
	
	public final native String getGradient() 
	/*-{ return this.gradient; }-*/;
	
	public final native Attributes setGradient(String gradient) 
	/*-{ this.gradient = gradient; return this; }-*/;
	
	public final native String getSrc() 
	/*-{ return this.src; }-*/;
	
	public final native Attributes setSrc(String src) 
	/*-{ this.src = src; return this; }-*/;
	
	public final native String getStrokeDasharray() 
	/*-{ return this['stroke-dasharray']; }-*/;
	
	public final native Attributes setStrokeDasharray(String strokeDasharray) 
	/*-{ this['stroke-dasharray'] = strokeDasharray; return this; }-*/;
	
	public final native String getStrokeLinecap() 
	/*-{ return this['stroke-linecap']; }-*/;
	
	public final native Attributes setStrokeLinecap(String strokeLinecap) 
	/*-{ this['stroke-linecap'] = strokeLinecap; return this; }-*/;
	
	public final native String getStrokeLinejoin() 
	/*-{ return this['stroke-linejoin']; }-*/;
	
	public final native Attributes setStrokeLinejoin(String strokeLinejoin) 
	/*-{ this['stroke-linejoin'] = strokeLinejoin; return this; }-*/;
	
	public final native Integer getStrokeMiterlimit() 
	/*-{ return this['stroke-miterlimit']; }-*/;
	
	public final native Attributes setStrokeMiterlimit(Integer strokeMiterlimit) 
	/*-{ this['stroke-miterlimit'] = strokeMiterlimit; return this; }-*/;

	public final native String getText() 
	/*-{ return this.text; }-*/;
	
	public final native Attributes setText(String text)
	/*-{ this.text = text; return this; }-*/;

}

