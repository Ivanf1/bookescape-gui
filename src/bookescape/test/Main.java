package bookescape.test;

import java.io.IOException;

import bookescape.persistence.DatabaseInfoProducer;
import bookescape.utils.CustomQueryLoader;

public class Main {

  public static void main(String[] args) {
   // DatabaseInfoProducer.makeInfo();
    CustomQueryLoader.readFromInputStream().forEach((q) -> System.out.println(q.prettyPrint()));
      //System.out.println(CustomQueryLoader.readFromInputStream());

  }

}
