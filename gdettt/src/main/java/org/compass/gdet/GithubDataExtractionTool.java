package org.compass.gdet;
import org.kohsuke.github.*;
import java.io.IOException;
import java.util.List;

public class GithubDataExtractionTool
{
  private GitHub github;

  /**Constructor
  * This constructor will try to establish a connection to gthub with the
  * username and password held in ~/.github and will print an error message
  * if it is unable to.
  */
  public GithubDataExtractionTool()
  {
    try {
      github = GitHub.connect();
    }
    catch (IOException e) {
      System.out.println("Could not access ~/.github with exception " + e);
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
    try {
      return github.getRepository(reponame);
    }
    catch (IOException e) {
      return null;
    }
  }
}
