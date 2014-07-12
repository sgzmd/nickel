package com.sigizmund.nickel;

public class StartBookDownloadRequest {
  private String chapterUrl;
  private String chapterText;

  public StartBookDownloadRequest() {
  }

  public StartBookDownloadRequest(String chapterUrl, String chapterText) {
    this.chapterUrl = chapterUrl;
    this.chapterText = chapterText;
  }

  public String getChapterUrl() {
    return chapterUrl;
  }

  public String getChapterText() {
    return chapterText;
  }
}
