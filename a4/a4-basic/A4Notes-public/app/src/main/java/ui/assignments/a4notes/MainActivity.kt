package ui.assignments.a4notes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ui.assignments.a4notes.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NoteListAdapter
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myVM : NotesViewModel by viewModels { NotesViewModel.Factory }

        adapter = NoteListAdapter(myVM,this)
        val recyclerList = findViewById<View>(R.id.recyclerList) as RecyclerView
        recyclerList.layoutManager = LinearLayoutManager(this)
        recyclerList.adapter = adapter

        val toggle: Switch = findViewById(R.id.showArchivedSwitch)
        toggle.isChecked = myVM.getViewArchived().value == true
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myVM.toggleViewArchived()
                adapter.notifyDataSetChanged()
            } else {
                myVM.toggleViewArchived()
                adapter.notifyDataSetChanged()
            }
        }
    }
}