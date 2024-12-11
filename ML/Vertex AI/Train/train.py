import json

# Read the input .jsonl file
input_filename = 'train.jsonl'
output_filename = 'output.jsonl'

# Initialize a list to hold the formatted content
formatted_data = []

# Read the .jsonl file and process each line
with open(input_filename, 'r') as infile:
    for line in infile:
        # Parse the current line as a JSON object
        entry = json.loads(line.strip())
        
        # Extract input and output from the entry
        user_input = entry["input"]
        output = entry["output"]
        
        # Create the new structured data
        formatted_entry = {
            "contents": [
                {
                    "role": "user",
                    "parts": [
                        {
                            "text": f"{user_input.lower()}"
                        }
                    ]
                },
                {
                    "role": "model",
                    "parts": [
                        {
                            "text": f"{output.lower()}"
                        }
                    ]
                }
            ]
        }
        
        # Append the formatted entry to the list
        formatted_data.append(formatted_entry)

# Write the formatted data to a new .jsonl file
with open(output_filename, 'w') as outfile:
    for entry in formatted_data:
        outfile.write(json.dumps(entry) + "\n")

print("Data has been successfully transformed and saved.")
