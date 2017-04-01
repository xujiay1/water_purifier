package xebia.ismail.water_purifier.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import xebia.ismail.water_purifier.R;

/**
 * Created by Admin on 5/25/2016.
 */
public class FilterManagerFragment extends Fragment {

    EditText radius, height,lukis;
    TextView tv_vol, tv_luas, tv_kel;
    Button hasil;
    Spinner spinnerService;
    Spinner spinnerBrand;
    Spinner spinnerTime;
    Spinner spinnerChangeTime;

    double vol, luas, luasper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filtermanager, container, false);


        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinnerService = (Spinner) getActivity().findViewById(R.id.spinnerService);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.service, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerService.setAdapter(adapter);

        spinnerBrand = (Spinner) getActivity().findViewById(R.id.spinnerBrand);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(getActivity(),
                R.array.brand, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerBrand.setAdapter(adapterBrand);

        spinnerTime = (Spinner) getActivity().findViewById(R.id.spinnerTIme);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(getActivity(),
                R.array.time, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerTime.setAdapter(adapterTime);

        spinnerChangeTime = (Spinner) getActivity().findViewById(R.id.spinnerChangeTIme);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterChangeTime = ArrayAdapter.createFromResource(getActivity(),
                R.array.changetime, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterChangeTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerChangeTime.setAdapter(adapterChangeTime);
    }
}
