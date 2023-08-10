/**
 * MyServer.java
 * This is the main file for the Key-Value Store server application using Java RMI.
 * It starts the server and binds the KeyStoreRemote object to the RMI registry.
 * Usage: java MyServer <port>
 *   - <port>: The port number on which the server will listen for incoming RMI requests.
 * Author: Gaurang Jotwani
 * Course: NEU Summer 23 CS 6650
 * Date: 06/20/2023
 */

import java.rmi.*;

public class MyServer {

  /**
   * The main method that starts the server.
   * @param args Command line arguments (expects a single argument: the port number).
   */
  public static void main(String args[]) {
    try {
      if (args.length != 1) {
        System.err.println("Provide Correct number of Arguments (Port)");
        System.exit(-1);
      }
      // Get the port from command line arguments
      int port = Integer.parseInt(args[0]);

      System.out.println("Server Starting...");
      // Create an instance of the KeyStoreRemote object
      KeyStore stub1 = new KeyStoreRemote();

      // Bind the KeyStoreRemote object to the RMI registry using the specified port
      //Replace localhost with IP address if needed
      Naming.rebind("rmi://localhost:" + port + "/keyStore", stub1);

    } catch (AccessException e) {
      System.err.println("Cannot access host. Try again!");
      System.exit(-1);
    } catch (UnknownHostException e) {
      System.err.println("Unknown Host. Try again!");
      System.exit(-1);
    } catch (RemoteException e) {
      System.err.println("Unknown Remote Exception. Try again!");
      System.exit(-1);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(-1);
    }
  }
}
