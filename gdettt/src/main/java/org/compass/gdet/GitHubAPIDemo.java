package org.compass.gdet;
import org.kohsuke.github.*;
import java.util.List;
import java.util.Map;

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

      //Print Commit Count Per User
      System.out.print(startSection);
      System.out.println("COMMIT-COUNT-PER-USER");
      System.out.print(endSection);
      Map<GHUser, Integer> commitsPerUser =
        GithubDataExtractionTool.getCommitCountPerUser(repo);
      for (GHUser user : commitsPerUser.keySet()) {
        System.out.printf("User: %-20s    Commit Count: %d\n",
          GithubDataExtractionTool.getGHUserNameWithFallback(user),
          commitsPerUser.get(user));
      }

      //Print Commit Count Per User
      System.out.print(startSection);
      System.out.println("PULL-REQUEST-OPENED-COUNT-PER-USER");
      System.out.print(endSection);
      Map<GHUser, Integer> prPerUser =
        GithubDataExtractionTool.getPullRequestOpenedCountPerUser(repo, false);
      for (GHUser user : prPerUser.keySet()) {
        System.out.printf("User: %-20s    PR Opened Count: %d\n",
          GithubDataExtractionTool.getGHUserNameWithFallback(user),
          prPerUser.get(user));
      }

      //Print Commit Count Per User
      System.out.print(startSection);
      System.out.println("PULL-REQUEST-MERGED-COUNT-PER-USER");
      System.out.print(endSection);
      Map<GHUser, Integer> prMergedPerUser =
        GithubDataExtractionTool.getPullRequestOpenedCountPerUser(repo, true);
      for (GHUser user : prMergedPerUser.keySet()) {
        System.out.printf("User: %-20s    PR Merged Count: %d\n",
          GithubDataExtractionTool.getGHUserNameWithFallback(user),
          prMergedPerUser.get(user));
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

      //Print Pull Request Comments
      System.out.print(startSection);
      System.out.println("Pull Request Review Comments");
      System.out.print(endSection);

     List<GHPullRequestReviewComment> pcrs = GithubDataExtractionTool.getPullRequestReviewComments(repo);
     for (GHPullRequestReviewComment pcr : pcrs) {
       System.out.print(GithubDataExtractionTool.pullRequestReviewCommentsToString(pcr));
    }

     System.out.print(startSection);
     System.out.println("Branches");
     System.out.print(endSection);

     List<GHBranch> gbs = GithubDataExtractionTool.getBranches(repo);
     for(GHBranch gb : gbs)
	   {
		    System.out.print(GithubDataExtractionTool.branchToString(gb));
     }

    }
  }
}
