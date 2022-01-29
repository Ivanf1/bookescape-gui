package bookescape.utils;

import java.util.List;

import bookescape.persistence.CustomQuery;

public interface ICustomQueryProducer {
  List<CustomQuery> getCustomQueries();

}
