package com.example.barterbarn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;

public class InfoActivity extends AppCompatActivity {

int helpCode;
TextView helpTitle;
TextView helpSubheader1;
TextView helpSubheader2;
TextView helpSubheader3;
TextView helpInformation;
TextView helpInformation2;
TextView helpInformation3;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);
    initializeToolbar();
    helpTitle = findViewById(R.id.help_title);

    helpSubheader1 = findViewById(R.id.help_subheader1);
    helpSubheader2 = findViewById(R.id.help_subheader2);
    helpSubheader3 = findViewById(R.id.help_subheader3);

    helpInformation = findViewById(R.id.help_information);
    helpInformation2 = findViewById(R.id.help_information2);
    helpInformation3 = findViewById(R.id.help_information3);

    Intent i = getIntent();
    Bundle b = i.getExtras();

    if (b!=null) {

        helpCode = (Integer) b.get("HELP_INFORMATION");
        helpInformation.setText(helpCode);

        if (helpCode == R.string.help_account_information) {
            startAccountHelp();
        }
       else if (helpCode == R.string.help_search_information) {
            startSearchHelp();
        }
        else if (helpCode == R.string.help_edit_information) {
            startEditHelp();
        }
        else if (helpCode == R.string.help_messaging_information) {
            startMessagingHelp();
        }

    }

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
                launchHelpDocumentationActivity();
                return true;
        }

        return false;
    }

    protected void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        launchCreateAccountActivity();
    }

    protected void startAccountHelp() {
        helpTitle.setText(R.string.help_account_header);

        helpSubheader1.setText(R.string.help_account_subheader1);
        helpSubheader2.setText(R.string.help_account_subheader2);
        helpSubheader3.setText(R.string.help_account_subheader3);

        helpInformation2.setText(R.string.help_account_information2);
        helpInformation3.setText(R.string.help_account_information3);
    }

    protected void startSearchHelp() {
        helpTitle.setText(R.string.help_search_header);

        helpSubheader1.setText(R.string.help_search_subheader1);
        helpSubheader2.setText(R.string.help_search_subheader2);
        helpSubheader3.setText(null);

        helpInformation2.setText(R.string.help_search_information2);
        helpInformation3.setText(null);
    }

    protected void startEditHelp() {
        helpTitle.setText(R.string.help_edit_header);

        helpSubheader1.setText(R.string.help_edit_subheader1);
        helpSubheader2.setText(R.string.help_edit_subheader2);
        helpSubheader3.setText(null);

        helpInformation2.setText(R.string.help_edit_information2);
        helpInformation3.setText(null);
    }

    protected void startMessagingHelp() {
        helpTitle.setText(R.string.help_messaging_header);

        helpSubheader1.setText(R.string.help_messaging_subheader1);
        helpSubheader2.setText(R.string.help_messaging_subheader2);
        helpSubheader3.setText(R.string.help_messaging_subheader3);

        helpInformation2.setText(R.string.help_messaging_information2);
        helpInformation3.setText(R.string.help_messaging_information3);
    }

    protected void launchHelpDocumentationActivity() {
        Intent intent = new Intent(this, HelpDocumentationActivity.class);
        startActivity(intent);
    }

    protected void launchCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.info_appbar);
        setSupportActionBar(toolbar);
    }

}
