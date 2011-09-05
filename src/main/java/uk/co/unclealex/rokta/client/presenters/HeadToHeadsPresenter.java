package uk.co.unclealex.rokta.client.presenters;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.model.Row;
import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.presenters.HeadToHeadsPresenter.Display;
import uk.co.unclealex.rokta.client.views.TableDisplay;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.HeadToHeads;
import uk.co.unclealex.rokta.shared.model.WinLoseCounter;

import com.google.common.base.Predicate;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.assistedinject.Assisted;

public class HeadToHeadsPresenter extends InformationActivity<Display, HeadToHeads> {

	public static interface Display extends TableDisplay {
		String HEADER = "header";
	}

	private final Display i_display;
	private final TitleMessages i_titleMessages;
	
	@Inject
	public HeadToHeadsPresenter(
			@Assisted GameFilter gameFilter, InformationService informationService, Display display, TitleMessages titleMessages) {
		super(gameFilter, informationService);
		i_display = display;
		i_titleMessages = titleMessages;
	}

	@Override
	protected HeadToHeads asSpecificInformation(CurrentInformation currentInformation) {
		return currentInformation.getHeadToHeads();
	}

	@Override
	protected void show(GameFilter gameFilter, AcceptsOneWidget panel, final HeadToHeads headToHeads) {
		Display display = getDisplay();
		panel.setWidget(display);
		Table table = new Table();
		Set<String> names = headToHeads.getNames();
		
		int size = names.size();
		Double[][] percentages = new Double[size][size];
		BiMap<String, Integer> indexByName = HashBiMap.create();
		int idx = 0;
		Row header = new Row(Display.HEADER);
		Iterables.addAll(header.getCells(), Iterables.concat(Collections.singleton(null), names));
		table.addRow(header);
		for (String name : names) {
			indexByName.put(name, idx++);
		}
		for (WinLoseCounter winLoseCounter : headToHeads.getWinLoseCounters()) {
			int rowIdx = indexByName.get(winLoseCounter.getWinner());
			int colIdx = indexByName.get(winLoseCounter.getLoser());
			percentages[rowIdx][colIdx] = winLoseCounter.getWinRatio();
		}
		Iterator<String> nameIter = names.iterator();
		for (int rowIdx = 0; rowIdx < percentages.length; rowIdx++) {
			Row row = new Row(null, nameIter.next());
			row.getCells().addAll(Arrays.asList(percentages[rowIdx]));
			table.addRow(row);
		}
		final BiMap<Integer, String> namesByIndex = indexByName.inverse();
		final TitleMessages titleMessages = getTitleMessages();
		TitleFactory titleFactory = new TitleFactory() {
			@Override
			public String createTitle(int row, int column) {
				if (row != 0 && column != 0) {
					final String winner = namesByIndex.get(row - 1);
					final String loser = namesByIndex.get(column - 1);
					Predicate<WinLoseCounter> predicate = new Predicate<WinLoseCounter>() {
						@Override
						public boolean apply(WinLoseCounter winLoseCounter) {
							return winLoseCounter.getWinner().equals(winner) && winLoseCounter.getLoser().equals(loser);
						}
					};
					WinLoseCounter winLoseCounter = Iterables.find(headToHeads.getWinLoseCounters(), predicate, null);
					if (winLoseCounter != null) {
						return
							titleMessages.headToHeadsSummary(
								winLoseCounter.getWinner(), winLoseCounter.getWinCount(), 
								winLoseCounter.getLoser(), winLoseCounter.getLossCount());
					}
				}
				return null;
			}
		};
		display.draw(table, titleFactory, null);
	}
	
	
	public Display getDisplay() {
		return i_display;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}
}
