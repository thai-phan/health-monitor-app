package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.dao.TopperDao
import com.sewon.healthmonitor.data.entity.Topper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultTopperRepository @Inject constructor(
    private val localDataSource: TopperDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ITopperRepository {



    override fun getTopper(): Flow<List<Topper>> {
        return localDataSource.getAllTopper()
    }


    override fun getCountTopper(): Flow<Int> {
        return localDataSource.countTopper()
    }
//     Count number record



    override suspend fun createTopper(topper: Topper): String {
        localDataSource.upsert(topper)
        return "Done"
    }

}