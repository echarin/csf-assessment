package ibf2022.batch2.csf.exceptions;

public class BundleUploadException extends Exception {
    public BundleUploadException(String message) {
        super(message);
    }

    public BundleUploadException(String message, Throwable t) {
        super(message, t);
    }
}
