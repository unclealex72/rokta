package uk.co.unclealex.rokta.pub.views;

import java.io.Serializable;

public class ColourView implements Serializable, Comparable<ColourView> {

	private String i_htmlName;
	private String i_name;
	private short i_red;
	private short i_green;
	private short i_blue;
	
	protected ColourView() {
		// Default constructor for serialisation.
	}
	
	public ColourView(String name, String htmlName, short red, short green, short blue) {
		super();
		i_name = name;
		i_htmlName = htmlName;
		i_red = red;
		i_green = green;
		i_blue = blue;
	}

	public int compareTo(ColourView o) {
		return getName().compareTo(o.getName());
	}

	public String toHexString() {
		return "#" + toHexString(getRed()) + toHexString(getGreen()) + toHexString(getBlue());
	}
	
	protected String toHexString(short number) {
		String hexString = Integer.toHexString(number);
		if (number < 16 ) {
			hexString = "0" + hexString;
		}
		return hexString;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public String getHtmlName() {
		return i_htmlName;
	}
	
	public String getName() {
		return i_name;
	}
	
	public short getRed() {
		return i_red;
	}
	
	public short getGreen() {
		return i_green;
	}
	
	public short getBlue() {
		return i_blue;
	}
}
