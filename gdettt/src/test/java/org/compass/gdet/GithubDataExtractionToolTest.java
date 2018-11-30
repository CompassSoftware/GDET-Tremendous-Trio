package org.compass.gdet;

import org.kohsuke.github.*;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GithubDataExtractionToolTest
{
  /*
  * A basic test for our constructor.  For a connection test, see
  * our test for checkConnection.
  */
  @Test
  public void shouldCreateAGithubDataExtractionToolInstance() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    assertTrue(gdet != null);
  }

  /*
  * A basic test for our checkConnection method.  Should succeed in connection.
  */
  @Test
  public void shouldHaveAValidConnection() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    assertTrue(gdet.checkConnection());
  }

  /*
  * A basic test for our getRepository method.  Should return a valid repository
  * for this repository.
  */
  @Test
  public void shouldReturnAValidRepository() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    assertTrue(repo != null);
    assertTrue(repo instanceof GHRepository);
    assertTrue(repo.getName().equals("GDET-Tremendous-Trio"));
    assertTrue(repo.getOwnerName().equals("CompassSoftware"));
  }

  /*
  * A basic test for our getRepositoryMetaData method.  Should return a
  * formatted metadata string for this repository.
  */
  @Test
  public void shouldReturnACorrectlyFormattedString() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    String expectedOutput = "--------------------------------\n" +
      "GDET-Tremendous-Trio\n" +
      "Owned by: CompassSoftware\n" +
      "--------------------------------\n";
    assertTrue(GithubDataExtractionTool.getRepositoryMetaData(repo).equals(
      expectedOutput));
  }

  /*
  * A basic test for our getCommits method.  Should return a list of valid
  * commits.
  */
  @Test
  public void shouldReturnAListOfValidCommits() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    List<GHCommit> commits = GithubDataExtractionTool.getCommits(repo);
    for (GHCommit commit : commits) {
      assertTrue(commit instanceof GHCommit);
      assertTrue(commit != null);
    }
    assertTrue(commits.size() > 0);
  }

  /*
  * A basic test for our getPullRequests method.  Should get a list of valid issues.
  */
  @Test
  public void shouldGetListPullRequests() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    List<GHPullRequest> prs = GithubDataExtractionTool.getPullRequests(repo,GHIssueState.ALL);
    for (GHPullRequest pr : prs) {
      assertTrue(pr instanceof GHPullRequest);
      assertTrue(pr != null);
    }
    assertTrue(prs.size() > 0);
  }

  /*
  * A basic test for our getCommitShortInfo method.  Should get a valid
  * GHCommit.ShortInfo object for a given commit.
  */
  @Test
  public void shouldGetValidCommitShortInfo() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    List<GHCommit> commits = GithubDataExtractionTool.getCommits(repo);
    GHCommit commit = commits.get(0);
    GHCommit.ShortInfo info =
      GithubDataExtractionTool.getCommitShortInfo(commit);
    assertTrue(info != null);
    assertTrue(info.getAuthor() != null);
  }

  /*
  * A basic test for our commitToString method.  Should get a valid formatted
  * string representation of our commit list.
  */
  @Test
  public void shouldGetAFormattedCommitString() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    List<GHCommit> commits = GithubDataExtractionTool.getCommits(repo);
    List<GHCommit> commitSublist = commits.subList(commits.size() - 2,
      commits.size());
    String result = "";
    for (GHCommit commit : commitSublist) {
      result += GithubDataExtractionTool.commitToString(commit);
    }
    String expected = "--------------------------------\n" +
      "Taylor Edwards\n" +
      "Wed Nov 07 14:38:37 GMT 2018\n" +
      "Initial Project Setup\n" +

      "--------------------------------\n\n" +
      "--------------------------------\n" +
      "Jay Fenwick\n" +
      "Mon Nov 05 14:08:28 GMT 2018\n" +
      "Initial commit\n" +
      "--------------------------------\n\n";
    assertTrue(expected.equals(result));
  }

  /*
  * A basic test for our pullRequestToString method.  Should get a valid formatted
  * string representation of our commit list.
  */
  @Test
  public void shouldGetAFormattedPullRequestString() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
	  gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    List<GHPullRequest> pr = GithubDataExtractionTool.getPullRequests(repo,GHIssueState.CLOSED);
    String prString = GithubDataExtractionTool.pullRequestToString(pr.get(pr.size()-1));
    String expected = 
    "----------------------------------------------------------------\n" +
    "Initial Project Setup\n" +
    "Created By: Taylor\n" +
    "Created Date: Wed Nov 07 14:40:32 GMT 2018\n" +
    "Merged By: Gurney Buchanan\n"+
    "Merged Date:Fri Nov 09 13:39:06 GMT 2018\n\n"+
    "Additions: 88\n"+
    "Deletions: 1\n"+
    "Number of Commits: 1\n"+
    "----------------------------------------------------------------\n\n";
    assertTrue(expected.equals(prString));
  }

  /*
  * A basic test for our getIssues method.  Should get a list of valid issues.
  */
  @Test
  public void shouldGetListOfIssues() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    List<GHIssue> issues = GithubDataExtractionTool.getIssues(repo);
    for (GHIssue issue : issues) {
      assertTrue(issue instanceof GHIssue);
      assertTrue(issue != null);
    }
    assertTrue(issues.size() > 0);
  }

  /*
  * A basic test for our issueToString method.  Should get a formatted string
  * for an issue.
  */
  @Test
  public void shouldGetCommitString() {
    GithubDataExtractionTool gdet = new GithubDataExtractionTool();
    GHRepository repo =
      gdet.getRepository("CompassSoftware/GDET-Tremendous-Trio");
    List<GHIssue> issues = GithubDataExtractionTool.getIssues(repo);
    GHIssue testIssue = null;
    for (GHIssue issue: issues) {
      if (issue.getNumber() == 1) {
        testIssue = issue;
      }
    }
    String result = GithubDataExtractionTool.issueToString(testIssue);
    String expected = "--------------------------------\n" +
      "#1 Research api.github.com\n" +
      "Jay Fenwick\n" +
      "--------------------------------\n\n";
    assertTrue(expected.equals(result));
  }
}
