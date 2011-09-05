package uk.co.unclealex.rokta.client.ui;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class HidingDockLayoutPanel extends DockLayoutPanel {

	private final Map<Direction, Boolean> i_isHiddenByDirection = Maps.newHashMap();
	private final Map<Direction, Double> i_sizeByDirection = Maps.newHashMap();
	private final int i_hidingDuration;
	
	public HidingDockLayoutPanel(Unit unit, int hidingDuration) {
		super(unit);
		i_hidingDuration = hidingDuration;
	}

	protected class AnimationCallbackAdaptor implements AnimationCallback {
		private final Runnable i_onAnimationFinished;

		public AnimationCallbackAdaptor(Runnable onAnimationFinished) {
			super();
			i_onAnimationFinished = onAnimationFinished;
		}
		
		@Override
		public void onLayout(Layer layer, double progress) {
			// Do nothing.
		}
		
		@Override
		public void onAnimationComplete() {
			getOnAnimationFinished().run();
		}
		
		public Runnable getOnAnimationFinished() {
			return i_onAnimationFinished;
		}
	}
	
	public void show(Direction direction, Runnable onAnimationFinished) {
		show(direction, new AnimationCallbackAdaptor(onAnimationFinished));
	}

	public void show(Direction direction, AnimationCallback callback) {
		hideOrShow(true, direction, callback);
	}
	
	public void hide(Direction direction, Runnable onAnimationFinished) {
		hide(direction, new AnimationCallbackAdaptor(onAnimationFinished));
	}

	public void hide(Direction direction, AnimationCallback callback) {
		hideOrShow(false, direction, callback);
	}

	public void hideOrShow(boolean show, Direction direction, AnimationCallback callback) {
		Widget directionWidget = null;
		int cnt = getWidgetCount();
		for (int idx = 0; idx < cnt && directionWidget == null; idx++) {
			Widget w = getWidget(idx);
			if (getWidgetDirection(w) == direction) {
				directionWidget = w;
			}
		}
		if (directionWidget == null) {
			callback.onAnimationComplete();
		}
		else {
			Map<Direction, Boolean> isHiddenByDirection = getIsHiddenByDirection();
			Boolean isHidden = isHiddenByDirection.get(direction);
			if (isHidden == null) {
				isHidden = false;
				isHiddenByDirection.put(direction, isHidden);
			}
			if (isHidden.booleanValue() != show) {
				callback.onAnimationComplete();
			}
			else {
				Map<Direction, Double> sizeByDirection = getSizeByDirection();
				Double size = sizeByDirection.get(direction);
				if (size == null) {
					size = ((LayoutData) directionWidget.getLayoutData()).size;
					sizeByDirection.put(direction, size);
				}
				double requiredSize = show?size:0;
				isHiddenByDirection.put(direction, !show);
				forceLayout();
				((LayoutData) directionWidget.getLayoutData()).size = requiredSize;
				animate(getHidingDuration(), callback);
			}
		}
	}

	public Map<Direction, Boolean> getIsHiddenByDirection() {
		return i_isHiddenByDirection;
	}

	public Map<Direction, Double> getSizeByDirection() {
		return i_sizeByDirection;
	}

	public int getHidingDuration() {
		return i_hidingDuration;
	}
}
