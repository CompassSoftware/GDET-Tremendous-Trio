package org.compass.gdet;
import org.kohsuke.github.*;
import java.util.List;

public class GitHubAPIDemo {
  public static void main( String[] args ) {
    System.out.println("GitHub Data Extration Tool Demo");
    GithubDataExtractionTool git = new GithubDataExtractionTool();
    if (!git.checkConnection()) {
      System.out.println("Error Establishing Connection");
    }
    System.out.println("Successfully Established Connection");
    GHRepository repo = git.getRepository(
      "CompassSoftware/GDET-Tremendous-Trio");
    if (repo != null) {
      System.out.print(GithubDataExtractionTool.getRepositoryMetaData(repo));
      String startSection = String.format("\n\n%32s\n", "").replace(" ", "*");
      String endSection = String.format("%32s\n\n\n", "").replace(" ", "*");
      System.out.print(startSection);
      System.out.println("COMMITS");
      System.out.print(endSection);
      List<GHCommit> commits =
        GithubDataExtractionTool.getCommits(repo);
      for (GHCommit commit : commits) {
        System.out.print(GithubDataExtractionTool.commitToString(commit));
      }
      System.out.print(startSection);
      System.out.println("ISUES");
      System.out.print(endSection);
      List<GHIssue> issues = GithubDataExtractionTool.getIssues(repo);
      for (GHIssue issue : issues) {
        System.out.print(GithubDataExtractionTool.issueToString(issue));
      }
    }
  }
}
