package ucsoftworks.com.bikestation.main_screen;


import android.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ucsoftworks.com.bikestation.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private static final String ARG_RENT_TIME = "rentTime";
    private static final String ARG_COST = "cost";

    private String username;
    private Time rentTime;
    private Float cost;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter username.
     * @param rentTime Parameter rentTime.
     * @param cost     Parameter cost.
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(String username, Time rentTime, float cost) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putLong(ARG_RENT_TIME, rentTime.toMillis(false));
        args.putFloat(ARG_COST, cost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            rentTime = new Time();
            rentTime.set(getArguments().getLong(ARG_RENT_TIME));
            cost = getArguments().getFloat(ARG_COST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


}
