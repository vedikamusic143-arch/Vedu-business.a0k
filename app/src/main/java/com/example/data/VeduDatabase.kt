package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AuditLogEntity::class, ApprovalRequestEntity::class, ConsumerVaultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VeduDatabase : RoomDatabase() {
    abstract fun veduDao(): VeduDao

    companion object {
        @Volatile
        private var INSTANCE: VeduDatabase? = null

        fun getDatabase(context: Context): VeduDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VeduDatabase::class.java,
                    "vedu_platform_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
