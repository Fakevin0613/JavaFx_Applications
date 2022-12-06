package ui.assignments.a4notes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ui.assignments.a4notes.viewmodel.NotesViewModel

@Suppress("NAME_SHADOWING")
class EditFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_screen, container, false)
        val id = this.arguments?.getInt("id")
        val position = this.arguments?.getInt("position")
        val myVM: NotesViewModel by activityViewModels { NotesViewModel.Factory }

        val title = position?.let { myVM.getNotes().value?.get(it)?.value?.title }
        val content = position?.let { myVM.getNotes().value?.get(it)?.value?.content }
        val archived = position?.let { myVM.getNotes().value?.get(it)?.value?.archived }
        val important = position?.let { myVM.getNotes().value?.get(it)?.value?.important }


        view.findViewById<TextView>(R.id.Edit_Title).text = title
        view.findViewById<TextView>(R.id.Edit_Content).text = content
        if (archived != null) {
            view.findViewById<Switch>(R.id.Edit_Archived).isChecked = archived
        }
        if (important != null) {
            view.findViewById<Switch>(R.id.Edit_Important).isChecked = important
        }

        view.findViewById<TextView>(R.id.Edit_Title).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (id != null) {
                    myVM.updateTitle(id, s.toString())
                }
            }
        })

        view.findViewById<TextView>(R.id.Edit_Content).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (id != null) {
                    myVM.updateContent(id, s.toString())
                }
            }
        })

        view.findViewById<Switch>(R.id.Edit_Archived).setOnClickListener{
                _: View ->
            if(view.findViewById<Switch>(R.id.Edit_Archived).isChecked){
                if (id != null) {
                    myVM.archiveANote(id)
                    view.findViewById<Switch>(R.id.Edit_Important).isChecked = false
                }
            }
            else{
                if (id != null) {
                    myVM.unArchiveANote(id)
                }
            }

        }

        view.findViewById<Switch>(R.id.Edit_Important).setOnClickListener{
                _: View ->
            if(view.findViewById<Switch>(R.id.Edit_Important).isChecked){
                if (id != null) {
                    myVM.importantNote(id)
                }
                view.findViewById<Switch>(R.id.Edit_Archived).isChecked = false
            }
            else{
                if (id != null) {
                    myVM.unImportantNote(id)
                }
            }
        }

        //The back-button navigates back to the main screen
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val activity: AppCompatActivity = view.context as AppCompatActivity
            val mainFragment: Fragment = MainFragment()
            val fm = activity.supportFragmentManager.beginTransaction()
            fm.replace(R.id.Frame_Container, mainFragment).addToBackStack(null).commit()
        }

        return view
    }
}