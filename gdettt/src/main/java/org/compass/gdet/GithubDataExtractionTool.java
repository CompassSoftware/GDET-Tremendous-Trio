package org.compass.gdet;
import org.kohsuke.github.*;
import java.io.IOException;
import java.lang.NullPointerException;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.ArrayList;

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
      github = GitHubBuilder.fromCredentials()
                            .withAbuseLimitHandler( AbuseLimitHandler.FAIL )
                            .withRateLimitHandler( RateLimitHandler.FAIL )
                            .build();
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

  /**getBranches
  * This method will try to get a list of all branches for a given repository.
  *
  * @params:
  *   repo - the GHRepository object to get branches from
  *
  * @return:
  *   List<GHBranch> - a list of the branch names in a repository.
  */
  public static List<GHBranch> getBranches(GHRepository repo) {
    try{
	    Map<String, GHBranch> gm = repo.getBranches();
	    List<GHBranch> gb = new ArrayList<GHBranch>(gm.values());
	    return gb;
    }
    catch(IOException e)
    {
	    return null;
    }
  }

  /**getCommitComments
  * This method will try to get a list of commit comments for a given repository.
  *
  * @params:
  *   repo - the GHRepository object to get commits comments from.
  *
  *   @return:
  *   List<GHCommitComment> - an iterable containing the commit comments for this repo
  *   or null if a list could not be found
  */
  public static List<GHCommitComment> getCommitComments(GHRepository repo)
  {
	  	return repo.listCommitComments().asList();
  }
  /**getPullRequests
  * This method will try to get a list of pull requests for a given reposito
ry based on a given state..
  *
  * @params:
  *   repo - the GHRepository object to get pull requests from.
  *   state - the state of the pull requests you want
  *   @return:
  *   List<GHPullRequest> - an iterable containing the pull requests for this repo
  *   or null if a list could not be found
  */
  public static List<GHPullRequest> getPullRequests(GHRepository repo, GHIssueState state)
  {
          return repo.listPullRequests(state).asList();
  }

  /**getPullRequestComments
  * This method will try to get a list of pull requests comments.
  *
  * @params:
  *   repo - the GHRepository object to get pull requests from.
  *   @return:
  *   List<GHPullRequestReviewComment> - an iterable containing the pull requests for this repo
  *   or null if a list could not be found
  */
  public static List<GHPullRequestReviewComment> getPullRequestReviewComments(GHRepository repo)
  {
     List<GHPullRequest> prs = getPullRequests(repo, GHIssueState.ALL);
     return getPullRequestReviewComments(prs);
  }

  /**getPullRequestComments
  * This method will try to get a list of pull requests comments.
  *
  * @params:
  *   prs- A list of pull requests to get the comments from

  *   @return:
  *   List<GHPullRequestReviewComment> - an iterable containing the pull requests comments for this repo
  *   or null if a list could not be found
  */
  public static List<GHPullRequestReviewComment> getPullRequestReviewComments(List<GHPullRequest> prs)
  {
	try{
	List<GHPullRequestReviewComment> prct = new ArrayList<GHPullRequestReviewComment>();

	for(GHPullRequest pr : prs)
	{
		List<GHPullRequestReviewComment> prc = pr.listReviewComments().asList();
		prct.addAll(prc);
	}
		return prct;
	}
	catch(IOException e)
	{
		return null;
	}

  }

  /**getCommits
  *
  * @return:
  *   List<GHCommit> - an iterable containing the commits for this repo
  *     or null if a list could not be found.
  */
  public static List<GHCommit> getCommits(GHRepository repo) {
    return repo.listCommits().asList();
  }

  /** getCommitCountPerUser
  * Gets a list of all users who have committed to the repository along with
  * the number of commits they've made.
  *
  * @params:
  *   repo - the GHRepository object to get a list of commits from.
  *
  * @return:
  *   Map<GHUser, Integer> - a map between all users that have committed to the
  *     repository and the number of commits they've made.Returns an empty map
  *     if an IOException is encountered.
  */
  public static Map<GHUser, Integer> getCommitCountPerUser(GHRepository repo) {
    List<GHCommit> commits = getCommits(repo);
    return getCommitCountPerUser(commits);
  }

  /** getCommitCountPerUser
  * Gets a list of all users who have committed to the repository along with
  * the number of commits they've made.
  *
  * @params:
  *   commits - a list of commits to find the commits counts per user from.
  *
  * @return:
  *   Map<GHUser, Integer> - a map between all users that have committed to the
  *     repository and the number of commits they've made.  Returns an empty map
  *     if an IOException is encountered.
  */
  public static Map<GHUser, Integer> getCommitCountPerUser(List<GHCommit> commits) {
    try {
      Map<GHUser, Integer> map = new WeakHashMap<GHUser, Integer>();
      for (GHCommit commit : commits) {
        GHUser committer = commit.getAuthor();
        if (map.containsKey(committer)) {
          map.put(committer, map.get(committer) + 1);
        }
        else {
          map.put(committer, 1);
        }
      }
      return map;
    }
    catch (IOException e) {
      return new WeakHashMap<GHUser, Integer>();
    }
  }


  /** getIssueCountPerUser
  * Generates a mapping between all users who have filed issues with
  * the repository and the number of issues they've made.
  *
  * @params:
  *   repo- a repository to find the issues counts per user from.
  *
  * @return:
  *   Map<GHUser, Integer> - a map of users to issue count
  *     Returns an empty map
  *     if an IOException is encountered.
  */
  public static Map<GHUser, Integer> getIssueCountPerUser(GHRepository repo) {
    List<GHIssue> commits = getIssues(repo);
    return getIssueCountPerUser(commits);
  }

  /** getIssueCountPerUser
  * Generates a mapping between all users who have filed issues with
  * the repository and the number of issues they've made.
  *
  * @params:
  *   issues - a list of issues to find the issues counts per user from.
  *
  * @return:
  *   Map<GHUser, Integer> - a map of users to issue count
  *     Returns an empty map
  *     if an IOException is encountered.
  */
  public static Map<GHUser, Integer> getIssueCountPerUser(List<GHIssue> issues) {
    try {
      Map<GHUser, Integer> map = new WeakHashMap<GHUser, Integer>();
      for (GHIssue issue : issues) {
        GHUser issueOpener = issue.getUser();
        if (map.containsKey(issueOpener)) {
          map.put(issueOpener, map.get(issueOpener) + 1);
        }
        else {
          map.put(issueOpener, 1);
        }
      }
      return map;
    }
    catch (IOException e) {
      return new WeakHashMap<GHUser, Integer>();
    }
  }

  /**getPullRequestOpenedCountPerUser
  *
  * This method will return a map of users and the count of pull requests
  *   opened per user.
  *
  * @params:
  *   repo - the repository to get a list of pull requests from, then get a
  *     pull request opened count for each user from.
  *
  * @return:
  *   Map<GHUser, Integer> - a map of GHUsers to the number of pull requests
  *     they've opened.
  */
  public static Map<GHUser, Integer> getPullRequestCountPerUser(
    GHRepository repo, boolean mergedBy) {
    return getPullRequestOpenedCountPerUser(getPullRequests(repo,
      GHIssueState.ALL), mergedBy);
  }

  /**getPullRequestOpenedCountPerUser
  *
  * This method will return a map of users and the count of pull requests
  *   opened per user.
  *
  * @params:
  *   List<GHPullRequest - a list of pull GHPullRequest objects.
  *
  * @return:
  *   Map<GHUser, Integer> - a map of GHUsers to the number of pull requests
  *     they've opened.
  */
  public static Map<GHUser, Integer> getPullRequestCountPerUser(
    List<GHPullRequest> prs, boolean mergedBy) {
    try {
      Map<GHUser, Integer> map = new WeakHashMap<GHUser, Integer>();
      for (GHPullRequest pr : prs) {
        GHUser prOpener = pr.getUser();
        if (mergedBy) {
          prOpener = pr.getMergedBy();
        }
        if (map.containsKey(prOpener)) {
          map.put(prOpener, map.get(prOpener) + 1);
        }
        else {
          map.put(prOpener, 1);
        }
      }
      return map;
    }
    catch (IOException e) {
      return new WeakHashMap<GHUser, Integer>();
    }
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

  /**getGHUserNameWithFallback
  * gets a GHUser's name or their login if their name is not set!
  *
  * @return the GHUser's name if it is set and login if not.  If an IOException
  *   occurs, it will return null.
  */
  public static String getGHUserNameWithFallback(GHUser user) {
    try {
      String name = user.getName();
      if (name == null) {
        name = user.getLogin();
      }
      return name;
    }
    catch (IOException e) {
      return null;
    }
    catch (NullPointerException e) {
      return "Unknown User";
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
  /*branchToString
  * converts a given branch to a stirng.
  *
  * @params:
  *   branch - the branch to convert to a string
  *
  * @return:
  *   string - a string representation of the branch.
  */
  public static String branchToString(GHBranch branch) {
    try {
      String response = "";
      response += String.format("%64s\n", "").replace(" ", "-");
      response += branch.getName() + "\n";
      response += "SHA: " + branch.getSHA1() + "\n";
      response += String.format("%64s\n\n", "").replace(" ", "-");

      return response;
    }
    catch (NullPointerException e) {
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
  /**pullRequestToString
  * converts a pull request to a formatted string representing the pull request.
  *
  * @params:
  *   pr - a representation of a pull request to convert to a string
  *   state - the state of the pull request
  * @return:
  *   string - a formatted string representation of the pull request..
  */
  public static String pullRequestToString(GHPullRequest pr) {
    try {
      String response = "";
      response += String.format("%64s\n", "").replace(" ", "-");
      response += pr.getTitle() + "\n";
      response += "Created By: "  + pr.getUser().getName() + "\n";
      response += "Created Date: " + pr.getCreatedAt() + "\n";
      response += "Merged By: " + pr.getMergedBy().getName() + "\n";
      response += "Merged Date:" + pr.getMergedAt() + "\n";
      response += "\nAdditions: ";
      response += pr.getAdditions();
      response += "\nDeletions: ";
      response += pr.getDeletions();
      response += "\nNumber of Commits: ";
      response += pr.getCommits() + "\n";
      response += String.format("%64s\n\n", "").replace(" ", "-");
      return response;
    }
    catch(IOException e)
    {
	return "";
    }
    catch (NullPointerException e) {
      return "";
    }
 }
  /**pullRequestCommentToString
  * converts a pull request comment to a formatted string representing the pull request.
  *
  * @params:
  *   prc - a representation of a pull request comment to convert to a string
  * @return:
  *   string - a formatted string representation of the pull request comment.
  */
  public static String pullRequestReviewCommentsToString(GHPullRequestReviewComment prc) {
    try {
      String response = "";
      response += String.format("%64s\n", "").replace(" ", "-");
      response += "Comment Created By: "  + prc.getUser().getName() + "\n";
      response += prc.getBody();
      response += String.format("%64s\n\n", "").replace(" ", "-");
      return response;
    }
    catch(IOException e)
    {
        return "";
    }
    catch (NullPointerException e) {
      return "";
    }
 }

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
