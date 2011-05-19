package plural.executor.maps.implementations;


import java.util.List;

import fava.signatures.FnMap;

import plural.executor.ExecutorSet;
import plural.executor.maps.MapExecutor;

/**
 * 
 * This {@link MapExecutor} executes a given {@link Task} on a single thread, and assigns work to the given
 * {@link Task} based on a ticket (number) representing one element of the problem set as defined by a
 * supplied problem size.This TaskExecutor does not accept a {@link ExecutorSet}, and will not update the given
 * {@link Task} as to the completion progress.
 * 
 * @author Nathaniel Sherry, 2009
 * 
 */

public class SimpleMapExecutor<T1, T2> extends MapExecutor<T1, T2>
{

	public SimpleMapExecutor(List<T1> sourceData, FnMap<T1, T2> t)
	{
		super(sourceData, t);
	}

	public SimpleMapExecutor(List<T1> sourceData, List<T2> targetList, FnMap<T1, T2> t)
	{
		super(sourceData, targetList, t);
	}

	@Override
	public int calcNumThreads()
	{
		return 1;
	}


	@Override
	public List<T2> executeBlocking()
	{
		super.advanceState();

		workForExecutor();
		
		if (super.executorSet != null && super.executorSet.isAbortRequested()) {
			super.executorSet.aborted(); 
		}

		super.advanceState();
		
		if (super.executorSet != null && super.executorSet.isAborted()) return null;
		return super.targetList;
		
	}


	@Override
	protected void workForExecutor()
	{
		for (int i = 0; i < super.getDataSize(); i++) {
			
			super.targetList.set(  i, super.map.f(super.sourceData.get(i))  );
			super.workUnitCompleted();
			
			if (super.executorSet.isAbortRequested()) return;
		}
	}

}
