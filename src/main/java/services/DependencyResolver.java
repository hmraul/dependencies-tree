package services;

import java.util.ArrayList;
import java.util.List;

public class DependencyResolver {
  public List<String> resolve(String item) {
    return getDependencies(item);
  }

  public List<String> resolve(List<String> items) {
    List<String> values = new ArrayList<String>(items);
    List<String> dependencies = new ArrayList<String>();
    if (items.isEmpty()) {
      return dependencies;
    }

    List<String> itemDependencies = resolve(values.remove(0));

    dependencies.addAll(itemDependencies);
    dependencies.addAll(resolve(itemDependencies));
    dependencies.addAll(resolve(values));
    return dependencies;
  }

  public List<String> getUniqueDependencies(List<String> items) {
    List<String> values = new ArrayList<String>(items);
    List<String> allDependencies = resolve(values);

    values.removeAll(allDependencies);
    return values;
  }

  private List<String> getDependencies(String item) {
    List<String> dependencies = new ArrayList<String>();

    if (item.equals("level1")) {
      dependencies.add("level2");
      dependencies.add("level3");
    }

    if (item.equals("level2")) {
      dependencies.add("level3");
    }

    if (item.equals("image")) {
      dependencies.add("quality");
    }

    return dependencies;
  }
}
