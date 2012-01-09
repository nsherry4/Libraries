package bolt.scripting;

import org.python.modules.synchronize;

import bolt.scripting.languages.Language;

import fava.signatures.FnMap;


public class BoltMap<T1, T2> extends BoltScripter implements FnMap<T1, T2>{

	private String inputName, outputName;
	
	
	public BoltMap(String language, boolean compilable, String inputName, String outputName, String script) {
		
		this(customLanguage(language, compilable), inputName, outputName, script);
		
	}
	
	
	public BoltMap(Language language, String inputName, String outputName, String script) {
		super(language, script);
		
		this.inputName = inputName;
		this.outputName = outputName;
		
	}
	



	@Override
	public T2 f(T1 v) {
		
		if (hasSideEffects || !multithreaded) {
			synchronized(this)
			{
				return do_f(v);
			}
		} else {
			return do_f(v);
		}
		
	}
	
	private T2 do_f(T1 v)
	{
		if (!hasSideEffects) clear();
		set(inputName, v);
				
		try {
			
			run();
			
			return (T2)get(outputName);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoltScriptExecutionException("Error executing script\n\n" + e.getMessage() + "\n-----\n" + getStdErr(), e);
		}
	}

}
