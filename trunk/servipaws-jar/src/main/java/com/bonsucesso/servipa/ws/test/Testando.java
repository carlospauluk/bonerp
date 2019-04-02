package com.bonsucesso.servipa.ws.test;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.bonsucesso.servipa.ws.ServerWsrcs;
import com.bonsucesso.servipa.ws.ServerWsrcsLocator;
import com.bonsucesso.servipa.ws.ServerWsrcsPortType;
import com.bonsucesso.servipa.ws.TRetornoRecebimentoDia;



public class Testando {

	public static void main(String... args) {
		try {
			ServerWsrcs locator = new ServerWsrcsLocator();
			ServerWsrcsPortType service = locator.getServerWsrcsPort();
			
			TRetornoRecebimentoDia recDia = service.fncListaRecebimentosDia(null, "3", "15090", "wsbonsucesso", "ws$321%^ers", 20140728, null, null, null, null);
			
			
			recDia.getListaRecebimento()[0].getDadosPrestacao();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
