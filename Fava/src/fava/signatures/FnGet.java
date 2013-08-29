package fava.signatures;

/**
 * Function signature for a function which accepts no values and returns a value
 * @author Nathaniel Sherry, 2010-2011
 *
 * @param <T1>
 */

public interface FnGet <T1> extends FnSignature {

	T1 f();
	
}
