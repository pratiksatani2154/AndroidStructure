package com.theone.demopermission.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Filter;

import com.theone.demopermission.R;
import com.theone.demopermission.adapter.BindingRecyclerAdapter;
import com.theone.demopermission.databinding.ActivityMainBinding;
import com.theone.demopermission.databinding.RawLayoutBinding;
import com.theone.demopermission.retrofit.RestClient;
import com.theone.demopermission.utills.GlobalUtills;
import com.theone.demopermission.utills.MyDateTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainViewModel extends BaseObservable {
    ArrayList<String> filteredList = new ArrayList<>();
    private final Context context;
    private final ActivityMainBinding activityMainBinding;
    private final RestClient restClient;
    public ObservableField<BindingRecyclerAdapter<String, RawLayoutBinding>> adapter = new ObservableField<>();

    public MainViewModel(Context context, ActivityMainBinding activityMainBinding) {
        this.context = context;
        this.activityMainBinding = activityMainBinding;
        this.restClient = new RestClient(context);
        init();
    }

    private void init() {
        activityMainBinding.rvMain.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        String date = GlobalUtills.getCurrentDate(GlobalUtills.DDMMMYYYY1);
        try {
            String formattedDate = GlobalUtills.formatDateFromDateString(GlobalUtills.DDMMMYYYY1, GlobalUtills.EEEDMMMYYYYHHMMSSZ, date);
            GlobalUtills.showMessage(context, "Current Date is: " + formattedDate, GlobalUtills.LENGTH_LONG);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //todo this is temporary arraylist for the Recycler view you can add your own arraylist with Model class
        ArrayList<String> s = new ArrayList<>();
        s.add("Ahmedabad");
        s.add("Surat");
        s.add("Vadodara");
        s.add("Ankleshwar");
        s.add("Kutch");
        s.add("Bhuj");
        s.add("Junagadh");
        s.add("Rajkot");
        s.add("Dwarka");
        s.add("GirSomnath");
        s.add("Bhavnagar");
        s.add("Jamnagar");
        s.add("Amreli");
        setAdapter(s);
        activityMainBinding.searchedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {
                    adapter.get().getFilter().filter(s);
                } else {
                    adapter.get().getFilter().filter("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setAdapter(final ArrayList<String> s) {
        filteredList.clear();
        filteredList.addAll(s);
        adapter.set(new BindingRecyclerAdapter<String, RawLayoutBinding>(R.layout.raw_layout) {
            @Override
            protected String getItemForPosition(int position) {
                return filteredList.get(position);
            }

            @Override
            protected void setView(RawLayoutBinding binding, int position) {
                String str = getItemForPosition(position);
                binding.textView.setText("" + str);
            }

            @Override
            protected Filter filterList() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        String CharString = constraint.toString();
                        if (CharString.isEmpty()) {
                            filteredList = s;
                        } else {
                            ArrayList<String> filteredLists = new ArrayList<>();
                            for (String model : s) {
                                String firstName = model.toLowerCase();
                                if (firstName.contains(CharString.toLowerCase())) {
                                    filteredLists.add(model);
                                }
                            }
                            filteredList = filteredLists;
                        }
                        FilterResults filter = new FilterResults();
                        filter.values = filteredList;

                        return filter;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        filteredList = (ArrayList<String>) results.values;
                        // refresh the list with filtered data
                        notifyDataSetChanged();
                    }
                };
            }

            @Override
            public int getBindingVariableID() {
                return filteredList.size();
            }

            @Override
            public int getItemCount() {
                return filteredList.size();
            }
        });
    }

    public void defaultDatePicker() {
        MyDateTimePicker.getInstance(context).setListener(new MyDateTimePicker.OnDateTimeSetListener() {
            @Override
            public void onDateSet(String dateInString, Date date) {

            }

            @Override
            public void onTimeSet(String time) {

            }
        }).showDateDialog(false);
    }

    public void defaultTimePicker() {
        MyDateTimePicker.getInstance(context).setListener(new MyDateTimePicker.OnDateTimeSetListener() {
            @Override
            public void onDateSet(String dateInString, Date date) {

            }

            @Override
            public void onTimeSet(String time) {

            }
        }).showTimeDialog(false);
    }

    public void customCalander() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 10);

        MyDateTimePicker myDateTimePicker = new MyDateTimePicker(context);
        myDateTimePicker.setMinDate(Calendar.getInstance());
        myDateTimePicker.setMaxDate(calendar);
        myDateTimePicker.setPositiveButtonText("okay");
        myDateTimePicker.setNegativeButtonText("no");
        myDateTimePicker.setListener(new MyDateTimePicker.OnDateTimeSetListener() {
            @Override
            public void onDateSet(String dateInString, Date date) {

            }

            @Override
            public void onTimeSet(String time) {

            }
        });
        myDateTimePicker.setPositiveButtonColor(R.color.colorBlack);
        myDateTimePicker.setCancelable(false);
        myDateTimePicker.setCancelOutSideTouch(false);
        myDateTimePicker.setDateReturnFormat(new SimpleDateFormat("dd-MMM-yyyy"));
        myDateTimePicker.set24HourTime(true);
        myDateTimePicker.showTimeDialog(true);
    }

}
