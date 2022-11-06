package net.lax1dude.eaglercraft.compat;

public class UnexpectedThrowable extends RuntimeException {

	public UnexpectedThrowable(Throwable t) {
		super("Encountered an unexpected exception", t);
	}

	public UnexpectedThrowable(String str, Throwable t) {
		super(str, t);
	}

}
