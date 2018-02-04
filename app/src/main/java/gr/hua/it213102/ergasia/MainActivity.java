package gr.hua.it213102.ergasia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitB = (Button) findViewById(R.id.button);
        Button continueB = (Button) findViewById(R.id.button2);
        Button updateB = (Button) findViewById(R.id.updateB);
        final EditText dtText = (EditText) findViewById(R.id.dtText); //Max length = 20 from xml
        final EditText latText = (EditText) findViewById(R.id.latText); //Only numbers and comma allowed from xml
        final EditText lonText = (EditText) findViewById(R.id.lonText); //Only numbers and comma allowed from xml
        final EditText uidText = (EditText) findViewById(R.id.uidText); //Max length = 10 from xml
        final EditText idText = (EditText) findViewById(R.id.idText);
        final DBHandler db = new DBHandler(this);

        //Click Listeners for Buttons//

        //Submit Button Listener
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User(); //New empty user object
                //Setting newUser's parameters from editTexts
                newUser.setDt(dtText.getText().toString());
                newUser.setUid(uidText.getText().toString());
                newUser.setLat(Float.parseFloat(latText.getText().toString()));
                newUser.setLon(Float.parseFloat(lonText.getText().toString()));

                //Add user to db and check result
                if(db.addUser(newUser) == -1){
                    Toast.makeText(v.getContext(),R.string.failed, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(),R.string.success, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Continue button listener that starts second activity
        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Main2Activity.class);
                startActivity(intent);
            }
        });

        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User(); //New empty user object
                //Setting newUser's parameters from editTexts
                newUser.setDt(dtText.getText().toString());
                newUser.setUid(uidText.getText().toString());
                newUser.setLat(Float.parseFloat(latText.getText().toString()));
                newUser.setLon(Float.parseFloat(lonText.getText().toString()));
                newUser.setId(Integer.parseInt(idText.getText().toString()));
                if(db.updateUser(newUser) == 0){
                    Toast.makeText(v.getContext(),"No user updated!!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(),"User " + newUser.getUid() + " updated successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
