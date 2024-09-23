package co.nexlabs.javaappsample.sqlitemagic.exception;

import android.support.annotation.NonNull;

public final class OperationFailedException extends RuntimeException {
  public OperationFailedException(@NonNull String message) {
    super(message);
  }

  public OperationFailedException(@NonNull String message, Throwable cause) {
    super(message, cause);
  }
}
