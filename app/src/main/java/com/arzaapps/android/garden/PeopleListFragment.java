package com.arzaapps.android.garden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PeopleListFragment extends Fragment implements SearchView.OnQueryTextListener {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_people:
                //   People people = new People();
                //    PeopleLab.get(getActivity()).addPeople(people);
                Intent intent = PeopleActivity.newIntent(getActivity(), null);
                startActivity(intent);
                return true;

            case R.id.action_search:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        PeopleLab peopleLab = PeopleLab.get(getActivity());
        int peopleCount = peopleLab.getPeoples().size();
        String subtitle = getString(R.string.subtitle_format, peopleCount);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_people_list, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
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
            mAdapter.setPeoples(peoples);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private void updateList(List<People> newList) {
        List<People> peoples = new ArrayList<>();
        peoples.addAll(newList);
        mAdapter.setPeoples(peoples);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        PeopleLab peopleLab = PeopleLab.get(getActivity());
        List<People> peoples = peopleLab.getPeoples();

        String userInput = newText;

        List<People> newList = new ArrayList<>();

        for (int i = 0; i < peoples.size(); i++) {
            if ((peoples.get(i).getName().toLowerCase().contains(userInput)) || (peoples.get(i).getNumberArea().toLowerCase().contains(userInput)) || (peoples.get(i).getTelephoneNumber().toLowerCase().contains(userInput)) || (peoples.get(i).getHomeAdress().toLowerCase().contains(userInput))) {
                newList.add(peoples.get(i));
            }
        }

        updateList(newList);
        return true;
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
            mNumberAreaTextView = itemView.findViewById(R.id.number_area_text_view);
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

        public void setPeoples(List<People> peoples) {
            mPeoples = peoples;
        }
    }

}
