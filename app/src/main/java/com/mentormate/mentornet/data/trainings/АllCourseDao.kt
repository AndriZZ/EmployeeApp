package com.mentormate.mentornet.data.trainings

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface АllCourseDao {
    @Query("select * from all_courses order by id")
    fun getAllCourses(): List<EmployeeTrainingsFull>

    @Query("select * from all_courses order by id")
    fun getAllCoursesLiveData(): LiveData<List<EmployeeTrainingsFull>>

    @Query("select * from all_courses where all_courses.id = :id")
    fun getCourse(id:Int): EmployeeTrainingsFull

    @Query("select  all_courses.id,all_courses.orderNumber,all_courses.name,trainings.kingdom,trainings.name AS category_name from all_courses inner join trainings on trainings.courseIds LIKE ('%' || all_courses.id  ||',%') where trainings.id =:id GROUP BY all_courses.orderNumber,trainings.kingdom")
    fun getCouseCategory(id:Int): List<OrderedTrainingCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(courses: List<EmployeeTrainingsFull>)
}
