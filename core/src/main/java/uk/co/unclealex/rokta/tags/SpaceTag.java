package uk.co.unclealex.rokta.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SpaceTag extends TagSupport {

	private boolean i_nonBreaking;

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(isNonBreaking()?"&#160":" ");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}
	
	public boolean isNonBreaking() {
		return i_nonBreaking;
	}

	public void setNonBreaking(boolean nonBreaking) {
		i_nonBreaking = nonBreaking;
	}
	
}
