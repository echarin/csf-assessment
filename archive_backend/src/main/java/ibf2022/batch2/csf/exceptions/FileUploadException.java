package ibf2022.batch2.csf.exceptions;

public class FileUploadException extends Exception {
    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable t) {
        super(message, t);
    }
}
