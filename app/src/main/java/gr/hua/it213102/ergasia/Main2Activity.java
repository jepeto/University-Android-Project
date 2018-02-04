package gr.hua.it213102.ergasia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Button searchB = (Button) findViewById(R.id.searchB);
        final EditText uidText = (EditText) findViewById(R.id.uidText2);
        final DBHandler db = new DBHandler(this);

        //Get all users in order to populate dropdown list with timestamps (Could be implemented in DBHandler)
        List<User> arr = db.getAllUsers();
        List<String> dts = new ArrayList<>();
        dts.add("");//Adding empty element
        for(User u : arr){
            dts.add(u.getDt());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dts); //Simple array adapter to add elements to the spinner
        spinner.setAdapter(dataAdapter);

        //OnClick Listener for the search button
        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = uidText.getText().toString(); //Getting uid from editText
                String dt = spinner.getSelectedItem().toString(); //Getting selected item from spinner
                List<User> result = db.getSearch(uid,dt); //Getting list of users that match our search terms
                Intent intent = new Intent(v.getContext(), Main3Activity.class); //Create new Intent to the third Activity
                String data = "";   //String that will hold the results and will be sent to the next activity (Could be implemented in activity3)
                if(result.size() > 0) {
                    for(User u : result){ //For each user u in our list
                        data += "------------------------------------\n";
                        data += "ID: "+ u.getId() +"\n";
                        data += "User ID: " + u.getUid() + "\n";
                        data += "Longitude: " + u.getLon() + "\n";
                        data += "Latitude: " + u.getLat() + "\n";
                        data += "Timestamp: " + u.getDt() + "\n";
                    }
                }else{ //If our result is empty
                    data = "No Users found with User ID: " + uid; //Not including timestamp
                }
                intent.putExtra("Data", data); //Put the calculated data into the intent
                startActivity(intent); //Starting the third Activity
            }
        });
    }
}
