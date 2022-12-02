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
    @SuppressLint("UseSwitchCompatOrMaterialCode", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        val fm = this.supportFragmentManager.beginTransaction()
        fm.replace(R.id.Frame_Container, mainFragment, "main_fragment").commit()
    }
}