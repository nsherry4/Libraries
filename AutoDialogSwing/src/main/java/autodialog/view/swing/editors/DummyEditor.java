package autodialog.view.swing.editors;

import javax.swing.JComponent;
import javax.swing.JPanel;

import autodialog.model.Parameter;

public class DummyEditor extends AbstractSwingEditor<Object> {

	private JComponent component;
	

	public DummyEditor() {
		this(new JPanel());
	}
	
	public DummyEditor(JComponent component) {
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
	public void setEditorValue(Object value) {}


	@Override
	public Object getEditorValue() {
		return param.getValue();
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
	

}
