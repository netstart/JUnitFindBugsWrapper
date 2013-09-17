package net.sourceforge.junit.findbugs.wrapper.exception;

public class JUnitWrapperFileNotFoundException extends JUnitWrapperException {
    private static final long serialVersionUID = -7229180930566436654L;

    public JUnitWrapperFileNotFoundException() {
        super();
    }

    public JUnitWrapperFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JUnitWrapperFileNotFoundException(String message) {
        super(message);
    }

    public JUnitWrapperFileNotFoundException(Throwable cause) {
        super(cause);
    }
}
