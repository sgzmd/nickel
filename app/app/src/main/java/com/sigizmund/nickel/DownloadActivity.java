package com.sigizmund.nickel;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import com.sigizmund.nickelApi.NickelApi;
import com.sigizmund.nickelApi.model.StartBookDownloadRequest;
import com.sigizmund.nickelApi.model.StartBookDownloadResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DownloadActivity extends Activity {

  public static final String ROOT_URL = "http://10.0.2.2:8080/_ah/api";
  private TextView downloadText;
  private NickelApi api;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_download);

    String url = getIntent().getStringExtra("URL");

    downloadText = (TextView) findViewById(R.id.download_text);
    downloadText.setText(url);

    NickelApi.Builder apiBuilder = new NickelApi.Builder(
        AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
    
    apiBuilder.setRootUrl(ROOT_URL);
    
    this.api = apiBuilder.build();

    new AsyncTask<String, String, Long>(){
      @Override
      protected Long doInBackground(String... strings) {
        String url = strings[0];

        try {
          updateLabelText("Downloading URL " + url);

          String mainPageHtml = slurpUrl(url);

          NickelApi.Nickel.StartDownload startDownload = api
              .nickel()
              .startDownload(new StartBookDownloadRequest()
                  .setChapterText(mainPageHtml)
                  .setChapterUrl(url));

          Log.d(getLocalClassName(), startDownload.toString());

          StartBookDownloadResponse response = startDownload.execute();


          for (String chapterUrl : response.getRequestedChapterUrls()) {
            updateLabelText("Downloading URL " + chapterUrl);
            String chapterHtml = slurpUrl(chapterUrl);
            api.nickel().addChapter(new StartBookDownloadRequest()
                .setChapterUrl(chapterUrl)
                .setChapterText(chapterHtml)).execute();
          }

          updateLabelText("All chapters downloaded");

          return 0L;
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
      }
    }.execute(url);
  }

  private void updateLabelText(final String text) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        downloadText.setText(text);
      }
    });
  }

  private String slurpUrl(String url) {
    try {
      URL u = new URL(url);

      Log.d(getLocalClassName(), "Opening a URL connection to " + url);

      InputStream inputStream = u.openConnection().getInputStream();

      Log.d(getLocalClassName(), "InputStream is now open");

      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder sb = new StringBuilder();
      String line = "";

      int lineNumber = 0;
      for ( ;line != null; line = br.readLine(), ++lineNumber) {
        sb.append(line);
      }

      Log.d(getLocalClassName(), "Read " + lineNumber + " lines from " + url);

      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
      Log.wtf(getLocalClassName(), "Something terrible happened", e);
      // terminate
      throw new RuntimeException(e);
    }
  }

  private void showErrorAndTerminate(String errorText) {
    Log.d(this.getLocalClassName(), errorText);
    Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
    Intent data = new Intent();
    data.putExtra("com.sigizmund.nickel.DownloadActivityFailure", errorText);
    if (getParent() == null) {
      setResult(Activity.RESULT_CANCELED, data);
    } else {
      getParent().setResult(Activity.RESULT_CANCELED, data);
    }

    finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.download, menu);
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
