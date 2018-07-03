package com.amir.alzheimer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseFragment;
import com.amir.alzheimer.dialogFragment.ReminderDialogFragment;
import com.amir.alzheimer.infrastructure.Constants;
import com.amir.alzheimer.infrastructure.Utils;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

public class SettingsFragment extends BaseFragment implements View.OnClickListener{

    private static final String REMINDER_DIALOG_FRAGMENT = "reminder_dialog_fragment";
    private View specialistButton;
    private View relativeButton;
    private View remindButton;
    private View relativeNameTextView;
    private View addHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        remindButton = view.findViewById(R.id.setting_fragment_reminder_button);
        remindButton.setOnClickListener(this);
        relativeButton = view.findViewById(R.id.setting_fragment_relatives_button);
        relativeButton.setOnClickListener(this);
        specialistButton = view.findViewById(R.id.setting_fragment_specialist_button);
        specialistButton.setOnClickListener(this);
        relativeNameTextView = view.findViewById(R.id.setting_fragment_relative_s_name);
        addHeader = view.findViewById(R.id.setting_fragment_add_new_header);
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.setting_fragment_reminder_button) {

            new ReminderDialogFragment().show(getFragmentManager(), REMINDER_DIALOG_FRAGMENT);

        } else if (itemId == R.id.setting_fragment_specialist_button) {

        } else if (itemId == R.id.setting_fragment_relatives_button) {

            if (remindButton.getVisibility() != View.GONE) {
                relativeNameTextView.setVisibility(View.VISIBLE);
                Utils.collapse(remindButton, null, null);
                Utils.collapse(specialistButton, null, null);
                Utils.collapse(addHeader, null, null);


            } else {
                GalleryConfig config = new GalleryConfig.Build()
                        .limitPickPhoto(3)
                        .singlePhoto(false)
                        .hintOfPick("this is pick hint")
                        .filterMimeTypes(new String[]{"image/jpeg"})
                        .build();

                GalleryActivity.openActivity(getActivity(), Constants.IMAGE_REQUEST_CODE, config);

            }

        }
    }



    public void relativesWasAdded() {
        relativeNameTextView.setVisibility(View.GONE);
        int height = relativeButton.getHeight();
        Utils.expand(remindButton, null, height);
        Utils.expand(specialistButton, null, height);
        Utils.expand(addHeader, null, height);

    }
}
