package com.sigizmund.nickel;

import com.google.appengine.repackaged.com.google.common.io.CharStreams;

import org.junit.Test;
import org.truth0.Truth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NickelAPITest {
  private static final String HTML;
  static {
    try {
      HTML = CharStreams.toString(new BufferedReader(new InputStreamReader(
          NickelAPITest.class.getResourceAsStream("/test.html"))));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  private static final String URL = "https://www.fanfiction.net/s/3384712/1/The-Lie-I-ve-Lived";

  @Test
  public void smokeTest() {
    NickelAPI api = new NickelAPI();
  }

  @Test
  public void initDownloadTest() throws IOException {
    NickelAPI api = new NickelAPI();
    StartBookDownloadResponse resp = api.startBookDownload(URL, HTML);
    Truth.ASSERT
        .that(resp.getRequestedChapterUrls().size())
        .is(24);

    // spot check
    Truth.ASSERT
        .that(resp.getRequestedChapterUrls().get(1))
        .is("https://www.fanfiction.net/s/3384712/2/The-Lie-I-ve-Lived");
  }
}
