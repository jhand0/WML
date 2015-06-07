package edu.washington.jhand1.wheresmylimbs;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentRoom extends Fragment {

    public String roomTitle;
    public String roomDescription;
    public String roomUpdate;

    public static FragmentRoom newInstance(String roomTitle, String roomDescription,
                                           String roomUpdate) {
        FragmentRoom fragment = new FragmentRoom();
        fragment.roomTitle = roomTitle;
        fragment.roomDescription = roomDescription;
        fragment.roomUpdate = roomUpdate;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        ((TextView) view.findViewById(R.id.txtRoomTitle)).setText(roomTitle);
        ((TextView) view.findViewById(R.id.txtRoomDescription)).setText(roomDescription);
        if (roomUpdate != null) {
            view.findViewById(R.id.txtRoomUpdate).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.txtRoomUpdate)).setText(roomUpdate);
        } else {
            view.findViewById(R.id.txtRoomUpdate).setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
