from flask import Flask, request, jsonify
import vertexai
from vertexai.generative_models import GenerativeModel
from uuid import uuid4
from google.oauth2 import service_account

app = Flask(__name__)

chat_sessions = {}

SERVICE_ACCOUNT_FILE = 'tepi-teguk-pintar-7744ed3f6b75.json'

def initialize_vertex_ai():
    credentials = service_account.Credentials.from_service_account_file(
        SERVICE_ACCOUNT_FILE, scopes=["https://www.googleapis.com/auth/cloud-platform"]
    )
    vertexai.init(credentials=credentials, project="tepi-teguk-pintar", location="us-central1")

@app.route("/generate-response", methods=["POST"])
def generate_response():
    try:
        # Get data from the request
        data = request.json

        # Retrieve structured nutritional data
        product_name = data.get("product_name")
        energy_kcal = data.get("energy_kcal")
        sugars = data.get("sugars")
        saturated_fat = data.get("saturated_fat")
        salt = data.get("salt")
        fiber = data.get("fiber")
        proteins = data.get("proteins")
        nutriscore_grade = data.get("nutriscore_grade")

        # Retrieve the custom prompt, if provided
        custom_prompt = data.get("custom_prompt")

        # Retrieve session ID from the request, or create a new one if not provided
        session_id = data.get("session_id") or str(uuid4())

        # Initialize Vertex AI using service account credentials
        initialize_vertex_ai()

        # Check if there's an existing chat session
        if session_id in chat_sessions:
            chat = chat_sessions[session_id]
        else:
            # If no session exists for the given session_id, create a new chat session
            model = GenerativeModel("gemini-1.5-pro-002")
            chat = model.start_chat()
            chat_sessions[session_id] = chat  # Save the new session

        # Construct the input text
        if custom_prompt:
            # Use the custom prompt directly
            input_text = custom_prompt
        else:
            # Use the default structured prompt
            input_text = (
                f"Produk: {product_name}, Energi: {energy_kcal} kcal/100g, Gula: {sugars}g, "
                f"Lemak Jenuh: {saturated_fat}g, Garam: {salt}g, Serat: {fiber}g, "
                f"Protein: {proteins}g. Berdasarkan informasi nutrisi ini, produk ini mendapatkan Nutri-Score {nutriscore_grade}. "
                f"Mengapa produk ini mendapatkan skor tersebut? **Jawab dalam bahasa Indonesia.**"
            )

        # Send the input to the model and get the response
        response = chat.send_message(input_text)

        # Update the chat session context
        chat_sessions[session_id] = chat

        # Return the response along with the session ID and input
        return jsonify({
            "session_id": session_id,
            "input": input_text,
            "response": response.text
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(debug=True)
