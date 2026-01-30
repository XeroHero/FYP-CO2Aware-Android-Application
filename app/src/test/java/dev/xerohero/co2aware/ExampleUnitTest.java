import org.junit.Test;

import static dev.xerohero.co2aware.SplashScreen.about;
import static dev.xerohero.co2aware.SplashScreen.friendList;
import static dev.xerohero.co2aware.SplashScreen.greenTips;
import static dev.xerohero.co2aware.SplashScreen.journeyPlan;

import static org.junit.Assert.assertNotNull;

public class ExampleUnitTest {
    @Test
    public void testGreenTipsNotNull() {
        assertNotNull(greenTips);
    }

    @Test
    public void testFriendsNotNull() {
        assertNotNull(friendList);
    }

    @Test
    public void testAboutNotNull() {
        assertNotNull(about);
    }

    @Test
    public void testJourneyPlannerNotNull() {
        assertNotNull(journeyPlan);
    }
}
