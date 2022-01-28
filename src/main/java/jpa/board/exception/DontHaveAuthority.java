package jpa.board.exception;

public class DontHaveAuthority extends RuntimeException {
    public DontHaveAuthority() {
        super();
    }

    public DontHaveAuthority(String message) {
        super(message);
    }

    public DontHaveAuthority(String message, Throwable cause) {
        super(message, cause);
    }

    public DontHaveAuthority(Throwable cause) {
        super(cause);
    }

    protected DontHaveAuthority(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
