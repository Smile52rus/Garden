package com.arzaapps.android.garden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.arzaapps.android.garden.database.PeopleBaseHelper;
import com.arzaapps.android.garden.database.PeopleDbSchema;

import java.util.ArrayList;
import java.util.List;

import static com.arzaapps.android.garden.database.PeopleDbSchema.*;

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

            case R.id.action_export_database_to_excel:
                SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getActivity(), PeopleBaseHelper.DATABASE_NAME);
                sqliteToExcel.exportSingleTable(PeopleTable.NAME, "peoples.xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                        Toast toast = Toast.makeText(getActivity(),
                                "Выгрузка начата",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onCompleted(String filePath) {
                        Toast toast = Toast.makeText(getActivity(),
                                "Выгрузка успешно завершена",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast toast = Toast.makeText(getActivity(),
                                "Ошибка выгрузки",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                return true;

            case R.id.action_import_database_to_excel:
                String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/peoples.xls";
                ExcelToSQLite excelToSQLite = new ExcelToSQLite(getActivity().getApplicationContext(), PeopleBaseHelper.DATABASE_NAME, true);
                excelToSQLite.importFromFile(directory_path, new ExcelToSQLite.ImportListener() {
                    @Override
                    public void onStart() {
                        Toast toast = Toast.makeText(getActivity(),
                                "Старт загрузки",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onCompleted(String dbName) {
                        Toast toast = Toast.makeText(getActivity(),
                                "Успешная загрузка",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast toast = Toast.makeText(getActivity(),
                                "Ошибка pfгрузки",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                return true;
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
