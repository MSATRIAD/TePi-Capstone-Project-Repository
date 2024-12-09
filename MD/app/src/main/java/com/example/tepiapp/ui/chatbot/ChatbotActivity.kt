import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tepiapp.R

class ChatbotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        val suggestions = listOf(
            "Mengapa minuman ini memiliki grade tersebut?",
            "Apakah ada rekomendasi minuman serupa yang lebih baik?",
            "Jelaskan komposisi dari minuman ini! [Scan]"
        )

        val rvSuggestions = findViewById<RecyclerView>(R.id.rv_questionSuggestions)
        rvSuggestions.layoutManager = LinearLayoutManager(this)
        rvSuggestions.adapter = SuggestionAdapter(suggestions) { selectedQuestion ->
            sendMessage(selectedQuestion)
        }
    }

    private fun sendMessage(message: String) {
        Log.d("Chatbot", "User selected: $message")
        // Tambahkan pesan ke RecyclerView chat history
    }
}
