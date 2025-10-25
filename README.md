# 🎯 Kotlin Math Quiz Game

A simple console-based quiz game built using **Kotlin**, focusing on arithmetic math questions.  
The player answers 10 randomly generated questions per game with multiple difficulty levels to choose from.

---

## 📌 Features

✅ 4 Difficulty Modes:
- Easy
- Medium
- Hard
- Mix Level (random difficulty each question)

✅ Math Operations Supported:
- Addition ➕
- Subtraction ➖
- Multiplication ✖️
- Mix (all operations randomized)

✅ Game Rules:
- 10 questions each round
- Score calculated at the end
- Accepts user input directly through console

---

## 🧠 Question Levels

| Level | Number Range |
|-------|--------------|
| Easy | 1 to 10 |
| Medium | 10 to 50 |
| Hard | 50 to 100 |
| Mix | Random from all levels |

---

## 📐 Operations

| Operation | Example |
|----------|---------|
| Addition | 4 + 6 |
| Subtraction | 12 - 5 |
| Multiplication | 7 × 3 |
| Mix | Any of the above |

---

## ▶️ How to Run

Make sure you have **Kotlin** installed on your system.
Then run:

```bash
kotlinc Main.kt -include-runtime -d QuizGame.jar
java -jar QuizGame.jar
