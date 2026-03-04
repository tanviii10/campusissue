import pickle
import sys
import json
import os

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

# Load models
category_model = pickle.load(open(os.path.join(BASE_DIR, "category_model.pkl"), "rb"))
category_vectorizer = pickle.load(open(os.path.join(BASE_DIR, "vectorizer.pkl"), "rb"))

priority_model = pickle.load(open(os.path.join(BASE_DIR, "priority_model.pkl"), "rb"))
priority_vectorizer = pickle.load(open(os.path.join(BASE_DIR, "priority_vectorizer.pkl"), "rb"))

resolution_model = pickle.load(open(os.path.join(BASE_DIR, "resolution_model.pkl"), "rb"))
resolution_vectorizer = pickle.load(open(os.path.join(BASE_DIR, "resolution_vectorizer.pkl"), "rb"))

# Get input from command line
description = sys.argv[1]
severity = sys.argv[2]

# ---- Category Prediction ----
desc_vector = category_vectorizer.transform([description])
predicted_category = category_model.predict(desc_vector)[0]

# ---- Priority Prediction ----
combined_priority_input = description + " " + predicted_category + " " + severity
priority_vector = priority_vectorizer.transform([combined_priority_input])
predicted_priority = priority_model.predict(priority_vector)[0]

# ---- Resolution Prediction ----
combined_resolution_input = (
    description + " " +
    predicted_category + " " +
    severity + " " +
    predicted_priority
)

resolution_vector = resolution_vectorizer.transform([combined_resolution_input])
predicted_resolution = resolution_model.predict(resolution_vector)[0]

# Final result
result = {
    "category": predicted_category,
    "priority": predicted_priority,
    "predictedResolutionTimeHours": round(float(predicted_resolution), 2)
}

print(json.dumps(result))