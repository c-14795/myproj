import java.io.IOException;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService
public interface SendRequestsPortType extends Remote {
    public String getXmlData(String URL, String filepath) throws IOException,
                                                                 RemoteException;

    public String postData(String sql, String url, String tableName,
                           String idColName,
                           String colMapFilepath) throws IOException,
                                                         RemoteException;
}
