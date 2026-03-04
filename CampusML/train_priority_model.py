import pandas as pd
import pickle

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

# Load dataset
df = pd.read_csv("campus_issues_dataset.csv")

# Combine description + category + severity as input
df["combined_input"] = df["description"] + " " + df["category"] + " " + df["severity"]

X = df["combined_input"]
y = df["priority"]

# Convert text to numbers
vectorizer_priority = TfidfVectorizer()
X_vectorized = vectorizer_priority.fit_transform(X)

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X_vectorized, y, test_size=0.2, random_state=42
)

# Train model
model_priority = LogisticRegression()
model_priority.fit(X_train, y_train)

# Test accuracy
y_pred = model_priority.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)

print("Priority Model Accuracy:", accuracy)

# Save model
pickle.dump(model_priority, open("priority_model.pkl", "wb"))
pickle.dump(vectorizer_priority, open("priority_vectorizer.pkl", "wb"))

print("Priority Model Saved Successfully!")