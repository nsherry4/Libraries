package autodialog.view.swing.editors;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import autodialog.model.Parameter;
import eventful.Eventful;

public class SeparatorEditor extends AbstractSwingEditor<Object> {

	private Parameter<Object> param;
	private JComponent component;
	

	public SeparatorEditor() {
		this(new JSeparator());
	}
	
	public SeparatorEditor(JComponent component) {
		this.component = component;
	}
	
	@Override
	public void initialize(Parameter<Object> param)
	{
		this.param = param;
	}
	
	@Override
	public boolean expandVertical() {
		return false;
	}

	@Override
	public boolean expandHorizontal() {
		return false;
	}

	@Override
	public LabelStyle getLabelStyle() {
		return LabelStyle.LABEL_HIDDEN;
	}

	@Override
	public void setFromParameter() {}

	@Override
	public void validateFailed() {}

	@Override
	public Object getEditorValue() {
		return param.getValue();
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
	
	@Override
	public Parameter<Object> getParameter() {
		return param;
	}

}
