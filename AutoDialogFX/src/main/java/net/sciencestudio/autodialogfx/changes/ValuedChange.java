package net.sciencestudio.autodialogfx.changes;

import net.sciencestudio.autodialogfx.model.Valued;

public class ValuedChange<T> extends SourcedChange<Valued<T>> {

	public ValuedChange(Valued<T> source) {
		super(source);
	}

}