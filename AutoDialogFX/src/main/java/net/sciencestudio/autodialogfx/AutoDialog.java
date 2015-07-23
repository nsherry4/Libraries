package net.sciencestudio.autodialogfx;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.model.group.IGroup;
import net.sciencestudio.autodialogfx.model.value.IValue;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.view.AbstractView;
import net.sciencestudio.autodialogfx.view.View;
import net.sciencestudio.autodialogfx.view.editors.SpinnerIntegerEditor;
import net.sciencestudio.autodialogfx.view.layouts.TitledLayout;
import net.sciencestudio.autodialogfx.view.layouts.VerticalLayout;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AutoDialog extends AbstractView implements View {

	private BorderPane node;
	private Group group;
	
	

	public AutoDialog(Value<?> value) {
		this(new IGroup(VerticalLayout.class, value.getTitle()).add(value), new Insets(6));
	}
	
	public AutoDialog(Group group) {
		this(group, new Insets(6));
	}
		
	public AutoDialog(Group group, Insets insets) {
		this.group = group;
		node = new BorderPane();
		node.setPadding(insets);
		node.setCenter(View.forModel(group).getNode());
		
	}
	
	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public String getTitle() {
		return group.getTitle();
	}


	@Override
	public Model<?> getModel() {
		return group;
	}

	
	public Stage present(Stage stage) {
		
		Scene scene = new Scene(node);
		stage.setScene(scene);
		stage.setTitle(getTitle());
		stage.sizeToScene();

		return stage;
	}
	

	
}

