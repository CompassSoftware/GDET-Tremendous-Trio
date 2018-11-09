package org.compass.gdet;

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
}
