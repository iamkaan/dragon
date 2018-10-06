package com.iamkaan.dragon.home

import com.iamkaan.dragon.DataManager
import com.iamkaan.dragon.Navigator
import com.iamkaan.dragon.UserManager

class HomePresenter(private val displayer: HomeDisplayer,
                    private val dataManager: DataManager,
                    private val userManager: UserManager,
                    private val navigator: Navigator) {

    fun startPresenting() {
        displayer.displayEmpty(HomeViewModel(onFabClick = ::onFabClick))

        dataManager.getDragons {
            displayer.display(HomeViewModel(it, ::onFabClick))
        }
    }

    fun onSignOutClick() {
        userManager.signOut { navigator.toSignInScreen() }
    }

    fun onFilterClick() {
        dataManager.getFilteredDragons {
            displayer.display(HomeViewModel(it, ::onFabClick))
        }
    }

    private fun onFabClick() {
        navigator.toNewPost()
    }
}
