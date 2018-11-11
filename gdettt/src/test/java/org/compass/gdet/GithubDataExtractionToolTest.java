package org.compass.gdet;

import org.kohsuke.github.*;
import org.junit.jupiter.api.*;
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
}
