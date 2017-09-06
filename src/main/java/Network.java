import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Network {
	private int size;
	private Map<Integer, Element> linkedElementsMap;

	public Network(int size) throws IllegalAccessException {
		if (size < 0) {
			throw new IllegalArgumentException("Number of elements should be a positive number");
		}
		this.size = size;
		linkedElementsMap = new HashMap<>(size);
		for (int value = 1; value <= size; value++) {
			linkedElementsMap.put(value, new Element(value));
		}
	}

	public void connect(int first, int second) {
		validateParameters(first, second);
		if (first != second) {
			Element firstElement = linkedElementsMap.get(first);
			Element secondElement = linkedElementsMap.get(second);
			firstElement.links.add(secondElement);
			secondElement.links.add(firstElement);
		}
	}

	public boolean query(int first, int second) {
		validateParameters(first, second);
		if (first == second) {
			return true;
		}
		Element firstElement = linkedElementsMap.get(first);
		HashSet<Integer> explored = new HashSet<>();
		explored.add(first);
		return search(firstElement, second, explored);
	}

	private boolean search(Element source, int target, Set<Integer> explored) {
		boolean result = false;
		for (Element element : source.links) {
			if (explored.contains(element.value)) {
				continue;
			}
			if (element.value == target || result) {
				return true;
			} else {
				explored.add(element.value);
				result = search(element, target, explored);
			}
		}
		return result;
	}


	private void validateParameters(int first, int second) {
		if (first < 0 || second < 0) {
			throw new IllegalArgumentException("The values should be a positive number");
		}
		if (first > size || second > size) {
			throw new IllegalArgumentException("The values should not be greater than " + size);
		}
	}


	private class Element {
		private int value;
		private Set<Element> links;

		public Element(int value) {
			this.value = value;
			links = new HashSet<>();
		}
	}

}
