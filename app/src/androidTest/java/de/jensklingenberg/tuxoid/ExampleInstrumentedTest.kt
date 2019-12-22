package de.jensklingenberg.tuxoid

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.jensklingenberg.tuxoid.model.element.ElementType
import de.jensklingenberg.tuxoid.utils.LevelHelper
import de.jensklingenberg.tuxoid.utils.LoadGame
import junit.framework.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <getMapMovingWood href="http://d.android.com/tools/testing">Testing documentation</getMapMovingWood>
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

  //  var levelE = arrayOf(arrayOf(intArrayOf(48, 0, 0)))


    @Before
    fun prepare(){
        val appContext = InstrumentationRegistry.getTargetContext()


    }

    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("de.jensklingenberg.tuxoid", appContext.packageName)
    }

    @Test
    @Throws(Exception::class)
    fun moveToBackground() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        // assertEquals("de.jensklingenberg.tuxoid", appContext.getPackageName());

val ee = LoadGame(appContext)
        ee.createLevel("t1")
        val helper= LevelHelper()

        helper.setLevel(ee.levelE,ee.levelEo)

        helper.screenTouched(0,1)

val tye= helper.level!![0][0][1].type
        Assert.assertEquals(ElementType.PLAYER,tye )

        //Assert.assertTrue(helper.level[0][0][1].elementGroup==Background)



    }


    @Test
    @Throws(Exception::class)
    fun moveCrateBlueToBackground() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        // assertEquals("de.jensklingenberg.tuxoid", appContext.getPackageName());

        val ee = LoadGame(appContext)
        ee.createLevel("t2")
        val helper= LevelHelper()

        helper.setLevel(ee.levelE,ee.levelEo)

        helper.screenTouched(0,1)

        val tye= helper.level!![0][0][2].type
        Assert.assertEquals(ElementType.CRATE_BLUE,tye )

    }

    @Test
    @Throws(Exception::class)
    fun moveToFish() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        val ee = LoadGame(appContext)
        ee.createLevel("t3")
        val helper= LevelHelper()

        helper.setLevel(ee.levelE,ee.levelEo)

        helper.screenTouched(0,1)

        val tye= helper.level!![0][0][1].type
        Assert.assertEquals(ElementType.PLAYER,tye )

    }

    @Test
    @Throws(Exception::class)
    fun whenMoveToKeyDoorShouldOpen() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        val ee = LoadGame(appContext)
        ee.createLevel("t4")
        val helper= LevelHelper()

        helper.setLevel(ee.levelE,ee.levelEo)

        helper.screenTouched(0,1)

        val tye= helper.level!![0][0][2].type
        Assert.assertEquals(ElementType.BACKGROUND,tye )

    }


    @Test
    @Throws(Exception::class)
    fun whenMoveToTeleInComeOutNextToTeleportOut() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        val ee = LoadGame(appContext)
        ee.createLevel("t5")
        val helper= LevelHelper()

        helper.setLevel(ee.levelE,ee.levelEo)

        helper.screenTouched(0,1)

        val tye= helper.level!![0][0][3].type
        Assert.assertEquals(ElementType.PLAYER,tye )

    }
}
