package com.example.tepiapp.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tepiapp.R

class SuggestionAdapter(
    private val questions: List<String>,
    private val onQuestionClick: (String) -> Unit
) : RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder>() {

    inner class SuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestion: TextView = itemView.findViewById(R.id.tv_questionSuggestion)

        fun bind(question: String) {
            tvQuestion.text = question
            tvQuestion.setOnClickListener { onQuestionClick(question) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question_suggestion, parent, false)
        return SuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size
}
