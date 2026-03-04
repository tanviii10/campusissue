import pandas as pd
import random

categories = {
    "Infrastructure": [
        "Broken desk",
        "Ceiling leakage",
        "Projector not working",
        "Fan not working",
        "Damaged window glass"
    ],
    "Cleanliness & Hygiene": [
        "Washroom not cleaned",
        "Garbage accumulated",
        "Bad smell in washroom",
        "Water spilled",
        "Dusty classroom floor"
    ],
    "Technical Issue": [
        "WiFi not working",
        "Computer system crash",
        "Server down",
        "Smart board not responding",
        "Printer not working"
    ],
    "Safety": [
        "Smoke from switch board",
        "Electrical spark observed",
        "Water leakage near electric panel",
        "Broken staircase railing",
        "Fire alarm malfunction"
    ],
    "Other": [
        "Parking space issue",
        "Canteen food complaint",
        "Noise disturbance",
        "Library timing issue",
        "Bus delay problem"
    ]
}

locations = ["Classroom", "Lab", "Campus Area", "Washrooms", "Other"]
severities = ["Low", "Medium", "High"]

data = []

for category, issues in categories.items():
    for i in range(50):
        description = random.choice(issues) + " in " + random.choice(locations)
        location = random.choice(locations)

        if category == "Safety":
            severity = random.choices(["High", "Medium"], weights=[0.7, 0.3])[0]
        else:
            severity = random.choices(severities, weights=[0.3, 0.45, 0.25])[0]

        # Resolution time logic
        if severity == "High":
            resolution = random.randint(1, 12)
        elif severity == "Medium":
            resolution = random.randint(8, 24)
        else:
            resolution = random.randint(24, 48)

        # Priority logic (SMART RULE)
        if category == "Safety":
            priority = "High"
        else:
            priority = severity

        data.append([description, category, location, severity, priority, resolution])

df = pd.DataFrame(data, columns=[
    "description", "category", "location", "severity", "priority", "resolutionTimeHours"
])

df.to_csv("campus_issues_dataset.csv", index=False)

print("Updated Dataset with Priority Generated Successfully!")