package com.sigizmund.nickel;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.common.base.Function;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;
import com.google.appengine.repackaged.com.google.common.collect.Iterables;

import javax.inject.Named;

import nickel.Chapter;
import nickel.FictionConverter$;

@Api(name = "nickelApi", version = "v1", namespace = @ApiNamespace(
    ownerDomain = "sigizmund.com",
    ownerName = "sgzmd",
    packagePath = ""
))
public class NickelAPI {
  @ApiMethod(name = "nickel.startDownload", path = "nickel", httpMethod = ApiMethod.HttpMethod.POST)
  public StartBookDownloadResponse startBookDownload(
      @Named("url") String url,
      @Named("html") String html) {

    StartBookDownloadResponse resp = new StartBookDownloadResponse(
        "",
        "",
        "",
        ImmutableList.copyOf(Iterables.transform(
        FictionConverter$.MODULE$.getStoryChapters(url, html),
        new Function<Chapter, String>() {
          @Override
          public String apply(Chapter chapter) {
            return chapter.url();
          }
        }
    )));

    return resp;
  }
}
