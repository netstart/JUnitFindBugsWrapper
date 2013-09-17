package net.sourceforge.junit.findbugs.wrapper.exception;

public class JUnitWrapperException extends Exception {
    private static final long serialVersionUID = -7229180930566436654L;

    public JUnitWrapperException() {
        super();
    }

    public JUnitWrapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public JUnitWrapperException(String message) {
        super(message);
    }

    public JUnitWrapperException(Throwable cause) {
        super(cause);
    }
}
