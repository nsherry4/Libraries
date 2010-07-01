package scidraw.drawing.backends.graphics2d;

import java.awt.Graphics2D;

import scidraw.drawing.backends.Surface;


class ScreenSurface extends AbstractGraphicsSurface
{

	public ScreenSurface(Graphics2D g)
	{
		super(g);
	}
	
	public Surface getNewContextForSurface()
	{
		return new ScreenSurface((Graphics2D)graphics.create());
	}

	@Override
	public boolean isVectorSurface() {
		return false;
	}
	
}
