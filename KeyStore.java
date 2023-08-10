/**
 * KeyStore.java
 *
 * This file defines the KeyStore interface, which is used for remote communication
 * between clients and the Key Store service. The interface provides methods to store,
 * retrieve, and delete key-value pairs in the Key Store.
 *
 * Author: Gaurang Jotwani
 * Course: NEU Summer 23 CS 6650
 * Date: 06/20/2023
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface KeyStore extends Remote {

  /**
   * Stores a key-value pair in the Key Store.
   *
   * @param key the key to store
   * @param val the value to associate with the key
   * @throws RemoteException if a remote communication error occurs
   */
  public void put(String key, String val) throws RemoteException;

  /**
   * Retrieves the value associated with the specified key from the Key Store.
   *
   * @param key the key to retrieve
   * @return the value associated with the key
   * @throws RemoteException if a remote communication error occurs
   */
  public String get(String key) throws RemoteException;

  /**
   * Deletes the key-value pair with the specified key from the Key Store.
   *
   * @param key the key to delete
   * @throws RemoteException if a remote communication error occurs
   */
  public void delete(String key) throws RemoteException;
}