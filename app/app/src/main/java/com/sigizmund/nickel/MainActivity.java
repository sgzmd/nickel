package com.sigizmund.nickel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

  EditText urlEditBox;
  Button downloadButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    urlEditBox = (EditText) findViewById(R.id.url_edit_box);
    downloadButton = (Button) findViewById(R.id.download_button);

    downloadButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = urlEditBox.getText().toString();
        Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
