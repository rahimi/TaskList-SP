package de.hsba.app.tasklistsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final String SHARED_NAME = "TASK SP";

    ArrayList<Task> tasks;

    TaskListAdapter taskListAdapter;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        String tasksString = sp.getString(SHARED_NAME,null);
        if (tasksString!=null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Task>>(){}.getType();
            tasks = gson.fromJson(tasksString,type);
        }else {
            tasks = new ArrayList<Task>();
        }

        RecyclerView tasksList = findViewById(R.id.list_view);
        tasksList.setLayoutManager(new LinearLayoutManager(this));
        taskListAdapter = new TaskListAdapter(tasks);
        tasksList.setAdapter(taskListAdapter);

    }

    public void addItem(View view) {
        Random r = new Random();
        tasks.add(new Task(r.nextInt()+" Task"));
        taskListAdapter.notifyDataSetChanged();
        String temp = new Gson().toJson(tasks);
        sp.edit().putString(SHARED_NAME,temp).commit();

        Toast.makeText(this,temp,Toast.LENGTH_SHORT).show();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder>{

        private final List<Task> tasks;

        public TaskListAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                                    .inflate(android.R.layout.simple_list_item_1,
                                             parent,false);
            return new TaskViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            TextView label = holder.itemView.findViewById(android.R.id.text1);
            label.setText(tasks.get(position).getLabel());
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }
}