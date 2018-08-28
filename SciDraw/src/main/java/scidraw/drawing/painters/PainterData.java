package scidraw.drawing.painters;


import scidraw.drawing.Drawing;
import scidraw.drawing.DrawingRequest;
import scidraw.drawing.backends.Surface;
import scitypes.Coord;
import scitypes.ISpectrum;
import scitypes.ReadOnlySpectrum;
import scitypes.Spectrum;

/**
 * 
 * This class is a structure for holding data needed by a {@link Painter} when drawing to a {@link Drawing}
 * 
 * @author Nathaniel Sherry, 2009
 * @see Painter
 * @see Drawing
 *
 */

public class PainterData
{
	public Surface context;
	public DrawingRequest dr;
	public Coord<Float> plotSize;
	public Spectrum dataHeights;
	public ReadOnlySpectrum originalHeights; //TODO: maybe dataHeights, decorationHeights?
	
	
	public PainterData(Surface context, DrawingRequest dr, Coord<Float> plotSize, Spectrum dataHeights)
	{
		this.context = context;
		this.dr = dr;
		this.plotSize = plotSize;
		this.dataHeights = dataHeights;
		this.originalHeights = new ISpectrum(dataHeights);
	}
	
	public double getChannelXValue(double channel)
	{
		double channelSize = plotSize.x / dr.dataWidth;
		return channel * channelSize;
	}
	
}
