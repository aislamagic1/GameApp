package ba.etf.rma23.projekat

import android.content.pm.ActivityInfo
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.projekat.GameData.Companion.getAll
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            Assert.assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            var rv: RecyclerView = view as RecyclerView
            ViewMatchers.assertThat(
                "GetItemCount RecyclerView broj elementa: ",
                rv.adapter?.itemCount,
                CoreMatchers.`is`(n)
            )
        }
    }

    @get:Rule
    var homeRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    /**
     * Testiranje Navigacije
     * -----------
     * Ovim testom se provjerava ispravnost BottomNavigationView-a, da li su fragmenti dobro
     * raspoređeni koristeći navigacijusku komponentu i da li je navigacija onemogućena u
     * slučajevima kada korisnik nije ranije kliknuo na neku od igara iz RecyclerView-a.
     * -----------
     * Ovo testiranje se vrši kako bi provjerili da li i dalje ispravno funkcioniše navigacija kroz
     * aplikaciju i nakon dodavanja BottomNavigationView-a.
     * -----------
     * Test će izvoditi klik na stavke BottomNavigationView-a, gameList RecyclerView-a, nakon čega
     * će se vršiti provjera da li je otvoren odgovarajući fragment
     */

    @Test
    fun navigationTest() {
        //Da li je BottomNavigationView disabled
        onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        //Otvaranje detalja o igri preko RecyclerView-a
        var prvaIgra = getAll().get(0)
        onView(withId(R.id.game_list)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    hasDescendant(withText(prvaIgra.title)),
                    hasDescendant(withText(prvaIgra.releaseDate)),
                    hasDescendant(withText(prvaIgra.rating.toString()))

                ), click()
            )
        )
        onView(withText(prvaIgra.description))
            .check(matches(isDisplayed()))
        onView(CoreMatchers.instanceOf(RecyclerView::class.java)).check(hasItemCount(prvaIgra.userImpressions.size))

        //Vraćanje na home fragment
        onView(withId(R.id.homeItem)).perform(click())
        //Provjera da li se nalazimo na home fragment
        onView(withId(R.id.game_list)).check((hasItemCount(getAll().size)))

        //Provjera details komponente
        onView(withId(R.id.gameDetailsItem)).perform(click())
        //Provjera da li je ispravna igra otvorena
        onView(withId(R.id.item_title_textview)).check(matches(withText(prvaIgra.title)))
    }

    /**
     * Testiranje promjene orijentacije
     * -----------
     * Ovim testom se provjerava da li se aplikacija ispravno ponaša kada se nalazi u landscape orijentaciji,
     * tj. da li su oba fragmenta prikazan u isto vrijeme. Također će se testirati i naizmjenična promjena iz
     * portrait orijentacije u landscape orijentaciju.
     * -----------
     * Ovo testiranje se vrši da bi se provjerilo da li su održane sve mogućnosti aplikacije u landscape
     * orijentaciji.
     * -----------
     * U testu će se ručno naizmjenično mijenjati orijentacija ekrana, provjeravati da li su ispravno
     * raspoređeni fragmenti, te da li su otvoreni detalji prve igre pri promjeni u landscape orijentaciju
     */

    @Test
    fun orientationTest() {
        // Promjena u landscape orijentaciju
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        var prvaIgra = getAll().get(0)
        // Provjera da li je otvoren HomeFragment
        onView(withId(R.id.nav_host_fragment_left)).check(matches(isDisplayed()))

        // Provjera da li je otvoren GameDetailsFragment
        onView(withId(R.id.nav_host_fragment_right)).check(matches(isDisplayed()))
        // Provjera da li je otvorena prva igra
        onView(withId(R.id.description_textview)).check(matches(withText(prvaIgra.description)))

        // Promjena u portrait orijentaciju
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Provjera da li je otvoren HomeFragment
        onView(withId(R.id.search_query_edittext)).check(matches(isDisplayed()))
    }

    /**
     * Testiranje RecyclerView-a
     * -----------
     * Ovim testom se provjerava funkcionalnost RecyclerView-a u portrait orijentaciji i
     * u landscape orijentaciji. Također će se vršiti provjera rasporeda elemenata u
     * GameDetails fragmentu.
     * -----------
     * Ovaj test provjerava da li RecyclerView ispravno radi u sa navigacionom komponentom,
     * i da li ispravno radi u landscape orijentaciji.
     * -----------
     * U testu će se klikom na element RecyclerView-a otvoriti detalj igre, vršit će se provjera
     * da li je ispravno igra otvorena preko naslova igre, te će se porvjeriti da li su svi elementi
     * ispravno raspoređeni.U landscape orijentaciju će se analogno vršiti provjera RecyclerView-a.
     */

    @Test
    fun testLayout() {
        //Otvaranje prve detalja prve igre
        var prvaIgra = getAll().get(0)
        onView(withId(R.id.game_list)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    hasDescendant(withText(prvaIgra.title)),
                    hasDescendant(withText(prvaIgra.releaseDate)),
                    hasDescendant(withText(prvaIgra.rating.toString()))

                ), click()
            )
        )
        //Provjera da li su otvorevi ispravni detalji prve igre
        onView(withId(R.id.item_title_textview)).check(matches(withText(prvaIgra.title)))

        //Provjera rasporeda elemenata
        onView(withId(R.id.card_view)).check(matches(isDisplayed()))
        onView(withId(R.id.card_view)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.horizontal_scroll_view)).check(matches(isDisplayed()))
        onView(withId(R.id.horizontal_scroll_view)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.esrb_rating_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.esrb_rating_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.developer_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.developer_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.publisher_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.publisher_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.description_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.description_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.platform_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.platform_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date_textview)).check(matches(isDisplayed()))

        onView(withId(R.id.card_view)).check((isCompletelyAbove(withId(R.id.item_title_textview))))
        onView(withId(R.id.item_title_textview)).check((isCompletelyAbove(withId(R.id.horizontal_scroll_view))))


        onView(withId(R.id.cover_imageview)).check(matches(isDescendantOfA(withId(R.id.card_view))))
        onView(withId(R.id.genre_textview)).check(matches(isDescendantOfA(withId(R.id.horizontal_scroll_view))))
        onView(withId(R.id.esrb_rating_textview)).check(matches(isDescendantOfA(withId(R.id.horizontal_scroll_view))))
        onView(withId(R.id.developer_textview)).check(matches(isDescendantOfA(withId(R.id.horizontal_scroll_view))))
        onView(withId(R.id.publisher_textview)).check(matches(isDescendantOfA(withId(R.id.horizontal_scroll_view))))
        onView(withId(R.id.genre_textview)).check((isCompletelyLeftOf(withId(R.id.esrb_rating_textview))))
        onView(withId(R.id.esrb_rating_textview)).check((isCompletelyLeftOf(withId(R.id.developer_textview))))
        onView(withId(R.id.developer_textview)).check((isCompletelyLeftOf(withId(R.id.publisher_textview))))

        onView(withId(R.id.description_textview)).check((isCompletelyAbove(withId(R.id.platform_textview))))
        onView(withId(R.id.platform_textview)).check((isCompletelyAbove(withId(R.id.release_date_textview))))

        onView(withId(R.id.release_date_textview)).check((isCompletelyAbove(withId(R.id.user_list))))


        // Promjena u landscape orijentaciju
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val drugaIgra = getAll().get(1)
        onView(withId(R.id.game_list)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    hasDescendant(withText(drugaIgra.title)),
                    hasDescendant(withText(drugaIgra.releaseDate)),
                    hasDescendant(withText(drugaIgra.rating.toString()))

                ), click()
            )
        )
        //Provjera da li su otvorevi ispravni detalji druge igre u landscape orijentaciji
        onView(withId(R.id.description_textview)).check(matches(withText(drugaIgra.description)))
    }


}