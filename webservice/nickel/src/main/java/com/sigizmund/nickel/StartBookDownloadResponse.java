package com.sigizmund.nickel;

import java.util.List;

public class StartBookDownloadResponse {
  private List<String> requestedChapterUrls;
  private String storyName;
  private String storyAuthor;
  private String storyId;

  public StartBookDownloadResponse() {
  }

  public StartBookDownloadResponse(
      String storyId,
      String storyName,
      String storyAuthor,
      List<String> requestedChapterUrls) {
    this.requestedChapterUrls = requestedChapterUrls;
    this.storyName = storyName;
    this.storyAuthor = storyAuthor;
    this.storyId = storyId;
  }

  public String getStoryId() {
    return storyId;
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
