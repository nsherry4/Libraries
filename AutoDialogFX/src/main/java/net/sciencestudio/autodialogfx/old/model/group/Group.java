package net.sciencestudio.autodialogfx.old.model.group;

import java.util.List;

import net.sciencestudio.autodialogfx.old.model.Model;
import net.sciencestudio.autodialogfx.old.view.layouts.Layout;

public interface Group extends Model<List<Model<?>>> {

	Group add(Model<?> model);
	Group addAll(Model<?>... models);
	Group addAll(List<Model<?>> models);
	
	List<Model<?>> getModels();
	
	public static Group wrap(Class<? extends Layout> layout, Model<?> model) {
		return new IGroup(layout, model.getTitle()).add(model);
	}
	
	
}