package com.example.employees.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty

@Database(entities = [Employee::class,Specialty::class], version = 2, exportSchema = false)
abstract class DatabaseEmployee : RoomDatabase() {
    abstract fun employeeDao(): EmployeesDao

    companion object {
        private var database: DatabaseEmployee? = null
        private const val DB_NAME = "employees.db"

        fun getInstance(context: Context): DatabaseEmployee? {
            if (database == null) {
                synchronized(DatabaseEmployee::class.java) {
                    database = Room.databaseBuilder(context, DatabaseEmployee::class.java, DB_NAME)
                        .fallbackToDestructiveMigrationFrom()//миграция при изменении БД
                        .build()
                }
            }

            return database

        }

        fun destroyDB() {
            database = null
        }
    }
}