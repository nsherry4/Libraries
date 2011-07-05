package plural.executor;

import eventful.Eventful;


public abstract class AbstractExecutor extends Eventful implements PluralExecutor{

	private String			name;

	private ExecutorState	state;
	private int				workUnits;
	private int				workUnitsCompleted;
	
	protected ExecutorSet<?>	executorSet;
	
	private boolean			stalling = false; 
	
	
	public AbstractExecutor() {
		state = ExecutorState.UNSTARTED;
		workUnitsCompleted = 0;
		setWorkUnits(-1);
	}
	
	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#getName()
	 */
	@Override
	public String getName() 
	{
		return name;
	}
	
	@Override
	public void setName(String name) 
	{
		this.name = name;
	}

	
	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#setStalling(boolean)
	 */
	@Override
	public void setStalling(boolean stalling)
	{
		this.stalling = stalling;
	}
	
	

	
	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#getState()
	 */
	@Override
	public synchronized ExecutorState getState()
	{
		return state;
	}


	@Override
	public ExecutorSet<?> getExecutorSet() {
		return executorSet;
	}

	@Override
	public void setExecutorSet(ExecutorSet<?> executorSet) {
		this.executorSet = executorSet;
	}

	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#advanceState()
	 */
	@Override
	public synchronized void advanceState()
	{

		switch (state) {
			case UNSTARTED:
				state = stalling? ExecutorState.STALLED : ExecutorState.WORKING;
				break;
			case WORKING:
			case STALLED:
				state = ExecutorState.COMPLETED;
				break;
			default:
				break;
		}

		updateListeners();

	}
	


	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#markTaskSkipped()
	 */
	@Override
	public synchronized void markTaskSkipped()
	{
		state = ExecutorState.SKIPPED;
		updateListeners();
	}


	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#workUnitCompleted()
	 */
	@Override
	public synchronized void workUnitCompleted()
	{
		workUnitCompleted(1);
	}


	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#workUnitCompleted(int)
	 */
	@Override
	public synchronized void workUnitCompleted(int unitCount)
	{
		this.workUnitsCompleted += unitCount;
		updateListeners();
	}


	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#getProgress()
	 */
	@Override
	public synchronized double getProgress()
	{
		return ((double) workUnitsCompleted) / workUnits;
	}


	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#getWorkUnits()
	 */
	@Override
	public synchronized int getWorkUnits()
	{
		return workUnits;
	}
	
	



	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#setWorkUnits(int)
	 */
	@Override
	public synchronized void setWorkUnits(int units)
	{
		if (state == ExecutorState.WORKING || state == ExecutorState.STALLED) return;
		if (units < workUnitsCompleted) return;
		if (units < 0) return;
		workUnits = units;
	}
	
	
	
	/**
	 * This method will be called once by each ThreadWorker after being dispatched from
	 * {@link MapExecutor#execute(int threadCount)}. Work should be assigned to the {@link Task} from
	 * here.
	 */
	protected abstract void workForExecutor();

	/**
	 * Implementations of MapExecutor should call this method to begin processing with the desired number of
	 * threads. The appropriate number of threads will be acquired, and each will call into
	 * {@link MapExecutor#workForExecutor()}
	 * 
	 * @param numThreads
	 */
	protected void execute(int numThreads)
	{
		if (numThreads <= 0) numThreads = 1;
				
		Runnable r = new Runnable() {
		
			public void run()
			{
				workForExecutor();
			}
		};
				
		PluralThreadPool.execute(r, numThreads);
		
	}
	
	/**
	 * Calculates the number of threads that should be used by this {@link MapExecutor}
	 * 
	 * @param threadsPerCore
	 *            a multiplier that determines the number of threads per processor to use.
	 * @return the total number of threads which should be used.
	 */
	public int calcNumThreads(double threadsPerCore)
	{
		int threads = (int) Math.round(Runtime.getRuntime().availableProcessors() * threadsPerCore);
		if (threads <= 0) threads = 1;
		return threads;
	}


	/**
	 * Calculates the number of threads that should be used by this {@link MapExecutor}
	 * 
	 * @return the total number of threads which should be used.
	 */
	public int calcNumThreads()
	{
		return calcNumThreads(1.0);
	}
	
		
	
	/* (non-Javadoc)
	 * @see plural.executor.PluralExecutor#getDataSize()
	 */
	@Override
	public abstract int getDataSize();
	
}
