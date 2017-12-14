package autodialog.view.swing;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import autodialog.controller.IADController;
import autodialog.controller.SimpleADController;
import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;
import autodialog.view.swing.editors.SwingEditorFactory;
import autodialog.view.swing.layouts.IADLayout;
import autodialog.view.swing.layouts.SimpleADLayout;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ImageButton;
import swidget.widgets.ImageButton.Layout;
import swidget.widgets.Spacing;
import swidget.widgets.TextWrapping;


public class AutoDialog extends JDialog
{

	public enum AutoDialogButtons {
		CLOSE, OK_CANCEL
	}
	
	private IADController	controller;
	private Container owner;
	
	private String helpTitle;
	private String helpMessage;
	
	private AutoDialogButtons buttons;

	private boolean selected_ok = false;
	
	
	private ImageButton info;
	

	public AutoDialog(List<IEditor<?>> editors) {
		this(new SimpleADController(editors), AutoDialogButtons.OK_CANCEL);
	}

	public AutoDialog(List<IEditor<?>> editors, AutoDialogButtons buttons) {
		this(new SimpleADController(editors), buttons);
	}
	
	public static AutoDialog fromParameters(List<Parameter<?>> params) {
		return new AutoDialog(SwingEditorFactory.forParameters(params));
	}
	
	public static AutoDialog fromParameters(List<Parameter<?>> params, AutoDialogButtons buttons) {
		return new AutoDialog(SwingEditorFactory.forParameters(params), buttons);
	}
	
	
	public AutoDialog(IADController _controller, AutoDialogButtons buttons, Window owner)
	{
		super(owner);
		this.controller = _controller;
		this.owner = owner;
		this.buttons = buttons;
	}

	public AutoDialog(IADController _controller, AutoDialogButtons buttons)
	{
		super();
		this.controller = _controller;
		this.buttons = buttons;
	}
	
	
	public void initialize(){
		initialize(new SimpleADLayout());
	}

	public void initialize(IADLayout layout){
		
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		AutoPanel view = new AutoPanel(controller.getEditors(), layout, 0);
		
		JScrollPane scroller = new JScrollPane(view);
		scroller.setBorder(Spacing.bNone());
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		c.add(scroller, BorderLayout.CENTER);


		c.add(createButtonBox(), BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(owner);
		setVisible(true);
	}
	
	
	
	
	private JPanel createButtonBox()
	{
		
		ButtonBox bbox = new ButtonBox();
		
		if (buttons == AutoDialogButtons.OK_CANCEL) {
			
			ImageButton ok = new ImageButton(StockIcon.CHOOSE_OK, "OK", true);
			ok.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					AutoDialog.this.setVisible(false);
					selected_ok = true;
				}
			});
			
			ImageButton cancel = new ImageButton(StockIcon.CHOOSE_CANCEL, "Cancel", true);
			cancel.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					AutoDialog.this.setVisible(false);
				}
			});
			
			bbox.addRight(0, cancel);
			bbox.addRight(0, ok);
			
		} else if (buttons == AutoDialogButtons.CLOSE) {
			
			ImageButton close = new ImageButton(StockIcon.WINDOW_CLOSE, "Close", true);
			close.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					AutoDialog.this.setVisible(false);
				}
			});
			
			bbox.addRight(0, close);
			
		}
		
		
		info = new ImageButton(StockIcon.BADGE_HELP, "Filter Information", Layout.IMAGE, true);
		info.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{	
				JOptionPane.showMessageDialog(
						AutoDialog.this, 
						TextWrapping.wrapTextForMultiline(helpMessage),
						helpTitle, 
						JOptionPane.INFORMATION_MESSAGE, 
						StockIcon.BADGE_HELP.toImageIcon(IconSize.ICON)
					);

			}
		});
		info.setFocusable(false);
		if (helpMessage == null) info.setVisible(false);
		
		
		bbox.addLeft(0, info);

		
				
		return bbox;
		
	}

	
	
	
	
	public String getHelpTitle() {
		return helpTitle;
	}

	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
		
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
		if (info != null) info.setVisible((helpMessage != null && helpMessage.length() > 0));
	}
	
	
	public boolean okSelected() {
		return selected_ok;
	}
	

}