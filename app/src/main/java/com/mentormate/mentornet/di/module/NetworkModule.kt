package com.mentormate.mentornet.di.module

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.gson.Gson
import com.mentormate.mentornet.MentorNetApp
import com.mentormate.mentornet.adapter.network.TokenInterceptor
import com.mentormate.mentornet.adapter.retrofit.authentication.AuthenticationNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.authentication.AuthorizationService
import com.mentormate.mentornet.adapter.retrofit.employee.EmployeeNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.employee.EmployeeService
import com.mentormate.mentornet.adapter.retrofit.employee.GeneralInformationService
import com.mentormate.mentornet.adapter.retrofit.kudos.KudoNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.kudos.KudoService
import com.mentormate.mentornet.adapter.retrofit.rank.RankService
import com.mentormate.mentornet.adapter.retrofit.store.StoreNetworkBoundRepository
import com.mentormate.mentornet.adapter.retrofit.store.StoreService
import com.mentormate.mentornet.adapter.retrofit.trainings.*
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.data.employee.CompleteEmployeeDao
import com.mentormate.mentornet.data.employee.EmployeeDao
import com.mentormate.mentornet.data.employee.GeneralInformationDao
import com.mentormate.mentornet.data.employee.GeneralInformationNetworkBoundRepository
import com.mentormate.mentornet.data.kudos.KudoDao
import com.mentormate.mentornet.adapter.retrofit.leaderboard.FullRankListNetworkBoundRepository
import com.mentormate.mentornet.data.leaderboard.LeaderBoardDao
import com.mentormate.mentornet.adapter.retrofit.leaderboard.RankListNetworkBoundRepository
import com.mentormate.mentornet.data.store.StoreItemsDao
import com.mentormate.mentornet.data.trainings.АllCourseDao
import com.mentormate.mentornet.data.trainings.TrainingDao
import com.mentormate.mentornet.utilities.BASE_API_URL
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by vasil.mitov@mentormate.com on 06/03/19.
 */

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .callbackExecutor(Executors.newCachedThreadPool())
            .build()
    }


    @Provides
    @Singleton
    fun provideHttpCache(application: MentorNetApp): Cache {
        return Cache(application.cacheDir, 10 * 1024 * 1024)
    }

    @Provides
    fun provideTokenInterceptor(authDao: AuthCredentialsDao,context: Context,googleSignInClient: GoogleSignInClient): TokenInterceptor {
        return TokenInterceptor(authDao,googleSignInClient,context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(tokenInterceptor)
            .addInterceptor(tokenInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    fun provideEmployeeService(retrofit: Retrofit): EmployeeService {
        return retrofit.create(EmployeeService::class.java)
    }

    @Provides
    fun provideKudoService(retrofit: Retrofit): KudoService {
        return retrofit.create(KudoService::class.java)
    }



    @Provides
    fun provideStoreItemService(retrofit: Retrofit): StoreService {
        return retrofit.create(StoreService::class.java)
    }

    @Provides
    fun provideTrainingService(retrofit: Retrofit): TrainingService {
        return retrofit.create(TrainingService::class.java)
    }

    @Provides
    fun provideGeneralInformationService(retrofit: Retrofit): GeneralInformationService {
        return retrofit.create(GeneralInformationService::class.java)
    }

    @Provides
    fun provideAuthorizationService(retrofit: Retrofit): AuthorizationService {
        return retrofit.create(AuthorizationService::class.java)
    }



    @Provides
    fun provideAuthBoundRepo(
        authDao: AuthCredentialsDao,
        authService: AuthorizationService
    ): AuthenticationNetworkBoundRepository {
        return AuthenticationNetworkBoundRepository(authDao, authService)
    }

    @Provides
    fun provideNetworkRepository(
        employeeDao: EmployeeDao,
        employeeService: EmployeeService

    ): EmployeeNetworkBoundRepository {
        return EmployeeNetworkBoundRepository(
            employeeDao,
            employeeService
        )
    }

    @Provides
    fun provideStoreNetworkRepository(
        storeItemsDao: StoreItemsDao,
        storeService: StoreService

    ): StoreNetworkBoundRepository {
        return StoreNetworkBoundRepository(storeItemsDao, storeService)
    }

    @Provides
    fun provideTrainingNetworkRepository(
        trainingDao: TrainingDao,
        trainingService: TrainingService

    ): TrainingNetworkBoundRepository {
        return TrainingNetworkBoundRepository(
            trainingDao,
            trainingService
        )
    }

    @Provides
    fun provideKudoNetworkRepository(
        kudoDao: KudoDao,
        kudoService: KudoService

    ): KudoNetworkBoundRepository {
        return KudoNetworkBoundRepository(kudoDao, kudoService)
    }


    @Provides
    fun provideGeneralInformationNetworkBoundRepository(
        generalInformationDao: GeneralInformationDao,
        generalInformationService: GeneralInformationService
    ): GeneralInformationNetworkBoundRepository {
        return GeneralInformationNetworkBoundRepository(
            generalInformationDao, generalInformationService
        )
    }

    @Provides
    fun provideCoursesNetworkBoundRepository(
        allCourseDao: АllCourseDao,
        trainingService: TrainingService

    ): CoursesNetworkBoundRepository {
        return CoursesNetworkBoundRepository(
            allCourseDao,
            trainingService
        )
    }

    @Provides
    fun provideRankingService(retrofit: Retrofit): RankService {
        return retrofit.create(RankService::class.java)
    }

    @Provides
    fun provideFullRankListNetworkBoundRepositoru(
        leaderBoardDao: LeaderBoardDao,
        rankService: RankService,
        completeEmployeeDao: CompleteEmployeeDao,
        context: Context
    ): FullRankListNetworkBoundRepository {
        return FullRankListNetworkBoundRepository(
            leaderBoardDao,
            rankService,
            completeEmployeeDao,
            context
        )
    }

    @Provides
    fun provideRankListNetworkBoundRepository(

        rankService: RankService,
        leaderBoardDao: LeaderBoardDao,
        context: Context
    ): RankListNetworkBoundRepository {
        return RankListNetworkBoundRepository(
            rankService,
            leaderBoardDao,
            context
        )
    }

}