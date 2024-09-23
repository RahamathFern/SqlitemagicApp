package co.nexlabs.javaappsample.sqlitemagic;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * Compiled SQL UPDATE statement.
 */
public final class CompiledUpdate {
  @NonNull
  private final SupportSQLiteStatement updateStm;
  @NonNull
  private final String tableName;
  @NonNull
  private final DbConnectionImpl dbConnection;

  CompiledUpdate(@NonNull SupportSQLiteStatement updateStm,
                 @NonNull String tableName,
                 @NonNull DbConnectionImpl dbConnection) {
    this.updateStm = updateStm;
    this.tableName = tableName;
    this.dbConnection = dbConnection;
  }

  /**
   * Execute this compiled update statement against a database.
   * <p>
   * This method runs synchronously in the calling thread.
   *
   * @return Number of updated rows
   */
  @WorkerThread
  public int execute() {
    final int affectedRows;
    synchronized (updateStm) {
      affectedRows = updateStm.executeUpdateDelete();
    }
    if (affectedRows > 0) {
      dbConnection.sendTableTrigger(tableName);
    }
    return affectedRows;
  }

  /**
   * Creates a {@link Single} that when subscribed to executes this compiled
   * update statement against a database and emits nr of updated records to downstream
   * only once.
   *
   * @return Deferred {@link Single} that when subscribed to executes the statement and emits
   * its result to downstream
   */
  @NonNull
  @CheckResult
  public Single<Integer> observe() {
    return Single.fromCallable(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        return execute();
      }
    });
  }

  static final class Builder {
    UpdateSqlNode sqlTreeRoot;
    int sqlNodeCount;
    Update.TableNode tableNode;
    final ArrayList<String> args = new ArrayList<>();
    DbConnectionImpl dbConnection = SqliteMagic.getDefaultDbConnection();

    @NonNull
    @CheckResult
    CompiledUpdate build() {
      final String sql = SqlCreator.getSql(sqlTreeRoot, sqlNodeCount);
      final SupportSQLiteStatement stm = dbConnection.compileStatement(sql);
      final ArrayList<String> args = this.args;
      for (int i = args.size(); i != 0; i--) {
        final String arg = args.get(i - 1);
        if (arg != null) {
          stm.bindString(i, arg);
        } else {
          stm.bindNull(i);
        }
      }
      return new CompiledUpdate(stm, tableNode.tableName, dbConnection);
    }
  }
}
