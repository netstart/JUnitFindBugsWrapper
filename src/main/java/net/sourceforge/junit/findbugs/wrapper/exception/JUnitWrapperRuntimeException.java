package net.sourceforge.junit.findbugs.wrapper.exception;

public class JUnitWrapperRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 3437466508608119344L;

    public JUnitWrapperRuntimeException() {
        super();
    }

    public JUnitWrapperRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JUnitWrapperRuntimeException(String message) {
        super(message);
    }

    public JUnitWrapperRuntimeException(Throwable cause) {
        super(cause);
    }
}
