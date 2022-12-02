package ui.assignments.a4notes

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ui.assignments.a4notes.R
import ui.assignments.a4notes.viewmodel.NotesViewModel

class EditActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_screen)

        findViewById<TextView>(R.id.Edit_Title).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.Edit_Content).text = intent.getStringExtra("content")
        findViewById<Switch>(R.id.Edit_Archived).isChecked = intent.getBooleanExtra("archived", false)
        findViewById<Switch>(R.id.Edit_Important).isChecked = intent.getBooleanExtra("important", false)

        val myVM : NotesViewModel by viewModels { NotesViewModel.Factory }

        findViewById<Switch>(R.id.Edit_Important).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myVM.toggleViewArchived()
            } else {
                myVM.toggleViewArchived()
            }
        }
    }
}
