package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.common.base.Function;

public class FunctionBasedRenderer<S, T> extends OnlyForClassColumnRenderer<S> {

	public static <S, T> CellRenderer create(Class<S> clazz, Function<S, T> function) {
		return new FunctionBasedRenderer<S, T>(clazz, function);
	}
		private final Function<S, T> i_function;

	public FunctionBasedRenderer(Class<S> clazz, Function<S, T> function) {
		super(clazz);
		i_function = function;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object doRender(Object cell, Table table, int row, int column) {
		return getFunction().apply((S) cell);
	}
	
	public Function<S, T> getFunction() {
		return i_function;
	}
}
