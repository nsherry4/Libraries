package net.sciencestudio.autodialogfx.view.editors;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import net.sciencestudio.autodialogfx.model.value.Value;

public class CheckboxEditor extends AbstractEditor<Boolean> {

	CheckBox node;
	
	public CheckboxEditor() {}
	
	@Override
	public void init(Value<Boolean> value) {	
		node = new CheckBox("");
		
		node.setSelected(value.getValue());
		
		node.selectedProperty().addListener(change -> {
			getModel().setValue(node.isSelected());
		});
	}
	

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	protected void onValueChange() {
		if (node.isSelected() == getModel().getValue()) return;
		node.setSelected(getModel().getValue());
	}

	@Override
	public Boolean getEditorValue() {
		return node.isSelected();
	}

}
