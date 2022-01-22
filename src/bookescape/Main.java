package bookescape;

import bookescape.persistence.ArbitraryQuery;

public class Main {

  public static void main(String[] args) {
    ArbitraryQuery.executeArbitraryQuery("select * from libro limit 4");
  }

}
