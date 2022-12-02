package ui.assignments.a4notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ui.assignments.a4notes.viewmodel.NotesViewModel

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val myVM: NotesViewModel by activityViewModels { NotesViewModel.Factory }

        val adapter = NoteListAdapter(myVM, activity as AppCompatActivity)
        val recyclerList = view.findViewById(R.id.recyclerList) as RecyclerView
        recyclerList.layoutManager = LinearLayoutManager(activity)
        recyclerList.adapter = adapter

        val toggle: Switch = view.findViewById(R.id.showArchivedSwitch)
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

        val addButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        addButton.setOnClickListener {
            val activity: AppCompatActivity = it.context as AppCompatActivity
            val addFragment: Fragment = AddFragment()
            val fm = activity.supportFragmentManager.beginTransaction()
            fm.replace(R.id.Frame_Container, addFragment).addToBackStack(null).commit()
        }

        return view
    }

}