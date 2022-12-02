package ui.assignments.a4notes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ui.assignments.a4notes.viewmodel.NotesViewModel

class NoteListAdapter(private val viewModel: NotesViewModel, private val activity: AppCompatActivity): RecyclerView.Adapter<NoteListAdapter.ViewHolder>(){

        init{
                viewModel.getNotes().observe(activity){
                        notifyDataSetChanged()
                }
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
                var title: TextView = view.findViewById(R.id.title_string) as TextView
                var content: TextView = view.findViewById(R.id.content_string) as TextView
                var card : LinearLayout = view.findViewById(R.id.note_item) as LinearLayout
                var archivedButton: Button = view.findViewById(R.id.archived_button) as Button
                var deleteButton: Button = view.findViewById(R.id.delete_button) as Button
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val v: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
                return ViewHolder(v)
        }

        override fun getItemCount(): Int {
                return viewModel.getNotes().value?.size ?: 0
        }

        @SuppressLint("ResourceType")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.card.setBackgroundColor(Color.parseColor("#FFFFFF"))
                if(viewModel.getNotes().value?.get(position)?.value?.important == true){
                        holder.card.setBackgroundColor(Color.parseColor("#f699cd"))
                }

                if(viewModel.getNotes().value?.get(position)?.value?.archived == true){
                        holder.card.setBackgroundColor(Color.parseColor("#a9a9a9"))
                }

                holder.title.text = viewModel.getNotes().value?.get(position)?.value?.title ?: "title fails to load"
                holder.content.text = viewModel.getNotes().value?.get(position)?.value?.content ?: "content fails to load"

                if(holder.title.text == ""){
                        holder.title.text = "Title"
                }

                if(holder.content.text == ""){
                        holder.content.text = "Content"
                }

                holder.archivedButton.setOnClickListener{
                        _: View ->
                        viewModel.getNotes().value?.get(position)?.value?.id?.let { it1 ->
                                if(viewModel.getNotes().value?.get(position)?.value?.archived == false){
                                        viewModel.archiveANote(it1)
                                }
                                else{
                                        viewModel.unArchiveANote(it1)
                                }
                        }
                }

                holder.deleteButton.setOnClickListener {
                        _: View ->
                        if(viewModel.getNotes().value?.get(position)?.value?.important == false){
                                viewModel.getNotes().value?.get(position)?.value?.id?.let { it1 -> viewModel.deleteANote(it1) }
                        }
                }

                val param = holder.itemView.layoutParams
                if(viewModel.getNotes().value?.get(position)?.value?.archived == true && viewModel.getViewArchived().value == false){
                        param.height = 0
                        param.width = 0
                        holder.itemView.visibility = View.GONE
                }
                else{
                        param.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        param.width = LinearLayout.LayoutParams.MATCH_PARENT
                        holder.itemView.visibility = View.VISIBLE
                }
                holder.itemView.layoutParams = param

                holder.card.setOnClickListener {
                        val activity: AppCompatActivity = it.context as AppCompatActivity
                        val bundle = Bundle()
                        val theID = viewModel.getNotes().value?.get(position)?.value?.id
                        if (theID != null) {
                                bundle.putInt("id", theID)
                        }
                        bundle.putInt("position", position)
                        val editFragment: Fragment = EditFragment()
                        editFragment.arguments = bundle
                        val fm = activity.supportFragmentManager.beginTransaction()
                        fm.replace(R.id.Frame_Container, editFragment).addToBackStack(null).commit()
                }
        }
}
