package com.arzaapps.android.garden;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    private Button mDeleteButton;
    private Button mEditPeopleButton;
    private Button mCallPeopleButton;
    private UUID peopleId;

    public static PeopleFragment newInstance(UUID peopleId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEOPLE_ID, peopleId);

        PeopleFragment fragment = new PeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        peopleId = (UUID) getArguments().getSerializable(ARG_PEOPLE_ID);
        if (peopleId != null) {
            mPeople = PeopleLab.get(getActivity()).getPeople(peopleId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_people, container, false);

        mNumberAreaEditText = v.findViewById(R.id.number_area_edit_text);
        mNumberAreaEditText.addTextChangedListener(this);

        mFioPeopleEditText = v.findViewById(R.id.fio_people_edit_text);
        mFioPeopleEditText.addTextChangedListener(this);

        mNumberTelephoneEditText = v.findViewById(R.id.number_telephone_edet_text);
        mNumberTelephoneEditText.addTextChangedListener(this);

        mHomeAddressEditText = v.findViewById(R.id.home_address_edit_text);
        mHomeAddressEditText.addTextChangedListener(this);

        mAddPeopleButton = v.findViewById(R.id.add_people_button);
        mAddPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                People people = new People();

                people.setNumberArea(mNumberAreaEditText.getText().toString());
                people.setName(mFioPeopleEditText.getText().toString());
                people.setTelephoneNumber(mNumberTelephoneEditText.getText().toString());
                people.setHomeAdress(mHomeAddressEditText.getText().toString());
                PeopleLab.get(getActivity()).addPeople(people);

                Toast toast = Toast.makeText(getActivity(),
                        "Человек добавлен!",
                        Toast.LENGTH_SHORT);
                toast.show();
                getActivity().finish();
            }
        });

        mDeleteButton = v.findViewById(R.id.delete_people_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePeopleDialog();
            }
        });

        mEditPeopleButton = v.findViewById(R.id.edit_people_button);
        mEditPeopleButton.setVisibility(View.GONE);
        mEditPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeople.setNumberArea(mNumberAreaEditText.getText().toString());
                mPeople.setName(mFioPeopleEditText.getText().toString());
                mPeople.setTelephoneNumber(mNumberTelephoneEditText.getText().toString());
                mPeople.setHomeAdress(mHomeAddressEditText.getText().toString());
                PeopleLab.get(getActivity()).updatePeople(mPeople);

                Toast toast = Toast.makeText(getActivity(),
                        "Изменения сохранены!",
                        Toast.LENGTH_SHORT);
                toast.show();
                getActivity().finish();
            }
        });

        mCallPeopleButton = v.findViewById(R.id.call_people_button);
        mCallPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumberTelephoneEditText != null) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mNumberTelephoneEditText.getText().toString())));
                }
            }
        });

        if (peopleId != null) {
            mNumberAreaEditText.setText(mPeople.getNumberArea());
            mFioPeopleEditText.setText(mPeople.getName());
            mNumberTelephoneEditText.setText(mPeople.getTelephoneNumber());
            mHomeAddressEditText.setText(mPeople.getHomeAdress());

            mAddPeopleButton.setVisibility(View.GONE);

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle("Участок №  " + mPeople.getNumberArea());
        } else {
            mDeleteButton.setVisibility(View.GONE);
            mCallPeopleButton.setVisibility(View.GONE);

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle("Добавление человека");
        }

        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (peopleId != null) {
            mEditPeopleButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void showDeletePeopleDialog() {
            AlertDialog.Builder deletePeopleAlertDialogBuilder = new AlertDialog.Builder(getActivity());
            deletePeopleAlertDialogBuilder.setTitle("Удалить человека?");  // заголовок
            deletePeopleAlertDialogBuilder.setMessage("Вы действительно хотите удалить " + mPeople.getName() + " из базы?");
            deletePeopleAlertDialogBuilder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {

                    PeopleLab.get(getActivity()).deletePeople(mPeople);

                    Toast toast = Toast.makeText(getActivity(),
                            "Человек удален!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    getActivity().finish();
                }
            });
            deletePeopleAlertDialogBuilder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {

                }
            });
            deletePeopleAlertDialogBuilder.show();
    }
}
