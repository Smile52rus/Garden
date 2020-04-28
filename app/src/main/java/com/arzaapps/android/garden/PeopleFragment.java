package com.arzaapps.android.garden;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class PeopleFragment extends Fragment implements TextWatcher {

    private static final String ARG_PEOPLE_ID = "people_id";
    private People mPeople;
    private EditText mNumberAreaEditText;
    private EditText mFioPeopleEditText;
    private EditText mNumberTelephoneEditText;
    private EditText mHomeAddressEditText;
    private Button mAddPeopleButton;

    public static PeopleFragment newInstance(UUID peopleId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEOPLE_ID, peopleId);

        PeopleFragment fragment = new PeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        UUID peopleId = (UUID) getArguments().getSerializable(ARG_PEOPLE_ID);
        mPeople = PeopleLab.get(getActivity()).getPeople(peopleId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_people, container, false);

        mNumberAreaEditText = v.findViewById(R.id.number_area_edit_text);
        mNumberAreaEditText.setText(mPeople.getNumberArea());
        mNumberAreaEditText.addTextChangedListener(this);

        mFioPeopleEditText = v.findViewById(R.id.fio_people_edit_text);
        mFioPeopleEditText.setText(mPeople.getName());
        mFioPeopleEditText.addTextChangedListener(this);

        mNumberTelephoneEditText = v.findViewById(R.id.number_telephone_edet_text);
        mNumberTelephoneEditText.setText(mPeople.getTelephoneNumber());
        mNumberTelephoneEditText.addTextChangedListener(this);

        mHomeAddressEditText = v.findViewById(R.id.home_address_edit_text);
        mHomeAddressEditText.setText(mPeople.getHomeAdress());
        mHomeAddressEditText.addTextChangedListener(this);

        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mPeople.setNumberArea(mNumberAreaEditText.getText().toString());
        mPeople.setName(mFioPeopleEditText.getText().toString());
        mPeople.setTelephoneNumber(mNumberTelephoneEditText.getText().toString());
        mPeople.setHomeAdress(mHomeAddressEditText.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
