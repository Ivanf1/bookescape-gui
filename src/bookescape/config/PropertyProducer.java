package bookescape.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public final class PropertyProducer {
  private static Properties properties;
  
  /**
   * Reads content of {@code application.properties} file.
   */
  private static void loadProperties() {
    properties = new Properties();
    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String applicationConfigPath = rootPath + "application.properties";
    
    try (InputStream stream = new FileInputStream(applicationConfigPath)) {
      properties.load(stream);
    } catch (FileNotFoundException e1) {
      throw new RuntimeException("Cannot find application.properties configuration file.");
    } catch (final IOException e) {
      throw new RuntimeException("Configuration file cannot be loaded.");
    }
    
  }
  
  public static String getProperty(String key) {
    if (properties == null) loadProperties();
    return (String) properties.get(key);
  }

}
