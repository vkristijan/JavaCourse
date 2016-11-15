package hr.fer.android.jmbag0036483282;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Android page that is used to display the result of an mathematical operation. The operation is
 * done on a different page and this one is used just to display the result.
 *
 * @author Kristijan Vulinovic
 */
public class DisplayActivity extends AppCompatActivity {
    /**
     * The key used to send the message text.
     */
    public static String MESSAGE_KEY = "message";

    /**
     * An array of e-mail addresses that should receive e-mail notifications.
     */
    private static String[] RECIPIENTS = {
            "ana.baotic@infinum.hr"
    };

    /**
     * The subject of e-mail messages.
     */
    private static String SUBJECT = "0036483282: Dz report";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle data = getIntent().getExtras();
        final String text = data.getString(DisplayActivity.MESSAGE_KEY);

        TextView message = (TextView)findViewById(R.id.message);
        message.setText(text);

        Button btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(text);
            }
        });
    }

    /**
     * Method used to send an e-mail report to the e-mail addresses defined in {@link #RECIPIENTS}.
     * The message will be sent using a messaging application already installed and configured on
     * the device.
     *
     * @param text
     *          the text that should be sent in the e-mail body.
     */
    private void sendEmail(String text){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, RECIPIENTS);
        i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        i.putExtra(Intent.EXTRA_TEXT, text);
        try {
            startActivity(Intent.createChooser(i, "Send e-mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(
                    DisplayActivity.this,
                    "Unable to send e-mail, no e-mail client found.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
