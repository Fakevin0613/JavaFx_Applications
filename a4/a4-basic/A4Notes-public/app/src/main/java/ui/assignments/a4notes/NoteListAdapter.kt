package ui.assignments.a4notes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ui.assignments.a4notes.viewmodel.NotesViewModel

@SuppressLint("NotifyDataSetChanged")
class NoteListAdapter(private val viewModel: NotesViewModel, private val activity: MainActivity): RecyclerView.Adapter<NoteListAdapter.ViewHolder>(){

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
                return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
        }

        override fun getItemCount(): Int {
                return viewModel.getNotes().value?.size ?: 0
        }

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

                holder.archivedButton.setOnClickListener{
                        _: View ->
                        viewModel.getNotes().value?.get(position)?.value?.id?.let { it1 -> viewModel.archiveANote(it1) }
                }

                holder.deleteButton.setOnClickListener {
                        _: View ->
                        if(viewModel.getNotes().value?.get(position)?.value?.important == false){
                                viewModel.getNotes().value?.get(position)?.value?.id?.let { it1 -> viewModel.deleteANote(it1) }
                        }
                }
        }
}
