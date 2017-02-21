package components;

import java.util.List;

/**
 * Created by akorovin on 21.02.2017.
 */
public interface NER {
    List<String> getEntities(String slide);
}
