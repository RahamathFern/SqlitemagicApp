package co.nexlabs.javaappsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* User user = new User("John Doe", 25);
        user.insert().execute();  // Insert a user into the database*/
    }
}