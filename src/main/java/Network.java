import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Network {
    private int size;
    private Map<Integer, Element> elementsMap;

    private static class Element {
        private int value;
        private Set<Element> links;

        public Element(int value) {
            this.value = value;
            links = new HashSet<>();
        }
    }


    /**
     * Generates new network with number of elements passed as parameter
     *
     * @param size number of elements to be generated
     * @throws IllegalAccessException if number of elements is less than zero
     */
    public Network(int size) throws IllegalAccessException {
        if (size < 0) {
            throw new IllegalArgumentException("Number of elements should be a positive number");
        }
        this.size = size;
        elementsMap = new HashMap<>(size);
        for (int value = 1; value <= size; value++) {
            elementsMap.put(value, new Element(value));
        }
    }

    /**
     * Connects two elements
     *
     * @param first  first element to be connected
     * @param second second element to be connected
     */
    public void connect(int first, int second) {
        validateParameters(first, second);
        if (first != second) {
            Element firstElement = elementsMap.get(first);
            Element secondElement = elementsMap.get(second);
            firstElement.links.add(secondElement);
            secondElement.links.add(firstElement);
        }
    }

    /**
     * Checks whether two elements are connected directly or indirectly through other elements
     *
     * @param first  source element
     * @param second target element
     * @return returns true, if elements are connected, otherwise - false
     */
    public boolean query(int first, int second) {
        validateParameters(first, second);
        if (first == second) {
            return true;
        }
        Element firstElement = elementsMap.get(first);
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
}
