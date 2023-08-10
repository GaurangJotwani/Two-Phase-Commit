# KeyStore Application

The KeyStore application is a client-server program that allows users to store, retrieve, and delete key-value pairs using Java RMI (Remote Method Invocation).

## Files

The project consists of the following files:

- `KeyStore.java`: Interface defining the methods for interacting with the KeyStore server.
- `KeyStoreRemote.java`: Implementation of the KeyStore interface, representing the server-side logic.
- `MyServer.java`: Main class for starting the KeyStore server.
- `MyClient.java`: Main class for running the KeyStore client.
- `README.md`: This file, providing an overview of the project and instructions.

## Assumptions

The following assumptions were made in the implementation of this project:

- The maximum message size for a command or value is limited to 1024 bytes.
- The server supports a single client connection at a time. If multiple clients attempt to connect simultaneously, they will be queued and served in a sequential manner.
- The server does not persist the key-value store in a database or file. If the server is restarted, all stored data will be lost.
- The key and val stored in the cache will be string type.
- The server will be available (run) forever.

## Compilation and Running

To compile the project, follow these steps:

1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Open a terminal or command prompt.
3. Navigate to the project directory.
4. Compile the source files using the following command:


    javac *.java


5. Register rmiregistry to the desired port


    rmiregistry <port>

Replace `<port>` with the desired port number to bind the server on.

6. To run the server, use the following command


    java MyServer <port>

Replace `<port>` with the desired port number to start the server on.

7. To run the client, use the following command


    java MyClient <ip> <port>


Replace `<ip>` with the IP address or hostname of the server, and `<port>` with the port number the server is running on.

## Sending Commands to the Server

Once the server and client are running, you can send commands to the server using the client. The following commands are supported:

- **GET**: Retrieve the value associated with a specific key.

`GET <key>`

The server will respond with the value associated with the key, or an error message if the key does not exist. Server will start the message with "1" if request is successful otherwise it's an error.

- **PUT**: Store a key-value pair in the server's key-value store.

`PUT <key> <val>`

The server will respond with a success message if the key-value pair is stored successfully. Server will start the message with "1" if request is successful otherwise it's an error.

- **DELETE**: Remove a key-value pair from the server's key-value store.

`DELETE <key>`

The server will respond with a success message if the key is found and removed, or an error message if the key does not exist. Server will start the message with "1" if request is successful otherwise it's an error.

- **QUIT**: Close the connection to the server and terminate the client.

`QUIT <key>`

## Multithreading

The KeyStore server implementation handles multiple client requests concurrently using multithreading. Each client request is executed in a separate thread, allowing multiple clients to interact with the server simultaneously without blocking each other.

This is achieved by leveraging Java RMI built-in support for multithreading. When a client connection is established, the server creates a new thread to handle the client's requests. This ensures that multiple clients can execute their operations concurrently, improving the responsiveness and scalability of the system.

To manage concurrent access to the shared data (key-value store) and prevent race conditions, a `ReentrantLock` is used. This lock ensures that only one thread can access the key-value store at a time.

The multithreading approach employed in this project enables efficient and concurrent access to the KeyStore server, providing a responsive and scalable solution.

## Error Handling

The KeyStore application incorporates error handling to provide robustness and graceful handling of various exceptional scenarios. The following types of errors are taken care of:

- **RemoteException**: This exception can occur during remote method invocations and signifies a failure in the RMI communication. The application handles RemoteExceptions appropriately and provides meaningful error messages to the user.

- **MalformedURLException**: This exception can occur when the URL for the RMI registry is malformed or invalid. The application catches this exception and displays an informative error message, allowing the user to retry with a correct URL.

- **UnknownHostException**: This exception can occur when the server's IP address or hostname is not recognized. The application handles this exception and notifies the user about the unknown host error.

- **AccessException**: This exception can occur when there is a problem accessing the remote host. The application catches AccessExceptions and displays an appropriate error message to guide the user.

- **NotBoundException**: This exception can occur when the requested remote object is not found in the RMI registry. The application handles this exception and informs the user about the unavailability of the desired remote object.

- **ConnectException**: This exception can occur when the client fails to establish a connection with the server. The application catches ConnectExceptions and provides a user-friendly error message, indicating a timeout or unresponsive server.
- **KeyLength Error**: If length of key or value is more than 1024 bytes, it will ask the user to input it again.
- **User Errors**: If there are user errors like invalid keys or invalid commands, the server will respond with a RemoteException.



By handling these exceptions, the KeyStore application enhances its robustness and provides users with clear feedback in case of errors. The error handling mechanism ensures that the application gracefully handles exceptional situations and guides users in resolving any connection or communication issues.




