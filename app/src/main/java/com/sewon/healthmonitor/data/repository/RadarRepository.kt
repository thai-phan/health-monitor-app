package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.source.local.dao.RadarDao
import com.sewon.healthmonitor.data.source.local.entity.Radar
import com.sewon.healthmonitor.data.repository.repointerface.IRadarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RadarRepository @Inject constructor(
    private val RadarDao: RadarDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : IRadarRepository {



    override fun getTopper(): Flow<List<Radar>> {
        return RadarDao.getAllTopper()
    }


    override fun getCountTopper(): Flow<Int> {
        return RadarDao.countTopper()
    }
//     Count number record



    override suspend fun createTopper(radar: Radar): String {
        RadarDao.upsert(radar)
        return "Done"
    }

}