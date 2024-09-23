package co.nexlabs.javaappsample.sqlitemagic.entity;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import co.nexlabs.javaappsample.sqlitemagic.entity.EntityOperationBuilder;
import co.nexlabs.javaappsample.sqlitemagic.entity.EntityUpdateByColumnBuilder;
import io.reactivex.Completable;

/**
 * Builder for bulk persist operation.
 * <p>
 * Persist is an operation that first tries to update and if that fails then inserts
 * the provided entity.
 */
public interface EntityBulkPersistBuilder extends EntityOperationBuilder<EntityBulkPersistBuilder>,
        EntityUpdateByColumnBuilder<EntityBulkPersistBuilder> {
  /**
   * Configure this operation to ignore {@code null} values inside entities while
   * persisting provided objects.
   *
   * @return Operation builder
   */
  @NonNull
  @CheckResult
  EntityBulkPersistBuilder ignoreNullValues();

  /**
   * Execute this configured bulk persist operation against a database.
   * Operation will be executed inside a transaction.
   *
   * @return {@code true} if the operation was successful; {@code false} when some operation failed
   * and this operation was rolled back.
   */
  boolean execute();

  /**
   * Creates a {@link Completable} that when subscribed to executes this configured bulk
   * persist operation against a database and emits operation result to downstream.
   * Operation will be executed inside a transaction. If the operation was
   * successful then complete will be emitted to downstream.
   * If the operation failed then it will be rolled back and error will be emitted to downstream.
   *
   * @return Deferred {@link Completable} that when subscribed to executes the operation and emits
   * its result to downstream
   */
  @NonNull
  @CheckResult
  Completable observe();
}
