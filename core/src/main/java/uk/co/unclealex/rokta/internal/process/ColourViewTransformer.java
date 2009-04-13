package uk.co.unclealex.rokta.internal.process;

import org.apache.commons.collections15.Transformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.internal.model.Colour;
import uk.co.unclealex.rokta.pub.views.ColourView;

@Service
@Transactional
public class ColourViewTransformer implements Transformer<Colour, ColourView> {

	@Override
	public ColourView transform(Colour colour) {
		return new ColourView(colour.getName(), colour.getHtmlName(), colour.getRed(), colour.getGreen(), colour.getBlue());
	}

}
