import pickle

# Load saved model
model = pickle.load(open("category_model.pkl", "rb"))
vectorizer = pickle.load(open("vectorizer.pkl", "rb"))

# Test sentence
new_issue = ["There is water leaking near the main electric switch board in lab and students are scared"]

# Convert text to numbers
new_issue_vectorized = vectorizer.transform(new_issue)

# Predict category
prediction = model.predict(new_issue_vectorized)

print("Predicted Category:", prediction[0])