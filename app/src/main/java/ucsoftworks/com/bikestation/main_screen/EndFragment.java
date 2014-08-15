package ucsoftworks.com.bikestation.main_screen;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ucsoftworks.com.bikestation.R;
import ucsoftworks.com.bikestation.application.BikeApp;
import ucsoftworks.com.bikestation.events.FinishTimeOutEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class EndFragment extends Fragment {

    private static final String ARG_END_RENT_TIME = "username";
    private static final String ARG_RENT_TIME = "rentTime";
    private static final String ARG_COST = "totalCost";

    private static final int FIRST_FORM = 0, SECOND_FORM = 1, THIRD_FORM = 2;
    private static final String[] HOUR_FORMS = {"час", "часа", "часов"}, MINUTE_FORMS = {"минута", "минуты", "минут"};

    private Time endRentTime;
    private Time rentTime;
    private Float totalCost;

    @InjectView(R.id.time_label)
    TextView timeField;
    @InjectView(R.id.cost_label)
    TextView costField;

    @Inject
    Bus bus;

    public EndFragment() {
        // Required empty public constructor
    }

    public static EndFragment newInstance(long rentTime, long endRentTime, float cost) {
        EndFragment fragment = new EndFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_END_RENT_TIME, endRentTime);
        args.putLong(ARG_RENT_TIME, rentTime);
        args.putFloat(ARG_COST, cost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rentTime = new Time();
        endRentTime = new Time();
        initFromArguments(savedInstanceState == null ? getArguments() : savedInstanceState);
    }

    private void initFromArguments(Bundle bundle) {
        Log.d(MainActivity.TAG, "Received: " + bundle.toString());
        endRentTime.set(bundle.getLong(ARG_END_RENT_TIME));
        rentTime.set(bundle.getLong(ARG_RENT_TIME));
        totalCost = bundle.getFloat(ARG_COST);
    }

    @Override
    public void onSaveInstanceState(android.os.Bundle outState) {
        outState.putFloat(ARG_COST, totalCost);
        outState.putLong(ARG_RENT_TIME, rentTime.toMillis(false));
        outState.putLong(ARG_END_RENT_TIME, endRentTime.toMillis(false));
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        BikeApp bikeApp = (BikeApp) getActivity().getApplication();
        bikeApp.inject(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bus.post(new FinishTimeOutEvent());
            }
        }, 300_000);

        View view = inflater.inflate(R.layout.fragment_end, container, false);
        ButterKnife.inject(this, view);

        int hours, minutes, seconds;
        long difference = endRentTime.toMillis(false) - rentTime.toMillis(false);
        hours = (int) (difference / (1000 * 60 * 60));
        minutes = (int) (difference - (1000 * 60 * 60 * hours)) / (1000 * 60);


        costField.setText(String.format("%.0f %s", totalCost, "р."));
        timeField.setText(String.format("%d %s %d %s", hours, HOUR_FORMS[getRussianForm(hours)], minutes, MINUTE_FORMS[getRussianForm(minutes)]));

        return view;
    }

    private int getRussianForm(int num) {
        int numBy100 = num % 100;
        int numBy10 = num % 10;
        if ((numBy100 > 4) && (numBy100 < 21))
            return THIRD_FORM;
        switch (numBy10) {
            case 1:
                return FIRST_FORM;
            case 2:
                return SECOND_FORM;
            case 3:
                return SECOND_FORM;
            case 4:
                return SECOND_FORM;
            default:
                return THIRD_FORM;
        }
    }

    @OnClick(R.id.root_layout)
    public void backToHomeScreen() {
        bus.post(new FinishTimeOutEvent());
    }
}
