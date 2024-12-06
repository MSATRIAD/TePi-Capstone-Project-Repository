from fastapi import FastAPI
from pydantic import BaseModel
import numpy as np
from tensorflow import keras
from fastapi.middleware.cors import CORSMiddleware

model = keras.models.load_model('models/model.h5')

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], 
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class NutriscoreRequest(BaseModel):
    energy_kcal: float
    sugars: float
    saturated_fat: float
    salt: float
    fruits_veg_nuts: float
    fiber: float
    proteins: float

def predict_nutriscore(energy_kcal, sugars, saturated_fat, salt, fruits_veg_nuts, fiber, proteins):
    input_data = np.array([[energy_kcal, sugars, saturated_fat, salt, fruits_veg_nuts, fiber, proteins]])
    predictions = model.predict(input_data)
    predicted_class = np.argmax(predictions, axis=1)[0]
    
    label_mapping = {0: 'a', 1: 'b', 2: 'c', 3: 'd', 4: 'e'}
    predicted_grade = label_mapping[predicted_class]
    
    return predicted_grade

@app.post("/predict/")
async def predict_nutriscore_endpoint(request: NutriscoreRequest):
    grade = predict_nutriscore(
        energy_kcal=request.energy_kcal,
        sugars=request.sugars,
        saturated_fat=request.saturated_fat,
        salt=request.salt,
        fruits_veg_nuts=request.fruits_veg_nuts,
        fiber=request.fiber,
        proteins=request.proteins
    )
    return {"predicted_grade": grade}

@app.get("/favicon.ico")
async def favicon():
    return {"message": "No favicon available"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)