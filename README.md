# Chat Application

This is a simple chat application implemented in Java using socket programming. It consists of a server and client that can communicate with each other over a network.

## Features

- Multiple clients can connect to the server simultaneously.
- Clients can send messages to each other through the server.
- Clients can change their nicknames.
- Clients can leave the chat gracefully.

## Project Structure
src/
│
├── com/muratprojects/
│ ├── Server.java
│ └── Client.java
│
└── README.md


## Requirements

- Java Development Kit (JDK) 8 or higher

## How to Run

### Running the Server

1. Compile the server code:
    ```bash
    javac com/muratprojects/Server.java
    ```

2. Run the server:
    ```bash
    java com.muratprojects.Server
    ```

### Running the Client

1. Compile the client code:
    ```bash
    javac com/muratprojects/Client.java
    ```

2. Run the client:
    ```bash
    java com.muratprojects.Client
    ```

### Interacting with the Chat Application

- **Joining the Chat**:
  When you run the client, you will be prompted to enter a nickname. Enter your desired nickname to join the chat.

- **Sending Messages**:
  Type your message and press Enter to send it to all connected clients.

- **Changing Nickname**:
  To change your nickname, type `/nick <new_nickname>` and press Enter. For example, `/nick Murat`.

- **Leaving the Chat**:
  To leave the chat, type `/quit` and press Enter. This will disconnect you from the server.

## Code Overview

### Server.java

- **Class `Server`**: Manages client connections and broadcasts messages to all clients.
  - `ArrayList<ConnectionHandler> connectionsList`: Stores active connections.
  - `ExecutorService pool`: Manages threads for handling client connections.
  - `ServerSocket server`: Listens for incoming client connections.
  - `boolean done`: Indicates if the server is shutting down.

- **Class `ConnectionHandler`**: Handles communication with individual clients.
  - `Socket client`: Represents the client socket.
  - `BufferedReader in`: Reads messages from the client.
  - `PrintWriter out`: Sends messages to the client.
  - `String nickName`: Stores the client's nickname.

### Client.java

- **Class `Client`**: Manages the connection to the server and communication with other clients through the server.
  - `Socket client`: Represents the client socket.
  - `BufferedReader in`: Reads messages from the server.
  - `PrintWriter out`: Sends messages to the server.
  - `boolean done`: Indicates if the client is shutting down.

- **Class `InputHandler`**: Handles user input from the console.
  - Reads messages from the user and sends them to the server.
  - Detects the `/quit` command to disconnect the client.
