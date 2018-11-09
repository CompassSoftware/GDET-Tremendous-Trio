package org.compass.gdet;
import org.kohsuke.github.*;
import java.io.IOException;

public class GithubDataExtractionTool
{
  private GitHub github;

  public GithubDataExtractionTool()
  {
    try {
      github = GitHub.connect();
    }
    catch (IOException e) {
      System.out.println("Could not access ~/.github with exception " + e);
    }
  }

  public boolean checkConnection() {
    try {
      if (github != null) {
        github.checkApiUrlValidity();
        return true;
      }
      else {
        return false;
      }
    }
    catch (IOException e) {
      return false;
    }
  }
}
