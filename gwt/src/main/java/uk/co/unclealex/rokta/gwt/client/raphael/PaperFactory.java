package uk.co.unclealex.rokta.gwt.client.raphael;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class PaperFactory {

	protected native final PaperObject createPaperObject(int x, int y, int width, int height)
	/*-{ return $wnd.Raphael(x1, y1, width, height); }-*/;

	protected native final PaperObject createPaperObject(String elementId, int width, int height)
	/*-{ return $wnd.Raphael(element, width, height); }-*/;

	protected native final PaperObject createPaperObject(Element element, int width, int height)
	/*-{ return $wnd.Raphael(element, width, height); }-*/;

	public native final Attributes createAttributes()
	/*-{ return new Object(); }-*/;
	
	public native final AnimatableAttributes createAnimatableAttributes()
	/*-{ return new Object(); }-*/;

	public PaperPanel createPaperPanel(int x, int y, int width, int height) {
		return new PaperPanel(createPaperObject(x, y, width, height), null);
	}

	public PaperPanel createPaperPanel(String elementId, int width, int height) {
		return new PaperPanel(createPaperObject(elementId, width, height), Document.get().getElementById(elementId));
	}

	public PaperPanel createPaperPanel(Element element, int width, int height) {
		return new PaperPanel(createPaperObject(element, width, height), element);
	}

	public PaperPanel createPaperPanel(int width, int height) {
		return createPaperPanel(Document.get().createDivElement(), width, height);
	}
	
	protected boolean isSvgSupported() {
		return GWT.isScript();
	}
}
