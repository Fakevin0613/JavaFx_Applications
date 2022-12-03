package ui.assignments.a4notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ui.assignments.a4notes.viewmodel.NotesViewModel

class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myVM: NotesViewModel by activityViewModels { NotesViewModel.Factory }
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //At the bottom is a button for creating a new note. This button also navigates back to the main screen
        view.findViewById<Button>(R.id.Add_submit).setOnClickListener{
                _: View ->
            val title = view.findViewById<TextView>(R.id.Add_Title).text.toString()
            val content = view.findViewById<TextView>(R.id.Add_Content).text.toString()
            val important = view.findViewById<Switch>(R.id.Add_Important).isChecked
            myVM.addNote(title, content, important)


            val activity: AppCompatActivity = view.context as AppCompatActivity
            val mainFragment: Fragment = MainFragment()
            val fm = activity.supportFragmentManager.beginTransaction()
            fm.replace(R.id.Frame_Container, mainFragment).addToBackStack(null).commit()
        }

        return view
    }

}