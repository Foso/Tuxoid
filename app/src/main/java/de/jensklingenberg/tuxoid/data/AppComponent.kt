package de.jensklingenberg.tuxoid.data

import dagger.Component
import de.jensklingenberg.tuxoid.ui.MainFragment
import de.jensklingenberg.tuxoid.ui.MainPresenter
import javax.inject.Singleton

@Component(modules = [(AppModule::class)])
@Singleton
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(mainFragment: MainPresenter)


}
