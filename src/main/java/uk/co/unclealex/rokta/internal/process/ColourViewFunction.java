package uk.co.unclealex.rokta.internal.process;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.internal.model.Colour;
import uk.co.unclealex.rokta.pub.views.ColourView;

import com.google.common.base.Function;

@Service
@Transactional
public class ColourViewFunction implements Function<Colour, ColourView> {

	@Override
	public ColourView apply(Colour colour) {
		return new ColourView(colour.getName(), colour.getHtmlName(), colour.getRed(), colour.getGreen(), colour.getBlue());
	}

}
