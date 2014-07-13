package com.sigizmund.nickel;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;

@Api(name = "nickelApi", version = "v1", namespace = @ApiNamespace(
    ownerDomain = "sigizmund.com",
    ownerName = "sgzmd",
    packagePath = ""
))
public class NickelAPI {
  public StartBookDownloadResponse startBookDownload(StartBookDownloadRequest request) {
//    return new StartBookDownloadResponse(new Converter().convert("ID"), "HELO", "EHLO", ImmutableList.<String>of());
    return null;
  }
}
