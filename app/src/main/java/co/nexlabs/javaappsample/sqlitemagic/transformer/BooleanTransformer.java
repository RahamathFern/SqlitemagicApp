package co.nexlabs.javaappsample.sqlitemagic.transformer;

import com.siimkinks.sqlitemagic.annotation.transformer.DbValueToObject;
import com.siimkinks.sqlitemagic.annotation.transformer.ObjectToDbValue;

/**
 * Transformer for {@code boolean} data types.
 */
public final class BooleanTransformer {
  @ObjectToDbValue
  public static Integer objectToDbValue(Boolean javaObject) {
    if (javaObject != null) {
      return javaObject ? 1 : 0;
    }
    return null;
  }

  @DbValueToObject
  public static Boolean dbValueToObject(Integer dbObject) {
    if (dbObject != null) {
      return dbObject == 1;
    }
    return null;
  }
}
