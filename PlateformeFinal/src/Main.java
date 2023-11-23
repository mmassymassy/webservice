import classes.Plateforme;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
    private static final int PORT=1099;
    public static void main(String[] args) throws RemoteException, MalformedURLException {

        LocateRegistry.createRegistry(PORT);
        Plateforme plateforme = new Plateforme();
        Naming.rebind("plateforme", plateforme);

    }
}