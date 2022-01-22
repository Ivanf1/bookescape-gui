package bookescape.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public final class PropertyProducer {
  private static Properties properties;
  
  public static void loadProperties() {
    if (properties != null) return;
    
    properties = new Properties();
    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String applicationConfigPath = rootPath + "application.properties";
    
    InputStream stream;
    try {
      stream = new FileInputStream(applicationConfigPath);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
      throw new RuntimeException("Cannot find application.properties configuration file.");
    }

    try {
      properties.load(stream);
    } catch (final IOException e) {
      throw new RuntimeException("Configuration file cannot be loaded.");
    }
  }
  
  public static String getProperty(String key) {
    return (String) properties.get(key);
  }

}
