package uk.co.unclealex.rokta.server.process;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.client.views.ColourView;
import uk.co.unclealex.rokta.server.model.Colour;

import com.google.common.base.Function;

@Service
@Transactional
public class ColourViewFunction implements Function<Colour, ColourView> {

	@Override
	public ColourView apply(Colour colour) {
		return new ColourView(colour.getName(), colour.getHtmlName(), colour.getRed(), colour.getGreen(), colour.getBlue());
	}

}
