package bookescape.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bookescape.persistence.CustomQuery;

public class CustomQueryLoader implements ICustomQueryProducer {
  public static List<CustomQuery> readFromInputStream() {
    ClassLoader classLoader = CustomQueryLoader.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("queries.txt");
    List<CustomQuery> queryList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      String comment = "";
      String query = "";
      while ((line = br.readLine()) != null) {
        // check if end of query
        if (line.equals("==")) {
          // make custom query
          CustomQuery c = new CustomQuery(comment, query);
          queryList.add(c);
          comment = "";
          query = "";
          continue;
        }

        // skip empty lines
        if (line.equals("")) continue;

        // check if line is a comment
        if (line.substring(0, 2).equals("--")) {
          comment += line + "\n";
        } else {
          query += line + " \n";
        }
      }
      return queryList;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public List<CustomQuery> getCustomQueries() {
    return readFromInputStream();
  }

}
