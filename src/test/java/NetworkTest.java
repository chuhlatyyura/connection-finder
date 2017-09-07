import org.junit.Before;
import org.junit.Test;

public class NetworkTest {

    public static final int NETWORK_SIZE = 20;
    private Network network;

    @Before
    public void setup() throws Exception {
        network = new Network(NETWORK_SIZE);
    }

    @Test
    public void testConstructorValidParameters() throws Exception {
        new Network(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeParametersShouldThrowAnException() throws Exception {
        new Network(-1);
    }

    @Test
    public void testConnectValidParameters() throws Exception {
        network.connect(1, 2);
    }

    @Test
    public void testConnectToSelf() throws Exception {
        network.connect(1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectFirstValueGreaterThanElementsNumberShouldThrowAnException() throws Exception {
        network.connect(NETWORK_SIZE + 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectSecondValueGreaterThanElementsNumberShouldThrowAnException() throws Exception {
        network.connect(1, NETWORK_SIZE + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectBothValuesGreaterThanElementsNumberShouldThrowAnException() throws Exception {
        network.connect(NETWORK_SIZE + 1, NETWORK_SIZE + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectFirstNegativeValueShouldThrowAnException() throws Exception {
        network.connect(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectNegativeSecondValueShouldThrowAnException() throws Exception {
        network.connect(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectBothValuesNegativeShouldThrowAnException() throws Exception {
        network.connect(-1, -1);
    }

    @Test
    public void testConnectDuplicate() throws Exception {
        network.connect(1, 2);
        network.connect(1, 2);
    }

    @Test
    public void testQuerySelf() throws Exception {
        assert network.query(1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryFirsValueGreaterThanSizeShouldThrowAndException() throws Exception {
        network.query(NETWORK_SIZE + 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuerySecondValueGreaterThanSizeShouldThrowAndException() throws Exception {
        network.query(NETWORK_SIZE + 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryBothValuesGreaterThanSizeShouldThrowAndException() throws Exception {
        network.query(NETWORK_SIZE + 1, NETWORK_SIZE + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryFirstNegativeValueShouldThrowAnException() throws Exception {
        assert network.query(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuerySecondNegativeValueShouldThrowAnException() throws Exception {
        assert network.query(1, -1);
    }

    @Test
    public void testQueryOneLevel() throws Exception {
        network.connect(1, 2);
        assert network.query(1, 2);
    }

    @Test
    public void testQueryOneLevelChangedOrder() throws Exception {
        network.connect(1, 2);
        assert network.query(2, 1);
    }

    @Test
    public void testQueryTwoLevels() throws Exception {
        network.connect(1, 2);
        network.connect(2, 3);
        assert network.query(1, 3);
    }

    @Test
    public void testQueryTwoLevelsChangedOrder() throws Exception {
        network.connect(1, 2);
        network.connect(2, 3);
        assert network.query(3, 1);
    }

    @Test
    public void testQueryThreeLevels() throws Exception {
        network.connect(1, 2);
        network.connect(2, 3);
        network.connect(3, 4);
        assert network.query(1, 4);
    }

    @Test
    public void testQueryThreeLevelsChangeOrder() throws Exception {
        network.connect(1, 2);
        network.connect(2, 3);
        network.connect(3, 4);
        assert network.query(4, 1);
    }

    @Test
    public void testQueryThreeLevelsTargetInTheMiddle() throws Exception {
        network.connect(1, 2);
        network.connect(2, 3);
        network.connect(3, 4);
        assert network.query(1, 3);
    }

    @Test
    public void testQueryThreeLevelsBinary() throws Exception {
        network.connect(1, 2);
        network.connect(1, 3);
        network.connect(2, 4);
        network.connect(2, 5);
        network.connect(3, 6);
        network.connect(3, 7);
        network.connect(4, 8);
        network.connect(4, 9);
        assert network.query(1, 8);
    }

    @Test
    public void testQueryOnNetworkOneCheckEightToOne() throws Exception {
        buildNetworkOne();
        assert !network.query(8, 1);
    }

    @Test
    public void testQueryOnNetworkOneCheckTwelveToNine() throws Exception {
        buildNetworkOne();
        assert network.query(12, 9);
    }

    @Test
    public void testQueryOnNetworkOneCheckEighteenToFour() throws Exception {
        buildNetworkOne();
        assert network.query(18, 4);
    }

    @Test
    public void testQueryOnNetworkTwoCheckOneToFourteen() throws Exception {
        buildNetworkTwo();
        assert network.query(1, 14);
    }

    @Test
    public void testQueryOnNetworkTwoCheckThreeToNine() throws Exception {
        buildNetworkTwo();
        assert !network.query(3, 9);
    }

    @Test
    public void testQueryOnNetworkTwoCheckFourToNine() throws Exception {
        buildNetworkTwo();
        assert !network.query(4, 9);
    }

    /**
     * <pre>
     * 1----2----3    4    5
     * |\__ |        /
     * |   \|       /
     * 10   9    8 |  7    6
     *   \_      | |/
     *     \     |/
     * 11   12   13   14   15
     *       |__(______|
     *         /
     * 16   17---18   19   20
     * </pre>
     */
    private void buildNetworkOne() {
        network.connect(1, 2);
        network.connect(1, 9);
        network.connect(1, 10);
        network.connect(2, 3);
        network.connect(10, 12);
        network.connect(12, 14);

        network.connect(18, 17);
        network.connect(17, 13);
        network.connect(13, 8);
        network.connect(13, 7);
        network.connect(13, 4);
    }

    /**
     * <pre>
     * 1----2    3    4----5
     * |    |       /      |
     * |    |      /       |
     * 6    7----8    9    10
     * |    |   / \__      /
     * |    | /      \   /
     * 11   12   13   14   15
     * |     \___    /
     * |         \ /
     * 16---17---18---19   20
     * </pre>
     */
    private void buildNetworkTwo() {
        network.connect(1, 2);
        network.connect(1, 6);
        network.connect(6, 11);
        network.connect(11, 16);
        network.connect(16, 17);
        network.connect(17, 18);

        network.connect(18, 19);
        network.connect(18, 12);
        network.connect(18, 14);

        network.connect(2, 7);
        network.connect(7, 8);
        network.connect(7, 12);
        network.connect(8, 12);
        network.connect(8, 14);
        network.connect(8, 4);
        network.connect(4, 5);
        network.connect(5, 10);
        network.connect(10, 14);
    }

}
