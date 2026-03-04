import pandas as pd
import pickle

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error

# Load dataset
df = pd.read_csv("campus_issues_dataset.csv")

# Combine useful inputs
df["combined_input"] = (
    df["description"] + " " +
    df["category"] + " " +
    df["severity"] + " " +
    df["priority"]
)

X = df["combined_input"]
y = df["resolutionTimeHours"]

# Convert text to numbers
vectorizer_resolution = TfidfVectorizer()
X_vectorized = vectorizer_resolution.fit_transform(X)

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X_vectorized, y, test_size=0.2, random_state=42
)

# Train regression model
model_resolution = LinearRegression()
model_resolution.fit(X_train, y_train)

# Test model
y_pred = model_resolution.predict(X_test)

error = mean_absolute_error(y_test, y_pred)

print("Mean Absolute Error (Hours):", error)

# Save model
pickle.dump(model_resolution, open("resolution_model.pkl", "wb"))
pickle.dump(vectorizer_resolution, open("resolution_vectorizer.pkl", "wb"))

print("Resolution Model Saved Successfully!")