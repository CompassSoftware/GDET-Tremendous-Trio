package org.compass.gdet;

public class GitHubAPIDemo {
  public static void main( String[] args ) {
    System.out.println("GitHub Data Extration Tool Demo");
    GithubDataExtractionTool git = new GithubDataExtractionTool();
    if (git.checkConnection()) {
      System.out.println("Successfully Established Connection");
    }
    else {
      System.out.println("Error Establishing Connection");
    }
  }
}
