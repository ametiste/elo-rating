package org.ametiste.elo.infrastructure.fs;

/**
 * Created by atlantis on 9/24/15.
 */
public class UnsupportedFileFormatException extends RuntimeException {

    public UnsupportedFileFormatException(String s) {
        super(s);
    }
}
