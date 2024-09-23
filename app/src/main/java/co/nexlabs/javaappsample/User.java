package co.nexlabs.javaappsample;

import com.google.auto.value.AutoValue;
import com.siimkinks.sqlitemagic.annotation.Id;
import com.siimkinks.sqlitemagic.annotation.IgnoreColumn;
import com.siimkinks.sqlitemagic.annotation.Table;

@Table(persistAll = true)
@AutoValue
public abstract class User {

    public static Builder builder() {
        return new AutoValue_User.Builder();
    }

    public static User create(long customerID, int customerType, String name, String number,
                              long branchID) {
        return builder()
                .customerID(customerID)
                .customerType(customerType)
                .name(name)
                .number(number)
                .branchID(branchID)
                .build();
    }

    @Id(autoIncrement = false)
    public abstract long customerID();

    public abstract int customerType();

    public abstract String name();

    public abstract String number();

    public abstract long branchID();

    @IgnoreColumn
    public abstract Builder toBuilder();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder customerID(long customerID);

        public abstract Builder customerType(int customerType);

        public abstract Builder name(String name);

        public abstract Builder number(String number);

        public abstract Builder branchID(long branchID);

        public abstract User build();
    }
}

