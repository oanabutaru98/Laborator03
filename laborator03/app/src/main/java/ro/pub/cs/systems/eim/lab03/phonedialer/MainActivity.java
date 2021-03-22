package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private ImageButton callImageButton;
    private ImageButton hangupImageButton;
    private ImageButton deleteImageButton;
    private Button button;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String existingText = ((Button)v).getText().toString();
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + existingText);
        }
    }

    private DeleteClickListener deleteClickListener = new DeleteClickListener();
    private class DeleteClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String existingText = phoneNumberEditText.getText().toString();
            if (existingText.length() > 0)
                phoneNumberEditText.setText(existingText.substring(0, existingText.length() - 1));
        }
    }

    private CallClickListener callClickListener = new CallClickListener();
    private class CallClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangupClickListener hangupClickListener = new HangupClickListener();
    private class HangupClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        phoneNumberEditText = (EditText)findViewById(R.id.phone_number_edit_text);
        for (int i = 0; i < Constants.buttonIds.length; i++) {
            button = (Button)findViewById(Constants.buttonIds[i]);
            button.setOnClickListener(buttonClickListener);
        }

        deleteImageButton = (ImageButton)findViewById(R.id.backspace_image_button);
        deleteImageButton.setOnClickListener(deleteClickListener);

        callImageButton = (ImageButton)findViewById(R.id.call_image_button);
        callImageButton.setOnClickListener(callClickListener);

        hangupImageButton = (ImageButton)findViewById(R.id.hangup_image_button);
        hangupImageButton.setOnClickListener(hangupClickListener);

    }


}