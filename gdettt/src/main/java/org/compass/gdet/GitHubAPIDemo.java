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

      //Print Commits
      System.out.print(startSection);
      System.out.println();
      System.out.println("COMMITS");
      System.out.print(endSection);
      List<GHCommit> commits =
        GithubDataExtractionTool.getCommits(repo);
      for (GHCommit commit : commits) {
        System.out.print(GithubDataExtractionTool.commitToString(commit));
      }

      //Print Issues
      System.out.print(startSection);
      System.out.println("ISSUES");
      System.out.print(endSection);
      List<GHIssue> issues = GithubDataExtractionTool.getIssues(repo);
      for (GHIssue issue : issues) {
        System.out.print(GithubDataExtractionTool.issueToString(issue));
      }

      //Print Pull Requests
      System.out.print(startSection);
      System.out.println("Pull Requests");
      System.out.print(endSection);

      System.out.println("Open Pull Requests");
      List<GHPullRequest> oprs = GithubDataExtractionTool.getPullRequests(repo, GHIssueState.OPEN);
      for (GHPullRequest opr : oprs) {
	  System.out.print(GithubDataExtractionTool.pullRequestToString(opr));
      }
      System.out.println("Closed Pull Requests");
      List<GHPullRequest> cprs = GithubDataExtractionTool.getPullRequests(repo, GHIssueState.CLOSED);
      for (GHPullRequest cpr : cprs) {
          System.out.print(GithubDataExtractionTool.pullRequestToString(cpr));
      }

    }
  }
}
