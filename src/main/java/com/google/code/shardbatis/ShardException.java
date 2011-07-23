/**
 * 
 */
package com.google.code.shardbatis;

/**
 * @author sean.he
 * 
 */
public class ShardException extends Exception {

	private static final long serialVersionUID = 1793760050084714190L;

	public ShardException() {
		super();
	}

	public ShardException(String msg) {
		super(msg);
	}

	public ShardException(String msg, Throwable t) {
		super(msg, t);
	}

	public ShardException(Throwable t) {
		super(t);
	}
}
