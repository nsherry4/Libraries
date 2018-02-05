package swidget.widgets.tabbedinterface;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.plaf.LayerUI;

public class TabbedInterfacePanel extends JLayeredPane {

	private JPanel modalLayer = new JPanel();
	private JPanel contentLayer = new JPanel();
	boolean modalShown = false;
	
	public TabbedInterfacePanel() {
		setLayout(new TabbedInterfaceLayerLayout());
		
		modalLayer.addMouseListener(new MouseAdapter() {});
		modalLayer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				e.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				e.consume();
			}
		});

		
		final JLayer<JPanel> layer = new JLayer<JPanel>(contentLayer, new BlurLayerUI<JPanel>(this) {
			@Override
			public void eventDispatched(AWTEvent e, JLayer<? extends JPanel> l) {
				((InputEvent) e).consume();
			}
			

			
		});
				
		add(layer, new StackConstraints(JLayeredPane.DEFAULT_LAYER, "content"));
		
		
	}
	
	private static final class StackConstraints {
		public final int layer;
		public final Object layoutConstraints;

		public StackConstraints(int layer, Object layoutConstraints) {
			this.layer = layer;
			this.layoutConstraints = layoutConstraints;
		}
	}

	protected void addImpl(Component comp, Object constraints, int index) {
		int layer = 0;
		int pos = 0;
		Object constr;
		if (constraints instanceof StackConstraints) {
			layer = ((StackConstraints) constraints).layer;
			constr = ((StackConstraints) constraints).layoutConstraints;
		} else {
			layer = getLayer(comp);
			constr = constraints;
		}
		pos = insertIndexForLayer(layer, index);
		super.addImpl(comp, constr, pos);
		setLayer(comp, layer, pos);
		comp.validate();
		comp.repaint();
	}
	
	public void showModal(JPanel panel) {
		
		JPanel wrap = new JPanel(new BorderLayout());
		wrap.add(panel, BorderLayout.CENTER);
		wrap.setBorder(new LineBorder(Color.BLACK, 1));
		
		modalShown = true;
		
		modalLayer.setOpaque(false);
		//modalLayer.setBackground(new Color(0, 0, 0, 0.4f));
		modalLayer.setBackground(new Color(0, 0, 0, 0f));
		modalLayer.removeAll();
		
		modalLayer.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 3;
		c.gridwidth = 3;
		c.weightx = 0f;
		c.weighty = 0f;
		c.gridx = 1;
		c.gridy = 1;
		
		modalLayer.add(wrap, c);
		
		//this.moveToBack(contentLayer);
		
		contentLayer.setEnabled(false);
		//contentLayer.setVisible(false);
		
		
		add(modalLayer, new StackConstraints(JLayeredPane.MODAL_LAYER, "modal"));
		modalLayer.requestFocus();
		//add(modalLayer, BorderLayout.CENTER);

		//modalLayer.setVisible(true);
		
	}
	
	public void clearModal() {
		modalShown = false;
		this.remove(modalLayer);
		//this.remove(contentLayer);
		//add(contentLayer, new StackConstraints(JLayeredPane.DEFAULT_LAYER, BorderLayout.CENTER));
		contentLayer.setEnabled(true);
		contentLayer.requestFocus();
		//contentLayer.setVisible(true);
	}
	
	public JPanel getContentLayer() {
		return contentLayer;
	}
	
	

}

class BlurLayerUI<T extends Component> extends LayerUI<T> {
	private BufferedImage mOffscreenImage;
	private BufferedImageOp mOperation;

	private TabbedInterfacePanel parent;

	public BlurLayerUI(TabbedInterfacePanel parent) {
		this.parent = parent;
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };
		mOperation = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, null);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		if (parent.modalShown) {
			int w = c.getWidth();
			int h = c.getHeight();
	
			if (w == 0 || h == 0) {
				return;
			}
	
			// Only create the offscreen image if the one we have
			// is the wrong size.
			if (mOffscreenImage == null || mOffscreenImage.getWidth() != w || mOffscreenImage.getHeight() != h) {
				mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			}
	
			Graphics2D ig2 = mOffscreenImage.createGraphics();
			ig2.setClip(g.getClip());
			super.paint(ig2, c);
			ig2.dispose();
	
			Graphics2D g2 = (Graphics2D) g.create();
			g2.drawImage(mOffscreenImage, mOperation, 0, 0);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, c.getWidth(), c.getHeight());
			g2.dispose();
			
		} else {
			super.paint(g, c);
		}
	}
}

