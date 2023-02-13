package com.example.barterbarn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HelpDocumentationActivity extends AppCompatActivity implements View.OnClickListener {

    //Introductory Paragraphs
    TextView helpIntro;
    TextView helpIntro2;

    //Buttons
    Button helpAccountButton;
    Button helpEditButton;
    Button helpSearchButton;
    Button helpMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_documentation);
        initializeToolbar();

        helpIntro = findViewById(R.id.introHelp1);
        helpIntro2 = findViewById(R.id.introHelp2);

        helpAccountButton = (Button)findViewById(R.id.help_account_button);
        helpAccountButton.setOnClickListener(this);
        helpSearchButton = (Button)findViewById(R.id.help_search_button);
        helpSearchButton.setOnClickListener(this);
        helpEditButton = (Button)findViewById(R.id.help_edit_button);
        helpEditButton.setOnClickListener(this);
        helpMessageButton = (Button)findViewById(R.id.help_message_button);
        helpMessageButton.setOnClickListener(this);

        helpIntro.setText(R.string.helpIntro);
        helpIntro2.setText(R.string.helpIntro_2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutButton:
                // User chose to logout
                logoutUser();
                return true;

            case R.id.backToHome:
                // Go to previous page
                launchMainActivity();
                return true;
        }

        return false;
    }

    public void onClick (View v)
    {
        int id = v.getId();
        int textId = -1;

        switch (id) {
            case R.id.help_account_button :
                textId = R.string.help_account_information;
                break;
            case R.id.help_search_button :
                textId = R.string.help_search_information;
                break;
            case R.id.help_edit_button :
                textId = R.string.help_edit_information;
                break;
            case R.id.help_message_button :
                textId = R.string.help_messaging_information;
                break;
            default:
                break;
        }

        if (textId >= 0) launchInfoActivity(textId);

    }

    protected void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        launchCreateAccountActivity();
    }

    protected void launchCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    protected void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.help_appbar);
        setSupportActionBar(toolbar);
    }

    public void launchInfoActivity(int textId) {
        Intent intent = (new Intent(this, InfoActivity.class));
        intent.putExtra("HELP_INFORMATION", textId);
        startActivity(intent);
    }
}
