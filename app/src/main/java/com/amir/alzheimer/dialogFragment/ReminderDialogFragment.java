package com.amir.alzheimer.dialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseDialogFragment;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.weiwangcn.betterspinner.library.BetterSpinner;

public class ReminderDialogFragment extends BaseDialogFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private String chosenDate;

    private static final String[] COUNTRIES = new String[]{
            "Once", "Weekly"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reminder_dialog_fragment, container, true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        BetterSpinner spinner = (BetterSpinner) view.findViewById(R.id.reminder_dialog_spinner);
        spinner.setAdapter(adapter);

        view.findViewById(R.id.reminder_dialog_fragment_submit).setOnClickListener(this);

        return view;
    }

    private void pickADate() {
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );

//        datePickerDialog.setMaxDate(new PersianCalendar(Calendar.getInstance().getTimeInMillis() + ONE_MONTH * 2));
        datePickerDialog.setMinDate(persianCalendar);
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        chosenDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " ";

        PersianCalendar persianCalendar = new PersianCalendar();

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                persianCalendar.getTime().getHours(),
                persianCalendar.getTime().getMinutes(),
                true
        );


        timePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String chosenDateComplete = chosenDate + hourOfDay + ":" + minute;
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.reminder_dialog_fragment_submit) {
            pickADate();
        }
    }
}
