package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CollectionUtil {
	public static List removeDuplicateWithOrder(List list) {
		Set<String> set = new HashSet<String>();
		List<Object> newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element.toString())) {
				newList.add(element);
			}
		}
		return newList;

	}
}
