package fava.functionable;

import java.util.Iterator;



public class RangeSet extends Functionable<Integer>
{

	private FList<Range> ranges;
	
	public RangeSet()
	{
		ranges = new FList<Range>();
	}
	
	public void addRange(Range range)
	{
		Iterator<Range> i;
		Range r;
		
		if (range == null) return;
		
		
		i = ranges.iterator();
		
		while (i.hasNext())
		{
			r = i.next();
			if (r.isTouching(range))
			{			
				i.remove();
				range = range.merge(r);
			}
		}
		
		ranges.add(range);		
		
	}
	
	public void addRangeSet(RangeSet rangeset)
	{
		for (Range r : rangeset.getRanges())
		{
			addRange(r);
		}
	}
	
	public void removeRange(Range range)
	{
		Iterator<Range> i;
		RangeSet difference = new RangeSet();
		Range r;
		
		if (range == null) return;
		
		
		i = ranges.iterator();
		
		while (i.hasNext())
		{
			r = i.next();
			if (r.isOverlapping(range))
			{			
				i.remove();
				difference.addRangeSet( r.difference(range) );
				
			}
		}
		
		addRangeSet(difference);
		
	}
	
	public void clear()
	{
		ranges.clear();
	}
	
	public Iterator<Integer> iterator()
	{
		return new Iterator<Integer>(){

			Iterator<Range> rangeIterator = ranges.iterator();
			Iterator<Integer> numIterator;
			
			
			public boolean hasNext()
			{
				while (numIterator == null || !numIterator.hasNext()){
					if (! rangeIterator.hasNext()) return false;
					numIterator = rangeIterator.next().iterator();
				}
				
				return numIterator.hasNext();
			}

			public Integer next()
			{
				while (numIterator == null || !numIterator.hasNext()){
					if (! rangeIterator.hasNext()) throw new IndexOutOfBoundsException();
					numIterator = rangeIterator.next().iterator();
				}
				
				return numIterator.next();
				
			}

			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
		
		
	}
	
	public boolean isTouching(Range other)
	{
		
		for (Range r : ranges)
		{		
			if (r.isTouching(other)) return true;
		}
		
		return false;
		
	}
	
	public boolean isTouching(RangeSet other)
	{
		
		for (Range r : other.ranges)
		{
			if (isTouching(r)) return true;
		}
		
		return false;
	}
	
	
	public boolean isOverlapping(Range other)
	{
		
		for (Range r : ranges)
		{		
			if (r.isOverlapping(other)) return true;
		}
		
		return false;
		
	}
	
	public boolean isOverlapping(RangeSet other)
	{
		
		for (Range r : other.ranges)
		{
			if (isOverlapping(r)) return true;
		}
		
		return false;
	}
	
	public static void main(String args[])
	{
		
		
		Range r1 = new Range(1, 6, -1);
		Range r2 = new Range(11, 18, 3);
				
		RangeSet rs1 = new RangeSet();
		
		
		rs1.addRange(r1);
		rs1.addRange(r2);
		
		
		
		
		Range r4 = new Range(14, 133, 3);
				
		RangeSet rs2 = new RangeSet();
		
		
		rs2.addRange(r4);
		
		System.out.println(rs2.isTouching(rs1));

		
		
		
	}
	
	@Override
	public String show()
	{
		return ranges.show();
	}

	@Override
	public String show(String separator)
	{
		return ranges.show(separator);
	}
	
	
	public FList<Range> getRanges()
	{
		return ranges.toSink();
	}
	
}
