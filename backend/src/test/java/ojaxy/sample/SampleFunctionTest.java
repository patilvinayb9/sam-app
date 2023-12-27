package ojaxy.sample;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SampleFunctionTest {
  @Test
  public void successfulResponse() {
    SampleFunction sampleFunction = new SampleFunction();
    APIGatewayProxyResponseEvent result = sampleFunction.handleRequest(null, null);
    assertEquals(200, result.getStatusCode().intValue());
    assertEquals("application/json", result.getHeaders().get("Content-Type"));
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains("\" Now working fine on both local and prod\""));
    assertTrue(content.contains("\"location\""));
  }
}
