package psych169.psychapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.M;
import static psych169.psychapp.R.id.TypeOfAppointment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner patienttype = (Spinner) findViewById(TypeOfAppointment);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reasons, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        patienttype.setAdapter(adapter);
    }


    public void DoTheThing(View view) {

        EditText Name = (EditText) findViewById(R.id.Name);
        EditText PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        EditText ReasonForVisit = (EditText) findViewById(R.id.ReasonForVisit);
        EditText Month = (EditText) findViewById(R.id.Month);
        EditText Day = (EditText) findViewById(R.id.Day);
        EditText Year = (EditText) findViewById(R.id.Year);
        EditText Hours = (EditText) findViewById(R.id.Hours);
        EditText Minutes = (EditText) findViewById(R.id.Minutes);
        Spinner patientType = (Spinner) findViewById(R.id.TypeOfAppointment);

        String pt = patientType.getSelectedItem().toString();
        String pn = null;
        String rname = null;
        Integer min = null;
        Integer mo = null;
        String rfv = null;
        Integer day = null;
        Integer hr = null;
        Integer yr = null;
        try {
            rname = Name.getText().toString();
            pn = PhoneNumber.getText().toString();
            rfv = ReasonForVisit.getText().toString();
            mo = Integer.parseInt(Month.getText().toString());
            day = Integer.parseInt(Day.getText().toString());
            yr = Integer.parseInt(Year.getText().toString());
            hr = Integer.parseInt(Hours.getText().toString());
            min = Integer.parseInt(Minutes.getText().toString());
        } catch (NumberFormatException a) {
            Toast ERROR = Toast.makeText(getApplicationContext(), "Invalid Entries", Toast.LENGTH_SHORT);
            ERROR.show();
        }

        String Appointment = ("Name: " + rname + "\n" + "Patient Type: " + pt + "\n" + "Phone Number: " + pn + "\n" + "Reason for visit: " + rfv + "\n" + "Date: " + mo + "/" + day + "/" + yr + "\n" + "Time: " + hr + ":" + min);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jmenende@uci.edu"});
        intent.putExtra(Intent.EXTRA_SUBJECT, rname + "'s appointment");
        intent.putExtra(Intent.EXTRA_TEXT, Appointment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }

    }




        }

