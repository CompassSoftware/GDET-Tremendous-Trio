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
      List<GHCommit> commits =
        GithubDataExtractionTool.getCommits(repo);
      System.out.print(GithubDataExtractionTool.commitsToString(commits));
      // for (GHCommit commit : commits) {
      //   GHCommit.ShortInfo cinfo =
      //     GithubDataExtractionTool.getCommitShortInfo(commit);
      //   System.out.println(cinfo.getMessage());
      //   System.out.println(cinfo.getAuthor());
      //   System.out.println(cinfo.getCommitDate());
      // }
    }
  }
}
