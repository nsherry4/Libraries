package plural.executor.eachindex;


import plural.executor.AbstractExecutor;
import plural.executor.ExecutorState;
import plural.executor.map.MapExecutor;
import fava.signatures.FnEach;

public abstract class EachIndexExecutor extends AbstractExecutor {

	protected FnEach<Integer>		eachIndex;
	
	
	public EachIndexExecutor(int size, FnEach<Integer> eachIndex)
	{

		super();
		
		this.eachIndex = eachIndex;
		
		super.setWorkUnits(size);
		
	}
	
	@Override
	public int getDataSize()
	{
		return super.getWorkUnits();
	}

	

	/**
	 * Sets the {@link PluralMap} for this {@link SplittingMapExecutor}. Setting the PluralMap after creation of the
	 * {@link MapExecutor} allows the associated {@link PluralMap} to query the {@link SplittingMapExecutor} for
	 * information about the work block for each thread. This method will return without setting the PluralMap if
	 * the current PluralMap's state is not {@link ExecutorState.State#UNSTARTED}
	 * 
	 * @param map
	 *            the {@link PluralMap} to execute.
	 */
	public void setEachIndex(FnEach<Integer> eachIndex)
	{
		if (this.eachIndex != null && super.getState() != ExecutorState.UNSTARTED) return;
		this.eachIndex = eachIndex;
	}
	

	/**
	 * Executes the EachIndexExecutor, waiting until the processing is complete.
	 */
	public abstract void executeBlocking();
	
}
