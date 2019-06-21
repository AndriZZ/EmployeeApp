package com.mentormate.mentornet.ui.fragment


import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.trainings.EmployeeTrainingsFull
import com.mentormate.mentornet.data.trainings.Training
import com.mentormate.mentornet.data.trainings.TrainingDao
import com.mentormate.mentornet.data.trainings.АllCourseDao
import com.mentormate.mentornet.utilities.AppExecutors
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject


class TrainingsDialog : DaggerAppCompatDialogFragment() {

    @Inject
    lateinit var allCourseDao: АllCourseDao

    @Inject
    lateinit var trainingDao: TrainingDao

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var firstCompletionBar: ImageView

    lateinit var secondCompletionBar: ImageView

    lateinit var thirdCompletionBar: ImageView

    lateinit var forthCompletionBar: ImageView

    lateinit var training_name: TextView

    lateinit var circularProgressBar: CircularProgressBar

    lateinit var training_kingdom: TextView

    lateinit var textPercentage: TextView

    lateinit var trainingImage: ImageView

    lateinit var training: Training

    lateinit var imageHolder: ConstraintLayout

    lateinit var firstCourseName: TextView

    lateinit var secondCourseName: TextView

    lateinit var thirdCourseName: TextView

    lateinit var forthCourseName: TextView

    lateinit var firstCompletionText: TextView

    lateinit var secondCompletionText: TextView

    lateinit var thirdCompletionText: TextView

    lateinit var forthCompletionText: TextView

    lateinit var okayButton: Button

    lateinit var formattedPercentage: String

    private var color: Int = 0

    private var percentage: Float = 0f

    private var counter = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val view = View.inflate(context, R.layout.trainings_dialog, null)

        firstCompletionBar = view.findViewById(R.id.first_completion_bar)
        secondCompletionBar = view.findViewById(R.id.second_completion_bar)
        thirdCompletionBar = view.findViewById(R.id.third_completion_bar)
        forthCompletionBar = view.findViewById(R.id.forth_completion_bar)

        trainingImage = view.findViewById(R.id.training_image)

        textPercentage = view.findViewById(R.id.textPercentage)

        imageHolder = view.findViewById(R.id.imageHolder)

        firstCourseName = view.findViewById(R.id.first_course_name)
        secondCourseName = view.findViewById(R.id.second_course_name)
        thirdCourseName = view.findViewById(R.id.third_course_name)
        forthCourseName = view.findViewById(R.id.forth_course_name)


        firstCompletionText = view.findViewById(R.id.first_completion_text)
        secondCompletionText = view.findViewById(R.id.second_completion_text)
        thirdCompletionText = view.findViewById(R.id.third_completion_text)
        forthCompletionText = view.findViewById(R.id.forth_completion_text)

        circularProgressBar = view.findViewById(R.id.circularProgressBar)

        training_name = view.findViewById(R.id.training_name)
        training_kingdom = view.findViewById(R.id.training_kingdom)
        okayButton = view.findViewById(R.id.okay_button)

        val allCourses: MutableList<EmployeeTrainingsFull> = mutableListOf()
        val clickedEmployee = arguments?.getSerializable(EMPLOYEE_BUNDLE_KEY) as CompleteEmployee
        val id: Int = arguments?.getSerializable(context?.getString(R.string.course_category_id)) as Int
        percentage = arguments?.getSerializable(context?.getString(R.string.course_percentage)) as Float

        circularProgressBar.progress = percentage

        //pass courseIds for this category as bundle
        appExecutors.diskIO.execute {

            training = trainingDao.getTraining(id)
            training_name.text = training.name
            training_kingdom.text = training.kingdom.name
            appExecutors.mainThread.execute {
                when {
                    training.kingdom.name.toLowerCase().contains(context!!.getString(R.string.water)) -> Colorize(
                        R.color.fadedBlue,
                        R.drawable.ic_water
                    )
                    training.kingdom.name.toLowerCase().contains(context!!.getString(R.string.air)) -> Colorize(
                        R.color.fadedPurple,
                        R.drawable.ic_air
                    )
                    training.kingdom.name.toLowerCase().contains(context!!.getString(R.string.earth)) -> Colorize(
                        R.color.fadedYellow,
                        R.drawable.ic_earth
                    )
                    training.kingdom.name.toLowerCase().contains(context!!.getString(R.string.fire)) -> Colorize(
                        R.color.fadedRed,
                        R.drawable.ic_fire
                    )
                }
            }

            for (course in training.courseIds) {
                OrderCourses(allCourses, course, clickedEmployee)
            }
            allCourses.sortBy { it.orderNumber }
            firstCourseName.text = allCourses[0].name
            secondCourseName.text = allCourses[1].name
            if (training.orderNumber != 3) { //category 3 is water kingdom, which only has 2 courses currently
                thirdCourseName.text = allCourses[2].name
                forthCourseName.text = allCourses[3].name
            } else {
                thirdCompletionText.visibility = View.INVISIBLE
                forthCompletionText.visibility = View.INVISIBLE
                thirdCompletionBar.visibility = View.INVISIBLE
                forthCompletionBar.visibility = View.INVISIBLE
            }
            builder.setView(view)
        }
        okayButton.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }

    private fun OrderCourses(
        allCourses: MutableList<EmployeeTrainingsFull>,
        course: Int,
        clickedEmployee: CompleteEmployee
    ) {

        allCourses.add(allCourseDao.getCourse(course))
        appExecutors.mainThread.execute {
            for (course in allCourses) {
                for (takenCourseId in clickedEmployee.courseIds) {
                    if (course.id == takenCourseId) {
                        counter = course.orderNumber
                        when (counter) {
                            1 -> Populate(firstCompletionText, firstCompletionBar)
                            2 -> Populate(secondCompletionText, secondCompletionBar)
                            3 -> Populate(thirdCompletionText, thirdCompletionBar)
                            4 -> Populate(forthCompletionText, forthCompletionBar)
                        }
                    }
                }
            }

        }
    }

    private fun Populate(textView: TextView, imageView: ImageView) {
        textView.text = context?.getString(R.string.done)
        imageView.setBackgroundColor(ContextCompat.getColor(context!!, color))
        counter = 0
    }

    private fun Colorize(color: Int, icon: Int) {

        this.color = color

        training_kingdom.setTextColor(ContextCompat.getColor(context!!, color))
        circularProgressBar.color = (ContextCompat.getColor(context!!, color))
        formattedPercentage = percentage.toString().substring(0, percentage.toString().length - 2) + "%"
        textPercentage.text = formattedPercentage
        Glide
            .with(context!!)
            .asBitmap()
            .load(icon)
            .thumbnail()
            .into(trainingImage)

        imageHolder.setBackgroundColor(ContextCompat.getColor(context!!, color))
    }


}