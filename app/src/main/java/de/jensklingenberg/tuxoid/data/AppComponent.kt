package de.jensklingenberg.tuxoid.data

import dagger.Component
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.ui.common.GView
import de.jensklingenberg.tuxoid.ui.GameFragment
import de.jensklingenberg.tuxoid.ui.MainPresenter
import javax.inject.Singleton

@Component(modules = [(AppModule::class)])
@Singleton
interface AppComponent {
    fun inject(gameFragment: GameFragment)
    fun inject(mainFragment: MainPresenter)
    fun inject(element: Element)
    fun inject(gView: GView)


}
