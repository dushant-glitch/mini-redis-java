# 🚀 Mini Redis (Java)

An in-memory key-value database inspired by Redis, built using Java.  
This project demonstrates efficient data storage, caching mechanisms, and system design concepts.

---

## 📌 Overview

Mini Redis is a lightweight implementation of a key-value store that supports fast data access using in-memory storage. It includes advanced features like LRU eviction, TTL-based expiration, persistence, and multithreading.

---

## 🔥 Features

- ⚡ O(1) SET, GET, DELETE operations  
- 🧠 LRU Cache using HashMap + Doubly Linked List  
- ⏳ TTL (Time-To-Live) based key expiration  
- 💾 File-based persistence (data survives restart)  
- 🔄 Multithreading for background cleanup of expired keys  
- 🧵 Thread-safe operations using synchronization  

---

## 🏗️ Architecture

Client Input → Database → LRU Cache → Storage (Memory + File)  
                                ↓  
                      Expiry Cleaner Thread  

---

## 🛠️ Tech Stack

- Language: Java  
- Core Concepts:  
  - Data Structures (HashMap, Doubly Linked List)  
  - Multithreading  
  - Object-Oriented Programming  
  - System Design  

---

## 📁 Project Structure

mini-redis-java/  
│── src/  
│   ├── cache/        # LRU Cache implementation  
│   ├── database/     # Database interface and logic  
│   ├── threads/      # Background expiry cleaner  
│   ├── Main.java     # Entry point  
│── README.md  
│── .gitignore  

---

## ▶️ How to Run

```bash
cd src
javac Main.java database/*.java cache/*.java threads/*.java
java Main
