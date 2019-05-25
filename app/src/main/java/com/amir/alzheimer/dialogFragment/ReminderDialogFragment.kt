package com.amir.alzheimer.dialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseDialogFragment
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.reminder_dialog_fragment.*
import kotlinx.android.synthetic.main.reminder_dialog_fragment.view.*

class ReminderDialogFragment : BaseDialogFragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private var chosenDate: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reminder_dialog_fragment, container, true)

        val adapter = ArrayAdapter(context!!, android.R.layout.simple_dropdown_item_1line, COUNTRIES)
        view.reminder_dialog_spinner.setAdapter(adapter)

        view.reminder_dialog_fragment_submit.setOnClickListener(this)

        return view
    }

    private fun pickADate() {
        val persianCalendar = PersianCalendar()
        val datePickerDialog = DatePickerDialog.newInstance(
                this,
                persianCalendar.persianYear,
                persianCalendar.persianMonth,
                persianCalendar.persianDay
        )

        //        datePickerDialog.setMaxDate(new PersianCalendar(Calendar.getInstance().getTimeInMillis() + ONE_MONTH * 2));
        datePickerDialog.minDate = persianCalendar
        datePickerDialog.show(activity!!.fragmentManager, "Datepickerdialog")
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        chosenDate = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " "

        val persianCalendar = PersianCalendar()

        val timePickerDialog = TimePickerDialog.newInstance(
                this,
                persianCalendar.time.hours,
                persianCalendar.time.minutes,
                true
        )


        timePickerDialog.show(activity!!.fragmentManager, "Datepickerdialog")
    }

    override fun onTimeSet(view: RadialPickerLayout, hourOfDay: Int, minute: Int) {
        val chosenDateComplete = "$chosenDate$hourOfDay:$minute"
        dismiss()
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            //            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    override fun onClick(v: View) {
        when (v) {
            reminder_dialog_fragment_submit -> {
                pickADate()

            }
        }
    }

    companion object {

        private val COUNTRIES = arrayOf("Once", "Weekly")
    }
}
