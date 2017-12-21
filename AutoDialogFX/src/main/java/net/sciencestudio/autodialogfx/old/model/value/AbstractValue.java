package net.sciencestudio.autodialogfx.old.model.value;

import net.sciencestudio.autodialogfx.old.model.AbstractModel;
import net.sciencestudio.autodialogfx.old.view.editors.Editor;

public abstract class AbstractValue<T> extends AbstractModel<T> implements Value<T> {

	protected T value, proposed;
		
	public AbstractValue(String title, T value, Class<? extends Editor<T>> view) {
		super(view, title);
		this.value = value;
		this.proposed = value;
	}
	
	@Override
	public T getValue() {
		return value;
	}

	@Override
	public T getProposedValue() {
		return proposed;
	}
	
	protected void setProposedValue(T proposed) {
		this.proposed = proposed;
	}
	
}