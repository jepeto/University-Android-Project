package gr.hua.it213102.ergasia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView results = (TextView) findViewById(R.id.resultsText);

        //Android Google Developers Documentation: The right way to get Extras//

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString = null;
            } else {
                newString = extras.getString("Data");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("Data");
        }

        results.setText(newString);
    }
}
