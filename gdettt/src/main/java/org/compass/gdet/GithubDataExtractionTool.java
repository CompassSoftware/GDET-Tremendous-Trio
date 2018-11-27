package org.compass.gdet;
import org.kohsuke.github.*;
import java.io.IOException;
import java.util.List;

public class GithubDataExtractionTool
{
  private GitHub github;
  private GDETOutputHandler outputController;

  /**Constructor
  * This constructor will try to establish a connection to gthub with the
  * username and password held in ~/.github and will print an error message
  * if it is unable to.
  */
  public GithubDataExtractionTool()
  {
    try {
      github = GitHub.connect();
      outputController = new GDETOutputHandler( java.io.OutputStreamWriter.class );
    }
    catch (IOException e) {
      System.err.println("Could not access ~/.github with exception " + e);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**checkConnection
  * This method will check the connection to github and will return false if a
  * connection has not been established.
  *
  * @return:
  *   boolean - true if connection exists, false if not.
  */
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

  /**getRepository
  * This method will try to get a repository with the name <reponame> and return
  * a GHRepository object if successful or null if not.
  *
  * @params:
  *   reponame - the string name of a repo in the standard github format
  *     <username>/<reponame>
  *
  * @return:
  *   GHRepository - a Github repository object if successful, null if not.
  */
  public GHRepository getRepository(String reponame) {
    assert(checkConnection());
    try {
      return github.getRepository(reponame);
    }
    catch (IOException e) {
      return null;
    }
  }

  /**getIssues
  * This method will try to get a list of all issues for a given repository.
  *
  * @params:
  *   repo - the GHRepository object to get Issues from
  *
  * @return:
  *   List<GHIssue> - a list of the GHIssues queried or null if the query fails.
  */
  public static List<GHIssue> getIssues(GHRepository repo) {
    return repo.listIssues(GHIssueState.ALL).asList();
  }

  /**getCommits
  * This method will try to get a list of commits for a given repository.
  *
  * @params:
  *   repo - the GHRepository object to get commits from.
  *
  *   @return:
  *   List<GHCommitComment> - an iterable containing the commit comments for this repo
  *   or null if a list could not be found
  */
  public static List<GHCommitComment> getCommitComments(GHRepository repo)
  {
	  return repo.listCommitComments().asList();
  }

 /*
  *
  * @return:
  *   List<GHCommit> - an iterable containing the commits for this repo
  *     or null if a list could not be found.
  */
  public static List<GHCommit> getCommits(GHRepository repo) {
    return repo.listCommits().asList();
  }

  /**getCommitShortInfo
  * This method will try to get the shortInfo object for a given commit.
  *
  * @params:
  *   commit - the GHCommit object to get the shortinfo from.
  *
  * @return:
  *   GHCommit.shortInfo - the shortInfo object from the GHCommit.
  */
  public static GHCommit.ShortInfo getCommitShortInfo(GHCommit commit) {
    try {
      return commit.getCommitShortInfo();
    }
    catch (IOException e){
      return null;
    }
  }

  /*issueToSTring
  * converts a given GHCommit to a stirng.
  *
  * @params:
  *   issue - the issue to convert to a string
  *
  * @return:
  *   string - a string representation of the issue.
  */
  public static String issueToString(GHIssue issue) {
    try {
      String response = "";
      response += String.format("%32s\n", "").replace(" ", "-");
      response += "#" + issue.getNumber() + " " + issue.getTitle() + "\n";
      response += issue.getUser().getName() + "\n";
      response += String.format("%32s\n\n", "").replace(" ", "-");
      return response;
    }
    catch (IOException e) {
      return "";
    }
  }

  /**commitsToString
  * converts a commit to a formatted string representing the commit.
  *
  * @params:
  *   commit - a List of commits to get a formatted string for.
  *
  * @return:
  *   string - a formatted string representation of the commit.
  */
  public static String commitToString(GHCommit commit) {
    String response = "";
    response += String.format("%32s\n", "").replace(" ", "-");
    GHCommit.ShortInfo cinfo =
      GithubDataExtractionTool.getCommitShortInfo(commit);
    response += cinfo.getAuthor().getName() + "\n";
    response += cinfo.getCommitDate() + "\n";
    response += cinfo.getMessage() + "\n";
    response += String.format("%32s\n\n", "").replace(" ", "-");
    return response;
  }
  /**commitsCommentToString
  * converts a commitComments to a formatted string representing the commit.
  *
  * @params:
  *   commit - a List of commitComments to get a formatted string for.
  *
  * @return:
  *   string - a formatted string representation of the commit.
  */
  public static String commitCommentToString(GHCommitComment cComment) {
<<<<<<< HEAD
      try {
=======
    try {
>>>>>>> e40899d292aa56e75f6dd7d5e258ad787ee48d6e
      String response = "";
      response += String.format("%32s\n", "").replace(" ", "-");
      response += cComment.getUser().getLogin() + "\n";
      response += "\nCommit Details:\n";
      response += commitToString(cComment.getCommit());
      response += cComment.getBody() + "\n";
      response += String.format("%32s\n\n", "").replace(" ", "-");
      return response;
    }
    catch (IOException e) {
      return "";
    }
<<<<<<< HEAD
 }  
 
=======
  }

>>>>>>> e40899d292aa56e75f6dd7d5e258ad787ee48d6e
  /**getRepositoryMetaData
  * This method will return a string representation of the repository's details.
  *
  * @params:
  *   repo - a GHRepository object to get metadata from
  *
  * @return:
  *   string - a foratted stirng representation of the repo's metadata
  */
  public static String getRepositoryMetaData(GHRepository repo) {
    String response = "";
    response += String.format("%32s\n", "").replace(" ", "-");
    if (repo != null) {
      response += repo.getName() + "\n";
      response += String.format("Owned by: %s\n", repo.getOwnerName());
    }
    response += String.format("%32s\n", "").replace(" ", "-");
    return response;
  }
}
