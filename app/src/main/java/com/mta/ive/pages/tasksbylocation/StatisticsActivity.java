package com.mta.ive.pages.tasksbylocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mta.ive.R;
import com.mta.ive.logic.LogicHandler;
import com.mta.ive.logic.location.LocationWithTasksWrapper;
import com.mta.ive.logic.task.Task;

public class StatisticsActivity extends AppCompatActivity {

    TextView locationNameTextView;
    int completed = 3, created = 7;

    String locationName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        locationNameTextView = findViewById(R.id.textViewLocation);

        loadValues();
        updateChart();
        setNavigationButtons();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadValues() {
        int indexOfSelectedLocation = LogicHandler.getLastSelectedIndex();
        LocationWithTasksWrapper location = LogicHandler.getSwichableLocationsWithAll()
                .get(indexOfSelectedLocation);

        locationName = location.getLocation().getName();
        locationNameTextView.setText("Productivity at " + locationName);

        String locationId = location.getLocation().getId();
        int leftToFinish = location.getTasks().size();

        created = (int) LogicHandler.getLocationIdToAllTasksCreated().get(locationId).stream()
                .filter(task -> task.getStatus() != Task.Status.ARCHIVED).count();
        completed = created - leftToFinish;

    }

    public void updateChart(){

        TextView numberOfCals = findViewById(R.id.number_of_calories);
        String doneStr = String.valueOf(completed);
        String totalStr = String.valueOf(created);
        numberOfCals.setText(doneStr + " / " + totalStr);

        // Calculate the slice size and update the pie chart:
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        double d = (double) completed / (double) created;
        int progress = (int) (d * 100);
        pieChart.setProgress(progress);
    }

    private void setNavigationButtons() {
        ((BottomNavigationView)findViewById(R.id.nav_view)).setSelectedItemId(R.id.navigation_location);

        findViewById(R.id.navigation_location).setOnClickListener( t -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("selection", "1");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
        findViewById(R.id.navigation_add).setOnClickListener( t -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("selection", "2");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        findViewById(R.id.navigation_user).setOnClickListener( t -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("selection", "3");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
    }
}
