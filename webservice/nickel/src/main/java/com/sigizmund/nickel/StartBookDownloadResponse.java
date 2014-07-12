package com.sigizmund.nickel;

import java.util.List;

public class StartBookDownloadResponse {
  private List<String> requestedChapterUrls;
  private String storyName;
  private String storyAuthor;

  public StartBookDownloadResponse() {
  }

  public StartBookDownloadResponse(

      String storyName,
      String storyAuthor,
      List<String> requestedChapterUrls) {
    this.requestedChapterUrls = requestedChapterUrls;
    this.storyName = storyName;
    this.storyAuthor = storyAuthor;
  }

  public List<String> getRequestedChapterUrls() {
    return requestedChapterUrls;
  }

  public String getStoryName() {
    return storyName;
  }

  public String getStoryAuthor() {
    return storyAuthor;
  }
}
