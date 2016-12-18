import org.junit.Before;
import org.junit.Test;
import services.DependencyResolver;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class DependencyResolverShould {

  private static final String ITEM_WITHOUT_DEPENDENCY = "price";
  private static final String LEVEL_1 = "level1";
  private static final List<String> LEVEL_1_DEPENDENCIES = new ArrayList<String>() {
    {
      add("level2");
      add("level3");
    }
  };

  private static final String LEVEL_2 = "level2";
  private static final List<String> LEVEL_2_DEPENDENCIES = new ArrayList<String>() {
    {
      add("level3");
    }
  };

  private static final String LEVEL_3 = "level3";
  private static final String IMAGE = "image";
  private static final String QUALITY = "quality";

  private DependencyResolver dependencyResolver = new DependencyResolver();
  private List<String> dependencies;
  private List<String> items;

  @Before
  public void setUp() {
    dependencies = new ArrayList<String>();
    items = new ArrayList<String>();
  }

  @Test
  public void returnEmptyListIfThereAreNoDependencies() {
    dependencies = dependencyResolver.resolve(ITEM_WITHOUT_DEPENDENCY);

    assertTrue(dependencies.isEmpty());
  }

  @Test
  public void returnDependenciesForItemWithOneDependency() {
    dependencies = dependencyResolver.resolve(LEVEL_2);

    assertTrue(dependencies.size() == LEVEL_2_DEPENDENCIES.size());
    assertTrue(dependencies.containsAll(LEVEL_2_DEPENDENCIES));
  }

  @Test
  public void returnDependenciesFromItems() {
    items.add(LEVEL_2);
    items.add(IMAGE);
    dependencies = dependencyResolver.resolve(items);

    assertTrue(dependencies.size() == 2);
    assertTrue(dependencies.contains("level3"));
    assertTrue(dependencies.contains("quality"));

  }

  @Test
  public void returnInnerDependenciesForItems() {
    items.add(LEVEL_1);
    items.add(IMAGE);
    dependencies = dependencyResolver.resolve(items);

    assertTrue(dependencies.size() == 4);
    assertTrue(dependencies.contains("level2"));
    assertTrue(dependencies.contains("level3"));
    assertTrue(dependencies.contains("quality"));
  }

  @Test
  public void getUniqueDependencies() {
    items.add(LEVEL_1);
    items.add(LEVEL_3);
    items.add(IMAGE);
    items.add(QUALITY);

    dependencies = dependencyResolver.getUniqueDependencies(items);

    assertTrue(dependencies.size() == 2);
    assertTrue(dependencies.contains("level1"));
    assertTrue(dependencies.contains("image"));
  }
}
