/*
 * KeyStoreRemote.java
 *
 * This file implements the KeyStore interface, providing the functionality to store, retrieve,
 * and delete key-value pairs in the Key Store. It uses a HashMap as the underlying data structure
 * and ensures thread safety by utilizing a ReentrantLock for mutual exclusion.
 *
 * Author: Gaurang Jotwani
 * Course: NEU Summer 23 CS 6650
 * Date: 06/20/2023
 */

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.HashMap;
import java.rmi.server.RemoteServer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KeyStoreRemote extends UnicastRemoteObject implements KeyStore {

  // Shared key-value store
  private static HashMap<String, String> keyValStore = new HashMap<String, String>();
  // Lock for thread safety
  private Lock lock = new ReentrantLock();
  // Constructor
  KeyStoreRemote() throws RemoteException {
    super();
  }

  // Store a key-value pair in the Key Store
  public void put(String key, String val) throws RemoteException {
    lock.lock();
    try {
      String serverMessage;
      String clientHost = getClientHost();
      // Log the PUT request
      System.out.println(getLogHeader(clientHost) + "Received PUT " +
              "Request to PUT key \"" + key + "\" with value \"" + val + "\"");
      // Store the key-value pair in the key-value store
      keyValStore.put(key, val);
      serverMessage = key + " with value \"" + val + "\" saved successfully";
      System.out.println(getLogHeader(clientHost) + serverMessage);
      lock.unlock();
    } catch (Exception e) {
      lock.unlock();
      throw new RemoteException("An error occurred while performing PUT request", e);
    }
  };

  // Retrieve the value associated with a key from the Key Store
  public String get(String key) throws RemoteException {
    lock.lock();
    try {
      String clientHost = getClientHost();
      // Log the GET request
      System.out.println(getLogHeader(clientHost) + "Received GET " +
              "Request to read key \"" + key +"\"");
      if (keyValStore.containsKey(key)) {
        // Get the value associated with the key from the key-value store
        String val = keyValStore.get(key);
        String serverMessage = "Successfully read key \"" + key +"\" with val \"" + val + "\"";
        System.out.println(getLogHeader(clientHost) + serverMessage);
        lock.unlock();
        return val;
      } else {
        // Key not found
        System.err.println(getLogHeader(clientHost) + "Invalid Key Provided. GET Request FAILED");
      }
    } catch (Exception e) {
      lock.unlock();
      throw new RemoteException("An error occurred while performing GET request", e);
    }
    lock.unlock();
    throw new RemoteException("Invalid Key Provided!");
  }

  public void delete(String key) throws RemoteException {
    lock.lock();
    try {
      String serverMessage;
      String clientHost = getClientHost();
      // Log the DELETE request
      System.out.println(getLogHeader(clientHost) + "Received DEL " +
              "Request to delete key \"" + key +"\"");
      if (keyValStore.containsKey(key)) {
        // Get the value associated with the key from the key-value store
        String val = keyValStore.remove(key);
        serverMessage = "Successfully deleted key \"" + key +"\" with val \"" + val + "\"";
        System.out.println(getLogHeader(clientHost) + serverMessage);
        lock.unlock();
        return;
      } else {
        // Key not found
        System.err.println(getLogHeader(clientHost) +  "Valid Key not Found in the Key Val Store.");
      }
    } catch (Exception e) {
      lock.unlock();
      throw new RemoteException("An error occurred while performing DELETE request", e);
    }

    lock.unlock();
    throw new RemoteException("Invalid Key Provided");
  }

  /**
   * Generates a log header with timestamp and client IP address.
   * @param ip The client IP address.
   * @return The log header string.
   */
  private static String getLogHeader(String ip) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return "[" + timestamp.toString() + " ,IP: " + ip + "]  ";
  }

  /**
   * Retrieves the client host IP address.
   * @return The client host IP address.
   */
  public static String getClientHost() {
    String clientHost = null;
    try {
      clientHost = RemoteServer.getClientHost();
    } catch (ServerNotActiveException e) {
      throw new RuntimeException(e);
    }
    return clientHost;
  }
}
