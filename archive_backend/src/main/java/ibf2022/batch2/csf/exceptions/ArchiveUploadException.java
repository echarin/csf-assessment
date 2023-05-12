package ibf2022.batch2.csf.exceptions;

public class ArchiveUploadException extends Exception {
    public ArchiveUploadException(String message) {
        super(message);
    }

    public ArchiveUploadException(String message, Throwable t) {
        super(message, t);
    }
}
