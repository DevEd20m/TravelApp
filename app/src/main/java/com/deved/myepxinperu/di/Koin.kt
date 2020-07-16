package com.deved.myepxinperu.di

import android.app.Application
import com.deved.data.repository.*
import com.deved.data.source.*
import com.deved.interactors.*
import com.deved.myepxinperu.data.AndroidPermissionsChecker
import com.deved.myepxinperu.data.dataBase.PlaceDataBase
import com.deved.myepxinperu.data.dataBase.RoomUserDataSource
import com.deved.myepxinperu.data.device.PlayServicesLocationDataSource
import com.deved.myepxinperu.data.server.FirebasePictureDataSource
import com.deved.myepxinperu.data.server.FirebasePlacesDataSource
import com.deved.myepxinperu.data.server.FirebaseSecurityDataSource
import com.deved.myepxinperu.data.storage.SharedPreferenceDataSource
import com.deved.myepxinperu.ui.detail.DetailFragment
import com.deved.myepxinperu.ui.detail.DetailViewModel
import com.deved.myepxinperu.ui.home.HomeFragment
import com.deved.myepxinperu.ui.home.HomeViewModel
import com.deved.myepxinperu.ui.security.logIn.LoginActivity
import com.deved.myepxinperu.ui.security.logIn.LoginViewModel
import com.deved.myepxinperu.ui.security.register.RegisterActivity
import com.deved.myepxinperu.ui.security.register.RegisterViewModel
import com.deved.myepxinperu.ui.share.ShareFragment
import com.deved.myepxinperu.ui.share.ShareViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        fragmentFactory()
        modules(listOf(appModule, dataModule, scopesModule, workers))
    }
}

private val appModule = module {
    single { PlaceDataBase.getDatabaseInstance(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single { FirebaseStorage.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    factory<DataBaseDataSource> { RoomUserDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PictureDataSource> { FirebasePictureDataSource(get()) }
    factory<PlaceDataSource> { FirebasePlacesDataSource(get()) }
    factory<PreferenceDataSource> { SharedPreferenceDataSource(get()) }
    factory<SecurityDataSource> { FirebaseSecurityDataSource(get(), get()) }
    factory<PermissionsChecker> { AndroidPermissionsChecker(get()) }
}

private val dataModule = module {
    factory<LocationRepository> { LocationRepositoryImpl(get(), get()) }
    factory<PictureRepository> { PictureRepositoryImpl(get()) }
    factory<PlacesRepository> { PlacesRepositoryImpl(get()) }
    factory<SecurityRepository> { SecurityRepositoryImpl(get()) }
}

private val scopesModule = module {
    scope(named<LoginActivity>()) {
        viewModel { LoginViewModel(get()) }
        scoped { LogIn(get()) }
    }

    scope(named<RegisterActivity>()) {
        viewModel { RegisterViewModel(get()) }
        scoped { RegisterUser(get()) }
    }

    scope(named<HomeFragment>()) {
        viewModel { HomeViewModel(get(), get()) }
        scoped { GetAllDepartment(get()) }
        scoped { AndroidPermissionsChecker(androidApplication()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { DetailViewModel(get(), get()) }
        scoped { GetDetailPlace(get()) }
        scoped { GetDetailUserPosted(get()) }
    }

    scope(named<ShareFragment>()) {
        viewModel { ShareViewModel(get(), get(), get()) }
        scoped { RegisterExp(get()) }
        scoped { UploadPicture(get()) }
        scoped { AndroidPermissionsChecker(androidApplication()) }
    }
}

private val workers = module {
    single { SaveLocation(get()) }
}