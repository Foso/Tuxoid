package jensklingenberg.de.tuxoid

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import jensklingenberg.de.tuxoid.model.Element.Destination.Background
import jensklingenberg.de.tuxoid.model.Element.ElementGroup
import jensklingenberg.de.tuxoid.model.Element.ElementType
import jensklingenberg.de.tuxoid.utils.LoadGame
import junit.framework.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Before

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

        assertEquals("jensklingenberg.de.tuxoid", appContext.packageName)
    }

    @Test
    @Throws(Exception::class)
    fun moveToBackground() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        // assertEquals("jensklingenberg.de.tuxoid", appContext.getPackageName());


        val tt = LevelTest.tst()
        val helper= LevelHelper()

        helper.setLevel(tt,tt)

        helper.screenTouched(0,1)


        Assert.assertEquals(ElementType.BACKGROUND, helper.level!![0][0][1].type)

        //Assert.assertTrue(helper.level[0][0][1].elementGroup==Background)



    }
}