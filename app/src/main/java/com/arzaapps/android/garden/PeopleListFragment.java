package com.arzaapps.android.garden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PeopleListFragment extends Fragment {
    private RecyclerView mPeopleRecyclerView;
    private PeopleAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_list, container, false);
        mPeopleRecyclerView = view.findViewById(R.id.people_recycler_view);
        mPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        PeopleLab peopleLab = PeopleLab.get(getActivity());
        List<People> peoples = peopleLab.getPeoples();
        if (mAdapter == null) {
            mAdapter = new PeopleAdapter(peoples);
            mPeopleRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PeopleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private People mPeople;

        private TextView mNumberAreaTextView;
        private TextView mNameTextView;
        private TextView mHomeAddressTextView;
        private TextView mNumberTelephoneTextView;

        @SuppressLint("CutPasteId")
        public PeopleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_people, parent, false));
            itemView.setOnClickListener(this);
            mNumberAreaTextView = itemView.findViewById(R.id.number_telephone_text_view);
            mNameTextView = itemView.findViewById(R.id.fio_people_text_view);
            mHomeAddressTextView = itemView.findViewById(R.id.home_address_text_view);
            mNumberTelephoneTextView = itemView.findViewById(R.id.number_telephone_text_view);
        }

        public void bind(People people) {
            mPeople = people;
            mNumberAreaTextView.setText(mPeople.getNumberArea());
            mNameTextView.setText(mPeople.getName());
            mHomeAddressTextView.setText(mPeople.getHomeAdress());
            mNumberTelephoneTextView.setText(mPeople.getTelephoneNumber());
        }

        @Override
        public void onClick(View v) {
            Intent intent = PeopleActivity.newIntent(getActivity(), mPeople.getId());
            startActivity(intent);
        }
    }

    private class PeopleAdapter extends RecyclerView.Adapter<PeopleHolder> {
        private List<People> mPeoples;

        public PeopleAdapter(List<People> peoples) {
            mPeoples = peoples;
        }

        @NonNull
        @Override
        public PeopleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PeopleHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PeopleHolder holder, int position) {
            People people = mPeoples.get(position);
            holder.bind(people);
        }

        @Override
        public int getItemCount() {
            return mPeoples.size();
        }
    }
}
